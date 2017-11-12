/*
 *    Copyright 2017 Chimerapps
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 */

package com.chimerapps.alexa.home

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestStreamHandler
import com.chimerapps.alexa.home.error.DriverInternalError
import com.chimerapps.alexa.home.error.SmartHomeError
import com.chimerapps.alexa.home.model.Controller
import com.chimerapps.alexa.home.model.Directive
import com.chimerapps.alexa.home.model.Event
import com.chimerapps.alexa.home.model.controllers
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.slf4j.LoggerFactory
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.io.OutputStreamWriter

/**
 * @author Nicola Verbeeck
 * Date 09/03/2017.
 */
abstract class RawSmartHomeRequestHandler : RequestStreamHandler {

    companion object {
        val mapper = jacksonObjectMapper().let {
            it.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            it
        }
        private val logger = LoggerFactory.getLogger(RawSmartHomeRequestHandler::class.java)

        private const val NAMESPACE_DISCOVERY = "Alexa.Discovery"
        private const val SUPPORTED_VERSION = 3
    }

    init {
        logger.info("Smart Home Request Handler being created")
    }

    @Throws(SmartHomeError::class)
    protected abstract fun onDiscovery(request: Directive, context: Context): Event

    @Throws(SmartHomeError::class)
    protected abstract fun onControl(controller: Controller, request: Directive, context: Context): Event

    override fun handleRequest(input: InputStream, output: OutputStream, context: Context) {
        try {
            logger.debug("handleRequest")
            val reply = doHandleRequest(input.buffered(), context)
            logger.debug("Handled request: {}", reply)
            sendReply(output.buffered(), reply)
        } catch (e: SmartHomeError) {
            logger.warn("Error while executing request: {}", e)
            sendReply(output.buffered(), makeError(e))
        }
    }

    private fun sendReply(output: OutputStream, reply: Event) {
        OutputStreamWriter(output, Charsets.UTF_8).use {
            if (logger.isDebugEnabled) {
                val replyString = mapper.writeValueAsString(reply)
                logger.debug("Writing reply: {}", replyString)
                it.write(replyString)
            } else {
                mapper.writeValue(it, reply)
            }
        }
    }

    private fun doHandleRequest(input: InputStream, context: Context): Event {
        val request = InputStreamReader(input, Charsets.UTF_8).use {
            mapper.readValue<Directive>(it)
        }
        logger.debug("Lambda Request: {}", request)
        checkVersion(request)

        try {
            val namespace = request.header.namespace
            if (namespace == NAMESPACE_DISCOVERY) {
                return onDiscovery(request, context)
            }
            val controller = controllers.find { it.namespace == namespace }
            if (controller != null)
                return onControl(controller, request, context)

            throw DriverInternalError(request, "Unknown namespace: $namespace")
        } catch (e: SmartHomeError) {
            throw e
        } catch (e: Throwable) {
            throw DriverInternalError(request, "Internal error!", e)
        }
    }

    private fun checkVersion(request: Directive) {
        try {
            val version = request.header.payloadVersion.toInt()
            if (version > SUPPORTED_VERSION) {
                throw DriverInternalError(request, "Unsupported version, reported: $version")
            }
        } catch (e: NumberFormatException) {
            throw DriverInternalError(request, "Invalid version, reported: ${request.header.payloadVersion}")
        }
    }

    private fun makeError(e: SmartHomeError): Event {
        return Event(header = e.directive.header.toResponseWithName(name = "ErrorResponse"),
                endpoint = e.directive.endpoint,
                payload = e.payload,
                context = null)
    }

}