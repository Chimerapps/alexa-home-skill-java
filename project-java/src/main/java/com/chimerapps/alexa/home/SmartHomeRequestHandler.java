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
import com.chimerapps.alexa.home.error.DriverInternalError;
import com.chimerapps.alexa.home.error.SmartHomeError;
import com.chimerapps.alexa.home.error.UnsupportedOperationError;
import com.chimerapps.alexa.home.model.ApplianceRequest;
import com.chimerapps.alexa.home.model.ResponsePayload;
import com.chimerapps.alexa.home.model.SmartHomeHeader;
import com.chimerapps.alexa.home.model.SmartHomeReply;
import com.chimerapps.alexa.home.model.SmartHomeRequest;
import com.chimerapps.alexa.home.model.discovery.DiscoverAppliancesRequest;
import com.chimerapps.alexa.home.model.discovery.DiscoverAppliancesResponse;
import com.chimerapps.alexa.home.model.healthcheck.HealthCheckRequest;
import com.chimerapps.alexa.home.model.healthcheck.HealthCheckResponse;
import com.chimerapps.alexa.home.model.lockstate.GetLockStateResponse;
import com.chimerapps.alexa.home.model.lockstate.SetLockStateConfirmation;
import com.chimerapps.alexa.home.model.lockstate.SetLockStateRequest;
import com.chimerapps.alexa.home.model.percentage.DecrementPercentageConfirmation;
import com.chimerapps.alexa.home.model.percentage.DecrementPercentageRequest;
import com.chimerapps.alexa.home.model.percentage.IncrementPercentageConfirmation;
import com.chimerapps.alexa.home.model.percentage.IncrementPercentageRequest;
import com.chimerapps.alexa.home.model.percentage.SetPercentageConfirmation;
import com.chimerapps.alexa.home.model.percentage.SetPercentageRequest;
import com.chimerapps.alexa.home.model.temperature.DecrementTargetTemperatureConfirmation;
import com.chimerapps.alexa.home.model.temperature.DecrementTargetTemperatureRequest;
import com.chimerapps.alexa.home.model.temperature.GetTargetTemperatureResponse;
import com.chimerapps.alexa.home.model.temperature.GetTemperatureReadingResponse;
import com.chimerapps.alexa.home.model.temperature.IncrementTargetTemperatureRequest;
import com.chimerapps.alexa.home.model.temperature.SetTargetTemperatureConfirmation;
import com.chimerapps.alexa.home.model.temperature.SetTargetTemperatureRequest;
import com.chimerapps.alexa.home.model.toggle.TurnOffConfirmation;
import com.chimerapps.alexa.home.model.toggle.TurnOnConfirmation;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * @author Nicola Verbeeck
 * Date 10/03/2017.
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class SmartHomeRequestHandler extends RawSmartHomeRequestHandler {

	public static final String ACTION_GET_LOCK_STATE = "GetLockStateRequest";
	public static final String ACTION_SET_LOCK_STATE = "SetLockStateRequest";

	public static final String ACTION_GET_TARGET_TEMPERATURE = "GetTargetTemperatureRequest";
	public static final String ACTION_SET_TARGET_TEMPERATURE = "SetTargetTemperatureRequest";
	public static final String ACTION_INC_TEMPERATURE = "IncrementTargetTemperatureRequest";
	public static final String ACTION_DEC_TEMPERATURE = "DecrementTargetTemperatureRequest";

	public static final String ACTION_GET_TEMPERATURE_READING = "GetTemperatureReadingRequest";

	public static final String ACTION_HEALTH_CHECK = "HealthCheckRequest";

	public static final String ACTION_DEC_PERCENTAGE = "DecrementPercentageRequest";
	public static final String ACTION_INC_PERCENTAGE = "IncrementPercentageRequest";
	public static final String ACTION_SET_PERCENTAGE = "SetPercentageRequest";

	public static final String ACTION_TURN_ON = "TurnOnRequest";
	public static final String ACTION_TURN_OFF = "TurnOffRequest";


	protected DiscoverAppliancesResponse handleDiscovery(final SmartHomeHeader header, final DiscoverAppliancesRequest request, final Context context) throws SmartHomeError {
		throw new UnsupportedOperationError(header, "Operation not supported");
	}

	protected GetLockStateResponse handleGetLockState(final SmartHomeHeader header, final ApplianceRequest request, final Context context) throws SmartHomeError {
		throw new UnsupportedOperationError(header, "Operation not supported");
	}

	protected GetTargetTemperatureResponse handleGetTargetTemperature(final SmartHomeHeader header, final ApplianceRequest request, final Context context) throws SmartHomeError {
		throw new UnsupportedOperationError(header, "Operation not supported");
	}

	protected GetTemperatureReadingResponse handleGetTemperatureReading(final SmartHomeHeader header, final ApplianceRequest request, final Context context) throws SmartHomeError {
		throw new UnsupportedOperationError(header, "Operation not supported");
	}

	protected HealthCheckResponse handleHealthCheck(final SmartHomeHeader header, final HealthCheckRequest request, final Context context) throws SmartHomeError {
		throw new UnsupportedOperationError(header, "Operation not supported");
	}

	protected SetLockStateConfirmation handleSetLockState(final SmartHomeHeader header, final SetLockStateRequest request, final Context context) throws SmartHomeError {
		throw new UnsupportedOperationError(header, "Operation not supported");
	}

	protected IncrementPercentageConfirmation handleIncrementTemperature(final SmartHomeHeader header, final IncrementTargetTemperatureRequest request, final Context context) throws SmartHomeError {
		throw new UnsupportedOperationError(header, "Operation not supported");
	}

	protected DecrementTargetTemperatureConfirmation handleDecrementTemperature(final SmartHomeHeader header, final DecrementTargetTemperatureRequest request, final Context context) throws SmartHomeError {
		throw new UnsupportedOperationError(header, "Operation not supported");
	}

	protected DecrementPercentageConfirmation handleDecrementPercentage(final SmartHomeHeader header, final DecrementPercentageRequest request, final Context context) throws SmartHomeError {
		throw new UnsupportedOperationError(header, "Operation not supported");
	}

	protected IncrementPercentageConfirmation handleIncrementPercentage(final SmartHomeHeader header, final IncrementPercentageRequest request, final Context context) throws SmartHomeError {
		throw new UnsupportedOperationError(header, "Operation not supported");
	}

	protected SetPercentageConfirmation handleSetPercentage(final SmartHomeHeader header, final SetPercentageRequest request, final Context context) throws SmartHomeError {
		throw new UnsupportedOperationError(header, "Operation not supported");
	}

	protected TurnOnConfirmation handleTurnOn(final SmartHomeHeader header, final ApplianceRequest request, final Context context) throws SmartHomeError {
		throw new UnsupportedOperationError(header, "Operation not supported");
	}

	protected TurnOffConfirmation handleTurnOff(final SmartHomeHeader header, final ApplianceRequest request, final Context context) throws SmartHomeError {
		throw new UnsupportedOperationError(header, "Operation not supported");
	}

	protected SetTargetTemperatureConfirmation handleSetTemperature(final SmartHomeHeader header, final SetTargetTemperatureRequest request, final Context context) throws SmartHomeError {
		throw new UnsupportedOperationError(header, "Operation not supported");
	}


	@Override
	protected SmartHomeReply onDiscovery(final SmartHomeRequest request, final Context context) throws SmartHomeError {
		try {
			final DiscoverAppliancesRequest actualRequest = mapper.treeToValue(request.getPayload(), DiscoverAppliancesRequest.class);
			return new SmartHomeReply(request.getHeader(), handleDiscovery(request.getHeader(), actualRequest, context));
		} catch (final JsonProcessingException e) {
			throw new DriverInternalError(request.getHeader(), "Failed to read json", e);
		}
	}

	@Override
	protected SmartHomeReply onQuery(final SmartHomeRequest request, final Context context) throws SmartHomeError {
		try {
			final ResponsePayload payload;
			switch (request.getHeader().getName()) {
				case ACTION_GET_LOCK_STATE:
					payload = handleGetLockState(request.getHeader(), mapper.treeToValue(request.getPayload(), ApplianceRequest.class), context);
					break;
				case ACTION_GET_TARGET_TEMPERATURE:
					payload = handleGetTargetTemperature(request.getHeader(), mapper.treeToValue(request.getPayload(), ApplianceRequest.class), context);
					break;
				case ACTION_GET_TEMPERATURE_READING:
					payload = handleGetTemperatureReading(request.getHeader(), mapper.treeToValue(request.getPayload(), ApplianceRequest.class), context);
					break;
				default:
					throw new UnsupportedOperationError(request.getHeader(), "Unknown name: " + request.getHeader().getName());
			}
			return new SmartHomeReply(request.getHeader(), payload);
		} catch (final JsonProcessingException e) {
			throw new DriverInternalError(request.getHeader(), "Failed to read json", e);
		}
	}

	@Override
	protected SmartHomeReply onControl(SmartHomeRequest request, Context context) throws SmartHomeError {
		try {
			final ResponsePayload payload;
			switch (request.getHeader().getName()) {
				case ACTION_SET_LOCK_STATE:
					payload = handleSetLockState(request.getHeader(), mapper.treeToValue(request.getPayload(), SetLockStateRequest.class), context);
					break;
				case ACTION_SET_TARGET_TEMPERATURE:
					payload = handleSetTemperature(request.getHeader(), mapper.treeToValue(request.getPayload(), SetTargetTemperatureRequest.class), context);
					break;
				case ACTION_INC_TEMPERATURE:
					payload = handleIncrementTemperature(request.getHeader(), mapper.treeToValue(request.getPayload(), IncrementTargetTemperatureRequest.class), context);
					break;
				case ACTION_DEC_TEMPERATURE:
					payload = handleDecrementTemperature(request.getHeader(), mapper.treeToValue(request.getPayload(), DecrementTargetTemperatureRequest.class), context);
					break;
				case ACTION_DEC_PERCENTAGE:
					payload = handleDecrementPercentage(request.getHeader(), mapper.treeToValue(request.getPayload(), DecrementPercentageRequest.class), context);
					break;
				case ACTION_INC_PERCENTAGE:
					payload = handleIncrementPercentage(request.getHeader(), mapper.treeToValue(request.getPayload(), IncrementPercentageRequest.class), context);
					break;
				case ACTION_SET_PERCENTAGE:
					payload = handleSetPercentage(request.getHeader(), mapper.treeToValue(request.getPayload(), SetPercentageRequest.class), context);
					break;
				case ACTION_TURN_ON:
					payload = handleTurnOn(request.getHeader(), mapper.treeToValue(request.getPayload(), ApplianceRequest.class), context);
					break;
				case ACTION_TURN_OFF:
					payload = handleTurnOff(request.getHeader(), mapper.treeToValue(request.getPayload(), ApplianceRequest.class), context);
					break;
				default:
					throw new UnsupportedOperationError(request.getHeader(), "Unknown name: " + request.getHeader().getName());
			}
			return new SmartHomeReply(request.getHeader(), payload);
		} catch (final JsonProcessingException e) {
			throw new DriverInternalError(request.getHeader(), "Failed to read json", e);
		}
	}

	@Override
	protected SmartHomeReply onSystem(SmartHomeRequest request, Context context) throws SmartHomeError {
		try {
			final ResponsePayload payload;
			switch (request.getHeader().getName()) {
				case ACTION_HEALTH_CHECK:
					payload = handleHealthCheck(request.getHeader(), mapper.treeToValue(request.getPayload(), HealthCheckRequest.class), context);
					break;
				default:
					throw new UnsupportedOperationError(request.getHeader(), "Unknown name: " + request.getHeader().getName());
			}
			return new SmartHomeReply(request.getHeader(), payload);
		} catch (final JsonProcessingException e) {
			throw new DriverInternalError(request.getHeader(), "Failed to read json", e);
		}
	}
}