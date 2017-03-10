package com.chimerapps.easypus.alexa.model

/**
 * @author Nicola Verbeeck
 * @date 09/03/2017.
 */
data class SmartHomeReply(val header: SmartHomeHeader, val payload: Any)

data class StringValue(val value: String)


//DISCOVERY
class DiscoverAppliancesResponse(discoveredAppliances: Collection<DiscoveredAppliance>)

data class DiscoveredAppliance(
        val actions: Collection<String>,
        val additionalApplianceDetails: Map<String, String>,
        val applianceId: String,
        val friendlyDescription: String,
        val friendlyName: String,
        val isReachable: Boolean,
        val manufacturerName: String,
        val modelName: String,
        val version: String
)

//QUERY
data class GetLockStateResponse(val lockState: String, val applianceResponseTimestamp: String?)

data class GetTargetTemperatureResponse(val targetTemperature: Value?, val coolingTargetTemperature: Value?, val heatingTargetTemperature: Value?, val temperatureMode: TemperatureMode, val applianceResponseTimestamp: String?)
data class GetTemperatureReadingResponse(val targetTemperature: Value, val applianceResponseTimestamp: String?)

data class TemperatureMode(val value: String, val friendlyName: String?)

//CONTROL - TEMPERATURE
data class SetTargetTemperatureConfirmation(val previousState: PreviousTemperature, val targetTemperature: Value, val temperatureMode: TemperatureMode)
typealias IncrementTargetTemperatureConfirmation = SetTargetTemperatureConfirmation
typealias DecrementTargetTemperatureConfirmation = SetTargetTemperatureConfirmation

data class PreviousTemperature(val mode: StringValue, val targetTemperature: Value)

//CONTROL - LOCK
data class SetLockStateConfirmation(val lockState: String)

//CONTROL - Percentage
class DecrementPercentageConfirmation()

class IncrementPercentageConfirmation()
class SetPercentageConfirmation()

//CONTROL - Turn on/off
class TurnOnConfirmation()

class TurnOffConfirmation()

//SYSTEM - Health
data class HealthCheckResponse(val isHealthy: Boolean, val description: String)