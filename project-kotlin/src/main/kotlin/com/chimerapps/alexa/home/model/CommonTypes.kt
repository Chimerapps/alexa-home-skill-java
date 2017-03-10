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

/**
 * @author Nicola Verbeeck
 * @date 10/03/2017.
 */

data class StringValue(val value: String)

data class DiscoveredAppliance(
        val actions: Collection<String>,
        val additionalApplianceDetails: Map<String, String>,
        val applianceId: String,
        val friendlyDescription: String,
        val friendlyName: String,
        val isReachable: Boolean,
        val manufacturerName: String,
        val modelName: String,
        val version: String)


data class PreviousTemperature(val mode: StringValue,
                               val targetTemperature: Value)

data class Appliance(val applianceId: String,
                     val additionalApplianceDetails: Map<String, String>)

data class Value(val value: Double)
