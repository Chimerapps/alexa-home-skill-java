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

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.JsonNode

/**
 * @author Nicola Verbeeck
 * Date 09/03/2017.
 */
data class SmartHomeRequest(val header: SmartHomeHeader, val payload: JsonNode)

data class SmartHomeHeader(val messageId: String, var name: String, val namespace: String, val payloadVersion: String)

//BASE
open class BaseRequestPayload(val accessToken: String)

open class ApplianceRequest(@JsonProperty("accessToken") accessToken: String, val appliance: Appliance) : BaseRequestPayload(accessToken)

//DISCOVERY
class DiscoverAppliancesRequest(@JsonProperty("accessToken") accessToken: String) : BaseRequestPayload(accessToken)

//QUERY
class GetLockStateRequest(@JsonProperty("accessToken") accessToken: String) : BaseRequestPayload(accessToken)

class GetTemperatureReadingRequest(@JsonProperty("accessToken") accessToken: String, @JsonProperty("appliance") appliance: Appliance) : ApplianceRequest(accessToken, appliance)
class GetTargetTemperatureRequest(@JsonProperty("accessToken") accessToken: String, @JsonProperty("appliance") appliance: Appliance) : ApplianceRequest(accessToken, appliance)

//CONTROL - TEMPERATURE
class SetTargetTemperatureRequest(@JsonProperty("accessToken") accessToken: String, @JsonProperty("appliance") appliance: Appliance, val targetTemperature: Value) : ApplianceRequest(accessToken, appliance)

class IncrementTargetTemperatureRequest(@JsonProperty("accessToken") accessToken: String, @JsonProperty("appliance") appliance: Appliance, val deltaTemperature: Value) : ApplianceRequest(accessToken, appliance)
class DecrementTargetTemperatureRequest(@JsonProperty("accessToken") accessToken: String, @JsonProperty("appliance") appliance: Appliance, val deltaTemperature: Value) : ApplianceRequest(accessToken, appliance)

//CONTROL - LOCK
class SetLockStateRequest(@JsonProperty("accessToken") accessToken: String, @JsonProperty("appliance") appliance: Appliance, val lockState: String) : ApplianceRequest(accessToken, appliance)

//CONTROL - Percentage
class DecrementPercentageRequest(@JsonProperty("accessToken") accessToken: String, @JsonProperty("appliance") appliance: Appliance, val deltaPercentage: Value) : ApplianceRequest(accessToken, appliance)

class IncrementPercentageRequest(@JsonProperty("accessToken") accessToken: String, @JsonProperty("appliance") appliance: Appliance, val deltaPercentage: Value) : ApplianceRequest(accessToken, appliance)
class SetPercentageRequest(@JsonProperty("accessToken") accessToken: String, @JsonProperty("appliance") appliance: Appliance, val percentageState: Value) : ApplianceRequest(accessToken, appliance)

//CONTROL - Turn on/off
class TurnOnRequest(@JsonProperty("accessToken") accessToken: String, @JsonProperty("appliance") appliance: Appliance) : ApplianceRequest(accessToken, appliance)

class TurnOffRequest(@JsonProperty("accessToken") accessToken: String, @JsonProperty("appliance") appliance: Appliance) : ApplianceRequest(accessToken, appliance)

//SYSTEM - Health
data class HealthCheckRequest(val initiationTimestamp: String)

//CONTROL - COLOR
class SetColorRequest(@JsonProperty("accessToken") accessToken: String, @JsonProperty("appliance") appliance: Appliance, val color: Color)
    : ApplianceRequest(accessToken, appliance)

class SetColorTemperatureRequest(@JsonProperty("accessToken") accessToken: String, @JsonProperty("appliance") appliance: Appliance, val colorTemperature : IntValue)
    : ApplianceRequest(accessToken, appliance)