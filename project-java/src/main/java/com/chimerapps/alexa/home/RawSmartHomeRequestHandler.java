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

package com.chimerapps.alexa.home;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.chimerapps.alexa.home.error.DriverInternalError;
import com.chimerapps.alexa.home.error.SmartHomeError;
import com.chimerapps.alexa.home.model.EmptyPayload;
import com.chimerapps.alexa.home.model.SmartHomeHeader;
import com.chimerapps.alexa.home.model.SmartHomeReply;
import com.chimerapps.alexa.home.model.SmartHomeRequest;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.UUID;

/**
 * @author Nicola Verbeeck
 *         Date 10/03/2017.
 */
public abstract class RawSmartHomeRequestHandler implements RequestStreamHandler {

	private static final String NAMESPACE_DISCOVERY = "Alexa.ConnectedHome.Discovery";
	private static final String NAMESPACE_CONTROL = "Alexa.ConnectedHome.Control";
	private static final String NAMESPACE_QUERY = "Alexa.ConnectedHome.Query";
	private static final String NAMESPACE_SYSTEM = "Alexa.ConnectedHome.System";
	private static final int SUPPORTED_VERSION = 2;

	private static final Logger logger = LoggerFactory.getLogger(RawSmartHomeRequestHandler.class);
	protected static final ObjectMapper mapper = new ObjectMapper();

	static {
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	}

	public RawSmartHomeRequestHandler() {
		logger.info("Smart Home Request Handler being created");
	}

	protected abstract SmartHomeReply onDiscovery(final SmartHomeRequest request, final Context context) throws SmartHomeError;

	protected abstract SmartHomeReply onQuery(final SmartHomeRequest request, final Context context) throws SmartHomeError;

	protected abstract SmartHomeReply onControl(final SmartHomeRequest request, final Context context) throws SmartHomeError;

	protected abstract SmartHomeReply onSystem(final SmartHomeRequest request, final Context context) throws SmartHomeError;

	@Override
	public void handleRequest(final InputStream input, final OutputStream output, final Context context) throws IOException {
		try {
			logger.debug("handleRequest");
			final SmartHomeReply reply = doHandleRequest(input, context);
			logger.debug("Handled request: {}", reply);
			sendReply(output, reply);
		} catch (final SmartHomeError e) {
			logger.warn("Error while executing request: {}", e, e.getErrorName());
			sendReply(output, makeError(e));
		}
	}

	private void sendReply(final OutputStream output, final SmartHomeReply reply) throws IOException {
		try (final Writer writer = new OutputStreamWriter(output, "UTF-8")) {
			if (logger.isDebugEnabled()) {
				final String replyString = mapper.writeValueAsString(reply);
				logger.debug("Writing reply: {}", replyString);
				writer.write(replyString);
			} else {
				mapper.writeValue(writer, reply);
			}
		}
	}

	private SmartHomeReply doHandleRequest(final InputStream input, final Context context) throws SmartHomeError {
		final SmartHomeRequest request;
		try (final Reader reader = new InputStreamReader(input, "UTF-8")) {
			request = mapper.readValue(reader, SmartHomeRequest.class);
		} catch (final Exception e) {
			throw new DriverInternalError(makeHeader(), "Internal error!", e);
		}

		logger.debug("Lambda Request: {}", request);
		checkVersion(request);

		try {
			switch (request.getHeader().getNamespace()) {
				case NAMESPACE_DISCOVERY:
					return onDiscovery(request, context);
				case NAMESPACE_CONTROL:
					return onControl(request, context);
				case NAMESPACE_QUERY:
					return onQuery(request, context);
				case NAMESPACE_SYSTEM:
					return onSystem(request, context);
				default:
					throw new DriverInternalError(request.getHeader(), "Unknown namespace: " + request.getHeader().getNamespace());
			}
		} catch (final SmartHomeError e) {
			throw e;
		} catch (final Throwable e) {
			throw new DriverInternalError(request.getHeader(), "Internal error!", e);
		}
	}

	private void checkVersion(final SmartHomeRequest request) throws SmartHomeError {
		try {
			final int version = Integer.parseInt(request.getHeader().getPayloadVersion());
			if (version > SUPPORTED_VERSION) {
				throw new DriverInternalError(request.getHeader(), "Unsupported version, reported: " + version);
			}
		} catch (final NumberFormatException e) {
			throw new DriverInternalError(request.getHeader(), "Invalid version, reported: " + request.getHeader().getPayloadVersion(), e);
		}
	}

	private static SmartHomeHeader makeHeader() {
		return new SmartHomeHeader(UUID.randomUUID().toString(), "", "", String.valueOf(SUPPORTED_VERSION));
	}

	private static SmartHomeReply makeError(final SmartHomeError e) {
		return new SmartHomeReply(e.getHeader(), new EmptyPayload(e.getErrorName()));
	}
}
