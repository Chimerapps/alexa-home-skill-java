package com.chimerapps.easypus.alexa

import com.amazonaws.services.lambda.runtime.Context
import com.chimerapps.easypus.alexa.error.UnsupportedOperationError
import com.chimerapps.easypus.alexa.model.*
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper

/**
 * @author Nicola Verbeeck
 * @date 09/03/2017.
 */
@Suppress("unused", "UNUSED_PARAMETER")
abstract class SmartHomeRequestHandler : RawSmartHomeRequestHandler() {

    protected fun handleDiscovery(header: SmartHomeHeader, request: DiscoverAppliancesRequest, context: Context): DiscoverAppliancesResponse = throw UnsupportedOperationError(header, "Operation not supported")

    protected fun handleGetLockState(header: SmartHomeHeader, request: GetLockStateRequest, context: Context): GetLockStateResponse = throw UnsupportedOperationError(header, "Operation not supported")

    protected fun handleGetTargetTemperature(header: SmartHomeHeader, request: GetTargetTemperatureRequest, context: Context): GetTargetTemperatureResponse = throw UnsupportedOperationError(header, "Operation not supported")

    protected fun handleGetTemperatureReading(header: SmartHomeHeader, request: GetTemperatureReadingRequest, context: Context): GetTemperatureReadingResponse = throw UnsupportedOperationError(header, "Operation not supported")

    protected fun handleHealthCheck(header: SmartHomeHeader, request: HealthCheckRequest, context: Context): HealthCheckResponse = throw UnsupportedOperationError(header, "Operation not supported")

    protected fun handleSetLockState(header: SmartHomeHeader, request: SetLockStateRequest, context: Context): SetLockStateConfirmation = throw UnsupportedOperationError(header, "Operation not supported")

    protected fun handleIncrementTemperature(header: SmartHomeHeader, request: IncrementTargetTemperatureRequest, context: Context): IncrementPercentageConfirmation = throw UnsupportedOperationError(header, "Operation not supported")

    protected fun handleDecrementTemperature(header: SmartHomeHeader, request: DecrementTargetTemperatureRequest, context: Context): DecrementTargetTemperatureConfirmation = throw UnsupportedOperationError(header, "Operation not supported")

    protected fun handleDecrementPercentage(header: SmartHomeHeader, request: DecrementPercentageRequest, context: Context): DecrementPercentageConfirmation = throw UnsupportedOperationError(header, "Operation not supported")

    protected fun handleIncrementPercentage(header: SmartHomeHeader, request: IncrementPercentageRequest, context: Context): IncrementPercentageConfirmation = throw UnsupportedOperationError(header, "Operation not supported")

    protected fun handleSetPercentage(header: SmartHomeHeader, request: SetPercentageRequest, context: Context): SetPercentageConfirmation = throw UnsupportedOperationError(header, "Operation not supported")

    protected fun handleTurnOn(header: SmartHomeHeader, request: TurnOnRequest, context: Context): TurnOnConfirmation = throw UnsupportedOperationError(header, "Operation not supported")

    protected fun handleTurnOff(header: SmartHomeHeader, request: TurnOffRequest, context: Context): TurnOffConfirmation = throw UnsupportedOperationError(header, "Operation not supported")

    protected fun handleSetTemperature(header: SmartHomeHeader, request: SetTargetTemperatureRequest, context: Context): SetTargetTemperatureConfirmation = throw UnsupportedOperationError(header, "Operation not supported")


    override fun onDiscovery(request: SmartHomeRequest, context: Context): SmartHomeReply {
        val actualRequest = mapper.readValue<DiscoverAppliancesRequest>(request.payload)
        return SmartHomeReply(request.header, handleDiscovery(request.header, actualRequest, context))
    }

    override fun onQuery(request: SmartHomeRequest, context: Context): SmartHomeReply {
        return SmartHomeReply(request.header,
                when (request.header.name) {
                    GET_LOCK_STATE -> handleGetLockState(request.header, mapper.readValue<GetLockStateRequest>(request.payload), context)
                    GET_TARGET_TEMPERATURE -> handleGetTargetTemperature(request.header, mapper.readValue<GetTargetTemperatureRequest>(request.payload), context)
                    GET_TEMPERATURE_READING -> handleGetTemperatureReading(request.header, mapper.readValue<GetTemperatureReadingRequest>(request.payload), context)
                    else -> throw UnsupportedOperationError(request.header, "Unknown name: ${request.header.name}")
                })
    }

    override fun onControl(request: SmartHomeRequest, context: Context): SmartHomeReply {
        return SmartHomeReply(request.header,
                when (request.header.name) {
                    SET_LOCK_STATE -> handleSetLockState(request.header, mapper.readValue<SetLockStateRequest>(request.payload), context)
                    SET_TARGET_TEMPERATURE -> handleSetTemperature(request.header, mapper.readValue<SetTargetTemperatureRequest>(request.payload), context)
                    INC_TEMPERATURE -> handleIncrementTemperature(request.header, mapper.readValue<IncrementTargetTemperatureRequest>(request.payload), context)
                    DEC_TEMPERATURE -> handleDecrementTemperature(request.header, mapper.readValue<DecrementTargetTemperatureRequest>(request.payload), context)
                    DEC_PERCENTAGE -> handleDecrementPercentage(request.header, mapper.readValue<DecrementPercentageRequest>(request.payload), context)
                    INC_PERCENTAGE -> handleIncrementPercentage(request.header, mapper.readValue<IncrementPercentageRequest>(request.payload), context)
                    SET_PERCENTAGE -> handleSetPercentage(request.header, mapper.readValue<SetPercentageRequest>(request.payload), context)
                    TURN_ON -> handleTurnOn(request.header, mapper.readValue<TurnOnRequest>(request.payload), context)
                    TURN_OFF -> handleTurnOff(request.header, mapper.readValue<TurnOffRequest>(request.payload), context)
                    else -> throw UnsupportedOperationError(request.header, "Unknown name: ${request.header.name}")
                }
        )
    }

    override fun onSystem(request: SmartHomeRequest, context: Context): SmartHomeReply {
        return SmartHomeReply(request.header,
                when (request.header.name) {
                    HEALTH_CHECK -> handleHealthCheck(request.header, mapper.readValue<HealthCheckRequest>(request.payload), context)
                    else -> throw UnsupportedOperationError(request.header, "Unknown name: ${request.header.name}")
                })
    }

    companion object {
        val GET_LOCK_STATE = "GetLockStateRequest"
        val SET_LOCK_STATE = "SetLockStateRequest"

        val GET_TARGET_TEMPERATURE = "GetTargetTemperatureRequest"
        val SET_TARGET_TEMPERATURE = "SetTargetTemperatureRequest"
        val INC_TEMPERATURE = "IncrementTargetTemperatureRequest"
        val DEC_TEMPERATURE = "DecrementTargetTemperatureRequest"

        val GET_TEMPERATURE_READING = "GetTemperatureReadingRequest"

        val HEALTH_CHECK = "HealthCheckRequest"

        val DEC_PERCENTAGE = "DecrementPercentageRequest"
        val INC_PERCENTAGE = "IncrementPercentageRequest"
        val SET_PERCENTAGE = "SetPercentageRequest"

        val TURN_ON = "TurnOnRequest"
        val TURN_OFF = "TurnOffRequest"
    }
}

private inline fun <reified T> ObjectMapper.readValue(payload: JsonNode): T {
    return treeToValue(payload, T::class.java)
}
