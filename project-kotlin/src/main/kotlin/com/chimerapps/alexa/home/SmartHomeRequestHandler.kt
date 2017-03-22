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
import com.chimerapps.alexa.home.error.SmartHomeError
import com.chimerapps.alexa.home.error.UnsupportedOperationError
import com.chimerapps.alexa.home.model.*
import com.chimerapps.alexa.home.utils.readValue
import java.util.*

/**
 * @author Nicola Verbeeck
 * Date 09/03/2017.
 */
@Suppress("unused", "UNUSED_PARAMETER")
abstract class SmartHomeRequestHandler : RawSmartHomeRequestHandler() {

    @Throws(SmartHomeError::class)
    protected open fun handleDiscovery(header: SmartHomeHeader, request: DiscoverAppliancesRequest, context: Context): DiscoverAppliancesResponse = throw UnsupportedOperationError(header, "Operation not supported")

    @Throws(SmartHomeError::class)
    protected open fun handleGetLockState(header: SmartHomeHeader, request: GetLockStateRequest, context: Context): GetLockStateResponse = throw UnsupportedOperationError(header, "Operation not supported")

    @Throws(SmartHomeError::class)
    protected open fun handleGetTargetTemperature(header: SmartHomeHeader, request: GetTargetTemperatureRequest, context: Context): GetTargetTemperatureResponse = throw UnsupportedOperationError(header, "Operation not supported")

    @Throws(SmartHomeError::class)
    protected open fun handleGetTemperatureReading(header: SmartHomeHeader, request: GetTemperatureReadingRequest, context: Context): GetTemperatureReadingResponse = throw UnsupportedOperationError(header, "Operation not supported")

    @Throws(SmartHomeError::class)
    protected open fun handleHealthCheck(header: SmartHomeHeader, request: HealthCheckRequest, context: Context): HealthCheckResponse = throw UnsupportedOperationError(header, "Operation not supported")

    @Throws(SmartHomeError::class)
    protected open fun handleSetLockState(header: SmartHomeHeader, request: SetLockStateRequest, context: Context): SetLockStateConfirmation = throw UnsupportedOperationError(header, "Operation not supported")

    @Throws(SmartHomeError::class)
    protected open fun handleIncrementTemperature(header: SmartHomeHeader, request: IncrementTargetTemperatureRequest, context: Context): IncrementTargetTemperatureConfirmation = throw UnsupportedOperationError(header, "Operation not supported")

    @Throws(SmartHomeError::class)
    protected open fun handleDecrementTemperature(header: SmartHomeHeader, request: DecrementTargetTemperatureRequest, context: Context): DecrementTargetTemperatureConfirmation = throw UnsupportedOperationError(header, "Operation not supported")

    @Throws(SmartHomeError::class)
    protected open fun handleDecrementPercentage(header: SmartHomeHeader, request: DecrementPercentageRequest, context: Context): DecrementPercentageConfirmation = throw UnsupportedOperationError(header, "Operation not supported")

    @Throws(SmartHomeError::class)
    protected open fun handleIncrementPercentage(header: SmartHomeHeader, request: IncrementPercentageRequest, context: Context): IncrementPercentageConfirmation = throw UnsupportedOperationError(header, "Operation not supported")

    @Throws(SmartHomeError::class)
    protected open fun handleSetPercentage(header: SmartHomeHeader, request: SetPercentageRequest, context: Context): SetPercentageConfirmation = throw UnsupportedOperationError(header, "Operation not supported")

    @Throws(SmartHomeError::class)
    protected open fun handleTurnOn(header: SmartHomeHeader, request: TurnOnRequest, context: Context): TurnOnConfirmation = throw UnsupportedOperationError(header, "Operation not supported")

    @Throws(SmartHomeError::class)
    protected open fun handleTurnOff(header: SmartHomeHeader, request: TurnOffRequest, context: Context): TurnOffConfirmation = throw UnsupportedOperationError(header, "Operation not supported")

    @Throws(SmartHomeError::class)
    protected open fun handleSetTemperature(header: SmartHomeHeader, request: SetTargetTemperatureRequest, context: Context): SetTargetTemperatureConfirmation = throw UnsupportedOperationError(header, "Operation not supported")

    @Throws(SmartHomeError::class)
    override fun onDiscovery(request: SmartHomeRequest, context: Context): SmartHomeReply {
        val actualRequest = mapper.readValue<DiscoverAppliancesRequest>(request.payload)
        return SmartHomeReply(request.header.copy(messageId = UUID.randomUUID().toString()), handleDiscovery(request.header, actualRequest, context))
    }

