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

package com.chimerapps.alexa.home.model.color

import com.chimerapps.alexa.home.model.EmptyPayload

/**
 * @author Nicola Verbeeck
 * @date 12/11/2017.
 */
data class SetColorPayload(val color: Color)

/**
 * @param hue
 * @param saturation Range: [0, 1]
 * @param brightness Range: [0, 1]
 */
data class Color(
        /**
         * Range: [0, 360]
         */
        val hue: Double,
        /**
         * Range: [0, 1]
         */
        val saturation: Double,
        /**
         * Range: [0, 1]
         */
        val brightness: Double)

typealias DecreaseColorTemperature = EmptyPayload
typealias IncreaseColorTemperature = EmptyPayload

/**
 * @param colorTemperatureInKelvin Range: [1000, 10000]
 */
data class SetColorTemperaturePayload(
        /**
         * Range: [1000, 10000]
         */
        val colorTemperatureInKelvin: Long
)
