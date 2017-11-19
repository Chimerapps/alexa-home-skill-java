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
import com.chimerapps.alexa.home.model.*
import com.chimerapps.alexa.home.utils.readJson
import com.google.gson.GsonBuilder
import org.slf4j.LoggerFactory
import java.io.InputStream
import java.io.OutputStream
import java.io.OutputStreamWriter

/**
 * @author Nicola Verbeeck
 * Date 09/03/2017.
 */
abstract class RawSmartHomeRequestHandler : RequestStreamHandler {

    companion object {
        val gson = GsonBuilder()
                .registerTypeAdapter(Directive::class.java, DirectiveAdapter)
                .registerTypeAdapter(DirectiveRequest::class.java, DirectiveRequestAdapter)
                .registerTypeAdapter(Header::class.java, HeaderAdapter)
                .registerTypeAdapter(EndpointAdapter::class.java, EndpointAdapter)
                .registerTypeAdapter(Scope::class.java, ScopeAdapter)
                .registerTypeAdapter(Context::class.java, ContextAdapter)
                .registerTypeAdapter(PropertyAdapter::class.java, PropertyAdapter)
                .create()

        private val logger = LoggerFactory.getLogger(RawSmartHomeRequestHandler::class.java)

        private const val NAMESPACE_DISCOVERY = "Alexa.Discovery"
        private const val SUPPORTED_VERSION = 3
    }

    init {
        logger.info("Smart Home Request Handler being created")
    }

    @Throws(SmartHomeError::class)
    protected abstract fun onDiscovery(request: Directive, context: Context): EventWithContext

    @Throws(SmartHomeError::class)
    protected abstract fun onControl(controller: Controller, request: Directive, context: Context): EventWithContext

    override fun handleRequest(input: InputStream, output: OutputStream, context: Context) {
        try {
            logger.debug("handleRequest")
            val reply = doHandleRequest(input.buffered(), context)
            logger.debug("Handled request: {}", reply)
            sendReply(output.buffered(), reply)
        } catch (e: SmartHomeError) {
            logger.warn("Error while executing request: {}", e)
            sendReply(output.buffered(), makeError(e))
        } catch (e: Throwable) {
            logger.error("Failed to handle request:", e)
        }
    }

    protected open fun sendReply(output: OutputStream, reply: EventWithContext) {
        OutputStreamWriter(output, Charsets.UTF_8).use {
            if (logger.isDebugEnabled) {
                val replyString = gson.toJson(reply)
                logger.debug("Writing reply: {}", replyString)
                it.write(replyString)
            } else {
                gson.toJson(reply, it)
            }
        }
    }

    private fun doHandleRequest(input: InputStream, context: Context): EventWithContext {
        logger.debug("Reading request from json")
        val text = input.bufferedReader().use { it.readText() }
        logger.debug("Request read, transforming")
        if (logger.isDebugEnabled) {
            logger.debug("Request json: {}", text)
        }
        val request = gson.readJson<DirectiveRequest>(text).directive
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

    private fun makeError(e: SmartHomeError): EventWithContext {
        return EventWithContext(
                event = Event(header = e.directive.header.toResponseWithName(name = "ErrorResponse"),
                        endpoint = e.directive.endpoint,
                        payload = e.payload),
                context = null)
    }

}