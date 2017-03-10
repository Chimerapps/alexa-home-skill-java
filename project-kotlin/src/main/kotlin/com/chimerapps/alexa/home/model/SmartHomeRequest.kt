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

import com.fasterxml.jackson.databind.JsonNode

/**
 * @author Nicola Verbeeck
 * @date 09/03/2017.
 */
data class SmartHomeRequest(val header: SmartHomeHeader, val payload: JsonNode)

data class SmartHomeHeader(val messageId: String, var name: String, val namespace: String, val payloadVersion: String)

//BASE
open class BaseRequestPayload(val accessToken: String)

open class ApplianceRequest(accessToken: String, val appliance: Appliance) : BaseRequestPayload(accessToken)

//DISCOVERY
class DiscoverAppliancesRequest(accessToken: String) : BaseRequestPayload(accessToken)

//QUERY
class GetLockStateRequest(accessToken: String) : BaseRequestPayload(accessToken)

class GetTemperatureReadingRequest(accessToken: String, appliance: Appliance) : ApplianceRequest(accessToken, appliance)
class GetTargetTemperatureRequest(accessToken: String, appliance: Appliance) : ApplianceRequest(accessToken, appliance)

//CONTROL - TEMPERATURE
class SetTargetTemperatureRequest(accessToken: String, appliance: Appliance, val targetTemperature: Value) : ApplianceRequest(accessToken, appliance)

class IncrementTargetTemperatureRequest(accessToken: String, appliance: Appliance, val deltaTemperature: Value) : ApplianceRequest(accessToken, appliance)
class DecrementTargetTemperatureRequest(accessToken: String, appliance: Appliance, val deltaTemperature: Value) : ApplianceRequest(accessToken, appliance)

//CONTROL - LOCK
class SetLockStateRequest(accessToken: String, appliance: Appliance, val lockState: String) : ApplianceRequest(accessToken, appliance)

//CONTROL - Percentage
class DecrementPercentageRequest(accessToken: String, appliance: Appliance, val deltaPercentage: Value) : ApplianceRequest(accessToken, appliance)

class IncrementPercentageRequest(accessToken: String, appliance: Appliance, val deltaPercentage: Value) : ApplianceRequest(accessToken, appliance)
class SetPercentageRequest(accessToken: String, appliance: Appliance, val percentageState: Value) : ApplianceRequest(accessToken, appliance)

//CONTROL - Turn on/off
class TurnOnRequest(accessToken: String, appliance: Appliance) : ApplianceRequest(accessToken, appliance)

class TurnOffRequest(accessToken: String, appliance: Appliance) : ApplianceRequest(accessToken, appliance)

//SYSTEM - Health
data class HealthCheckRequest(val initiationTimestamp: String)