    @Throws(SmartHomeError::class)
    override fun onQuery(request: SmartHomeRequest, context: Context): SmartHomeReply {
        return SmartHomeReply(request.header.copy(messageId = UUID.randomUUID().toString()),
                when (request.header.name) {
                    ACTION_GET_LOCK_STATE -> handleGetLockState(request.header, mapper.readValue<GetLockStateRequest>(request.payload), context)
                    ACTION_GET_TARGET_TEMPERATURE -> handleGetTargetTemperature(request.header, mapper.readValue<GetTargetTemperatureRequest>(request.payload), context)
                    ACTION_GET_TEMPERATURE_READING -> handleGetTemperatureReading(request.header, mapper.readValue<GetTemperatureReadingRequest>(request.payload), context)
                    else -> throw UnsupportedOperationError(request.header, "Unknown name: ${request.header.name}")
                })
    }

    @Throws(SmartHomeError::class)
    override fun onControl(request: SmartHomeRequest, context: Context): SmartHomeReply {
        return SmartHomeReply(request.header.copy(messageId = UUID.randomUUID().toString()),
                when (request.header.name) {
                    ACTION_SET_LOCK_STATE -> handleSetLockState(request.header, mapper.readValue<SetLockStateRequest>(request.payload), context)
                    ACTION_SET_TARGET_TEMPERATURE -> handleSetTemperature(request.header, mapper.readValue<SetTargetTemperatureRequest>(request.payload), context)
                    ACTION_INC_TEMPERATURE -> handleIncrementTemperature(request.header, mapper.readValue<IncrementTargetTemperatureRequest>(request.payload), context)
                    ACTION_DEC_TEMPERATURE -> handleDecrementTemperature(request.header, mapper.readValue<DecrementTargetTemperatureRequest>(request.payload), context)
                    ACTION_DEC_PERCENTAGE -> handleDecrementPercentage(request.header, mapper.readValue<DecrementPercentageRequest>(request.payload), context)
                    ACTION_INC_PERCENTAGE -> handleIncrementPercentage(request.header, mapper.readValue<IncrementPercentageRequest>(request.payload), context)
                    ACTION_SET_PERCENTAGE -> handleSetPercentage(request.header, mapper.readValue<SetPercentageRequest>(request.payload), context)
                    ACTION_TURN_ON -> handleTurnOn(request.header, mapper.readValue<TurnOnRequest>(request.payload), context)
                    ACTION_TURN_OFF -> handleTurnOff(request.header, mapper.readValue<TurnOffRequest>(request.payload), context)
                    else -> throw UnsupportedOperationError(request.header, "Unknown name: ${request.header.name}")
                }
        )
    }

    @Throws(SmartHomeError::class)
    override fun onSystem(request: SmartHomeRequest, context: Context): SmartHomeReply {
        return SmartHomeReply(request.header.copy(messageId = UUID.randomUUID().toString()),
                when (request.header.name) {
                    ACTION_HEALTH_CHECK -> handleHealthCheck(request.header, mapper.readValue<HealthCheckRequest>(request.payload), context)
                    else -> throw UnsupportedOperationError(request.header, "Unknown name: ${request.header.name}")
                })
    }

    companion object {
        val ACTION_GET_LOCK_STATE = "GetLockStateRequest"
        val ACTION_SET_LOCK_STATE = "SetLockStateRequest"

        val ACTION_GET_TARGET_TEMPERATURE = "GetTargetTemperatureRequest"
        val ACTION_SET_TARGET_TEMPERATURE = "SetTargetTemperatureRequest"
        val ACTION_INC_TEMPERATURE = "IncrementTargetTemperatureRequest"
        val ACTION_DEC_TEMPERATURE = "DecrementTargetTemperatureRequest"

        val ACTION_GET_TEMPERATURE_READING = "GetTemperatureReadingRequest"

        val ACTION_HEALTH_CHECK = "HealthCheckRequest"

        val ACTION_DEC_PERCENTAGE = "DecrementPercentageRequest"
        val ACTION_INC_PERCENTAGE = "IncrementPercentageRequest"
        val ACTION_SET_PERCENTAGE = "SetPercentageRequest"

        val ACTION_TURN_ON = "TurnOnRequest"
        val ACTION_TURN_OFF = "TurnOffRequest"
    }
}