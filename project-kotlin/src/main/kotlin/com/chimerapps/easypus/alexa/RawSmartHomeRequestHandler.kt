package com.chimerapps.easypus.alexa

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestStreamHandler
import com.chimerapps.easypus.alexa.error.DriverInternalError
import com.chimerapps.easypus.alexa.error.SmartHomeError
import com.chimerapps.easypus.alexa.model.SmartHomeReply
import com.chimerapps.easypus.alexa.model.SmartHomeRequest
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.slf4j.LoggerFactory

import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.io.OutputStreamWriter

/**
 * @author Nicola Verbeeck
 * @date 09/03/2017.
 */
abstract class RawSmartHomeRequestHandler : RequestStreamHandler {

    companion object {
        val mapper = jacksonObjectMapper().let {
            it.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            it
        }
        val logger = LoggerFactory.getLogger(RawSmartHomeRequestHandler::class.java)

        private val NAMESPACE_DISCOVERY = "Alexa.ConnectedHome.Discovery"
        private val NAMESPACE_CONTROL = "Alexa.ConnectedHome.Control"
        private val NAMESPACE_QUERY = "Alexa.ConnectedHome.Query"
        private val NAMESPACE_SYSTEM = "Alexa.ConnectedHome.System"
        private val SUPPORTED_VERSION = 2
    }

    @Throws(SmartHomeError::class)
    protected abstract fun onDiscovery(request: SmartHomeRequest, context: Context): SmartHomeReply

    @Throws(SmartHomeError::class)
    protected abstract fun onQuery(request: SmartHomeRequest, context: Context): SmartHomeReply

    @Throws(SmartHomeError::class)
    protected abstract fun onControl(request: SmartHomeRequest, context: Context): SmartHomeReply

    @Throws(SmartHomeError::class)
    protected abstract fun onSystem(request: SmartHomeRequest, context: Context): SmartHomeReply

    final override fun handleRequest(input: InputStream, output: OutputStream, context: Context) {
        try {
            val reply = doHandleRequest(input, context)
            OutputStreamWriter(output, Charsets.UTF_8).use {
                logger.debug("Handled request: {}", reply)
                mapper.writeValue(it, reply)
            }
        } catch (e: SmartHomeError) {
            logger.warn("Error while executing request: {}", e, e.errorName)
            OutputStreamWriter(output, Charsets.UTF_8).use {
                mapper.writeValue(it, makeError(e))
            }
        }
    }

    private fun doHandleRequest(input: InputStream, context: Context): SmartHomeReply {
        val request = InputStreamReader(input, Charsets.UTF_8).use {
            mapper.readValue<SmartHomeRequest>(it)
        }
        logger.debug("Lambda Request: {}", request)
        checkVersion(request)

        return when (request.header.namespace) {
            NAMESPACE_DISCOVERY -> onDiscovery(request, context)
            NAMESPACE_CONTROL -> onControl(request, context)
            NAMESPACE_QUERY -> onQuery(request, context)
            NAMESPACE_SYSTEM -> onSystem(request, context)
            else -> throw DriverInternalError(request.header, "Unknown namespace: ${request.header.namespace}")
        }
    }

    private fun checkVersion(request: SmartHomeRequest) {
        try {
            val version = request.header.payloadVersion.toInt()
            if (version > SUPPORTED_VERSION) {
                throw DriverInternalError(request.header, "Unsupported version, reported: $version")
            }
        } catch (e: NumberFormatException) {
            throw DriverInternalError(request.header, "Invalid version, reported: ${request.header.payloadVersion}")
        }
    }

    private fun makeError(e: SmartHomeError): SmartHomeReply {
        return SmartHomeReply(e.header.copy(name = e.errorName), ObjectNode(mapper.nodeFactory))
    }

}