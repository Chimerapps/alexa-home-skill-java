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

package com.chimerapps.alexa.home.model.temperature

import com.chimerapps.alexa.home.ActionHandler
import com.chimerapps.alexa.home.error.UnsupportedOperationError
import com.chimerapps.alexa.home.model.*
import java.util.*

/**
 * @author Nicola Verbeeck
 * @date 12/11/2017.
 */
typealias TemperatureSensorPayload = EmptyPayload

data class Temperature(val value: Double, val scale: Scale) {
    enum class Scale {
        CELSIUS,
        FAHRENHEIT,
        KELVIN
    }
}

data class SetTargetTemperaturePayload(
        val targetSetpoint: Temperature?,
        val lowerSetpoint: Temperature?,
        val upperSetpoint: Temperature?
)

data class AdjustTargetTemperaturePayload(
        val targetSetpointDelta: Temperature
)

data class ThermostatMode(
        val value: ThermostatValue,
        val customName: String?
) {
    enum class ThermostatValue {
        AUTO,
        COOL,
        ECO,
        HEAT,
        OFF,
        CUSTOM
    }
}

data class SetThermostatModePayload(
        val thermostatMode: ThermostatMode
)

open class TemperatureState(val value: Temperature,
                            val uncertaintyInMilliseconds: Long,
                            val time: Date,
                            val key: String = "temperature",
                            val namespace: String = AlexaTemperatureSensor.namespace) {
    fun toProperty(): Property {
        return Property(
                namespace = namespace,
                name = key,
                timeOfSample = time,
                uncertaintyInMilliseconds = uncertaintyInMilliseconds,
                value = value
        )
    }
}

class TargetSetpointState(value: Temperature,
                          uncertaintyInMilliseconds: Long,
                          time: Date)
    : TemperatureState(value, uncertaintyInMilliseconds, time,
        key = "targetSetpoint",
        namespace = AlexaThermostatController.namespace)

class LowerSetpointState(value: Temperature,
                         uncertaintyInMilliseconds: Long,
                         time: Date)
    : TemperatureState(value, uncertaintyInMilliseconds, time,
        key = "lowerSetpoint",
        namespace = AlexaThermostatController.namespace)

class UpperSetpointState(value: Temperature,
                         uncertaintyInMilliseconds: Long,
                         time: Date)
    : TemperatureState(value, uncertaintyInMilliseconds, time,
        key = "upperSetpoint",
        namespace = AlexaThermostatController.namespace)

data class ThermostatModeState(val mode: ThermostatMode, val uncertaintyInMilliseconds: Long, val time: Date) {
    fun toProperty(): Property {
        return Property(
                namespace = AlexaThermostatController.namespace,
                name = "thermostatMode",
                timeOfSample = time,
                uncertaintyInMilliseconds = uncertaintyInMilliseconds,
                value = mode
        )
    }
}

interface TemperatureSensorController {

    fun getTemperature(token: String, endpointId: String): List<Property>

}

interface ThermostatController {

    fun setTargetTemperature(token: String, endpointId: String, target: Temperature): List<Property>

    fun setTargetTemperature(token: String, endpointId: String,
                             lower: Temperature, upper: Temperature): List<Property> {
        return emptyList()
    }

    fun setTargetTemperature(token: String, endpointId: String,
                             target: Temperature, lower: Temperature, upper: Temperature): List<Property> {
        return emptyList()
    }

    fun adjustTargetTemperature(token: String, endpointId: String, delta: Temperature): List<Property>

    fun setThermostatMode(token: String, endpointId: String, thermostatMode: ThermostatMode): List<Property>
}

class TemperatureSensorControlHandler(val delegate: TemperatureSensorController) : ActionHandler<Any> {

    override fun handleAction(directive: Directive,
                              controller: Controller,
                              action: String,
                              payload: Any): EventWithContext {
        val token = directive.endpoint!!.scope.asType<Scopes.BearerScope>(Scope.ScopeType.BEARER).token
        val id = directive.endpoint.endpointId

        val result = when (action) {
            AlexaTemperatureSensor.NAME_REPORT_STATE -> delegate.getTemperature(token, id)
            else -> throw UnsupportedOperationError(directive, "$action not supported")
        }

        return EventWithContext(
                event = Event(header = directive.header.toResponseWithNewId("Response").copy(namespace = "Alexa"),
                        payload = EmptyPayload(),
                        endpoint = directive.endpoint.copy(cookie = null)),
                context = Context(result))
    }

}

class ThermostatControlHandler(val delegate: ThermostatController) : ActionHandler<Any> {

    override fun handleAction(directive: Directive,
                              controller: Controller,
                              action: String,
                              payload: Any): EventWithContext {
        val token = directive.endpoint!!.scope.asType<Scopes.BearerScope>(Scope.ScopeType.BEARER).token
        val id = directive.endpoint.endpointId
        val result = when (action) {
            AlexaThermostatController.NAME_SET_THERMOSTAT_MODE ->
                delegate.setThermostatMode(token, id, (payload as SetThermostatModePayload).thermostatMode)
            AlexaThermostatController.NAME_ADJUST_TARGET_TEMPERATURE ->
                delegate.adjustTargetTemperature(token, id,
                        (payload as AdjustTargetTemperaturePayload).targetSetpointDelta)
            AlexaThermostatController.NAME_SET_TARGET_TEMPERATURE ->
                delegateSet(directive, token, id, payload as SetTargetTemperaturePayload)

            else -> throw UnsupportedOperationError(directive, "$action not supported")
        }
        return EventWithContext(
                event = Event(header = directive.header.toResponseWithNewId("Response").copy(namespace = "Alexa"),
                        payload = EmptyPayload(),
                        endpoint = directive.endpoint.copy(cookie = null)),
                context = Context(result))
    }

    private fun delegateSet(directive: Directive,
                            token: String,
                            id: String,
                            payload: SetTargetTemperaturePayload): List<Property> {
        if (payload.lowerSetpoint != null && payload.upperSetpoint != null) {
            return if (payload.targetSetpoint != null)
                delegate.setTargetTemperature(token,
                        id,
                        target = payload.targetSetpoint,
                        lower = payload.lowerSetpoint,
                        upper = payload.upperSetpoint)
            else
                delegate.setTargetTemperature(token,
                        id,
                        lower = payload.lowerSetpoint,
                        upper = payload.upperSetpoint)
        } else if (payload.targetSetpoint != null) {
            return delegate.setTargetTemperature(token,
                    id,
                    target = payload.targetSetpoint)
        }
        throw UnsupportedOperationError(directive, "Got set temperature with invalid payload: $payload")
    }

}