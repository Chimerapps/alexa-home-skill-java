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

package com.chimerapps.alexa.home.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * @author Nicola Verbeeck
 * Date 09/03/2017.
 */
data class SmartHomeReply(val header: SmartHomeHeader, val payload: ResponsePayload)

open class ResponsePayload(@JsonIgnore val name: String)
class EmptyPayload(name: String) : ResponsePayload(name)
class CurrentModePayload(name: String, val currentDeviceMode: String) : ResponsePayload(name)

//DISCOVERY
data class DiscoverAppliancesResponse(val discoveredAppliances: Collection<DiscoveredAppliance>)
    : ResponsePayload("DiscoverAppliancesResponse")

//QUERY
data class GetLockStateResponse(val lockState: String,
                                val applianceResponseTimestamp: String?)
    : ResponsePayload("GetLockStateResponse")

data class GetTargetTemperatureResponse(val targetTemperature: Value?,
                                        val coolingTargetTemperature: Value?,
                                        val heatingTargetTemperature: Value?,
                                        val temperatureMode: TemperatureMode,
                                        val applianceResponseTimestamp: String?)
    : ResponsePayload("GetTargetTemperatureResponse")

data class GetTemperatureReadingResponse(val targetTemperature: Value,
                                         val applianceResponseTimestamp: String?)
    : ResponsePayload("GetTemperatureReadingResponse")

data class TemperatureMode(val value: String, val friendlyName: String?)

//CONTROL - TEMPERATURE
data class SetTargetTemperatureConfirmation(val previousState: PreviousTemperature,
                                            val targetTemperature: Value,
                                            val temperatureMode: TemperatureMode)
    : ResponsePayload("SetTargetTemperatureConfirmation")

data class IncrementTargetTemperatureConfirmation(val previousState: PreviousTemperature,
                                                  val targetTemperature: Value,
                                                  val temperatureMode: TemperatureMode)
    : ResponsePayload("IncrementTargetTemperatureConfirmation")

data class DecrementTargetTemperatureConfirmation(val previousState: PreviousTemperature,
                                                  val targetTemperature: Value,
                                                  val temperatureMode: TemperatureMode)
    : ResponsePayload("DecrementTargetTemperatureConfirmation")


//CONTROL - LOCK
data class SetLockStateConfirmation(val lockState: String)
    : ResponsePayload("SetLockStateConfirmation")

//CONTROL - Percentage
class DecrementPercentageConfirmation
    : ResponsePayload("DecrementPercentageConfirmation")

class IncrementPercentageConfirmation
    : ResponsePayload("IncrementPercentageConfirmation")

class SetPercentageConfirmation
    : ResponsePayload("SetPercentageConfirmation")

//CONTROL - Turn on/off
class TurnOnConfirmation
    : ResponsePayload("TurnOnConfirmation")

class TurnOffConfirmation
    : ResponsePayload("TurnOffConfirmation")

//SYSTEM - Health
data class HealthCheckResponse(@get:JsonProperty("isHealthy") val isHealthy: Boolean,
                               val description: String)
    : ResponsePayload("HealthCheckResponse")


//CONTROL - Color
data class AchievedColorState(val color: Color)

data class AchievedTemperature(val colorTemperature: IntValue)

data class SetColorConfirmationResponse(val achievedState: AchievedColorState)
    : ResponsePayload("SetColorConfirmation")

data class SetColorTemperatureConfirmationResponse(val achievedState: AchievedTemperature)
    : ResponsePayload("SetColorTemperatureConfirmation")

data class IncrementColorTemperatureConfirmation(val achievedState: AchievedTemperature)
    : ResponsePayload("IncrementColorTemperatureConfirmation")

data class DecrementColorTemperatureConfirmation(val achievedState: AchievedTemperature)
    : ResponsePayload("DecrementColorTemperatureConfirmation")