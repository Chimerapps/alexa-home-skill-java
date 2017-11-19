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

package com.chimerapps.alexa.home.model.discovery

/**
 * @author Nicola Verbeeck
 * @date 08/11/2017.
 */
data class DiscoveryResultPayload(val endpoints: Collection<DiscoveryEndpoint>)

data class DiscoveryEndpoint(
        val endpointId: String,
        val manufacturerName: String,
        val friendlyName: String,
        val description: String,
        val displayCategories: Collection<DisplayCategory>,
        val capabilities: Collection<Capability>,
        val cookie: Map<String, String>? = null
)

enum class DisplayCategory {
    ACTIVITY_TRIGGER,
    CAMERA,
    DOOR,
    LIGHT,
    OTHER,
    SCENE_TRIGGER,
    SMARTLOCK,
    SMARTPLUG,
    SPEAKERS,
    SWITCH,
    TEMPERATURE_SENSOR,
    THERMOSTAT,
    TV
}

data class NameValuePair(val name: String)

data class Properties(
        val supported: Collection<NameValuePair>,
        val proactivelyReported: Boolean?,
        val retrievable: Boolean?
)

data class Capability(
        val type: String,
        val `interface`: String,
        val version: String,
        val properties: Properties
)