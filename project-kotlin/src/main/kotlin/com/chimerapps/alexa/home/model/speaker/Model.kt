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

package com.chimerapps.alexa.home.model.speaker

/**
 * @param volume Range: [0, 100]
 */
data class SetVolumePayload(
        /**
         * Range: [0, 100]
         */
        val volume: Int
)

/**
 * @param volume Range: [-100, 100]
 */
data class AdjustVolumePayload(
        /**
         * Range: [-100, 100]
         */
        val volume: Int,
        val volumeDefault: Boolean
)

data class SetMutePayload(
        val mute: Boolean
)

/**
 * @param volumeSteps Range: [-100, 100]
 */
data class StepSpeakerAdjustVolumePayload(
        /**
         * Range: [-100, 100]
         */
        val volumeSteps: Int
)