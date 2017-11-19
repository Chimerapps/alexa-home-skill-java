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

package com.chimerapps.alexa.home.model.powerlevel

import com.chimerapps.alexa.home.model.AlexaPowerLevelController
import com.chimerapps.alexa.home.model.Property
import java.util.*

/**
 * @param powerLevel Range: [0, 100]
 */
data class SetPowerLevelPayload(
        /**
         * Range: [0, 100]
         */
        val powerLevel: Int
)

/**
 * @param powerLevelDelta Range: [-100, 100]
 */
data class AdjustPowerLevelPayload(
        /**
         * Range: [-100, 100]
         */
        val powerLevelDelta: Int
)

data class PowerLevelState(val value: Int, val uncertaintyInMilliseconds: Long, val time: Date) {

    fun toProperty(): Property {
        return Property(
                namespace = AlexaPowerLevelController.namespace,
                name = "powerLevel",
                timeOfSample = time,
                uncertaintyInMilliseconds = uncertaintyInMilliseconds,
                value = value
        )
    }

}
