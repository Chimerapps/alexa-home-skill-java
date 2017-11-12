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

package com.chimerapps.alexa.home.model.camera

/**
 * @author Nicola Verbeeck
 * @date 12/11/2017.
 */
data class Resolution(val width: Int, val height: Int)

data class InitializeCameraStreamsPayload(
        val cameraStreams: List<CameraStream>
)

data class CameraStream(
        val protocol: String,
        val resolution: Resolution,
        val authorizationType: Authorization,
        val videoCodec: VideoCodec,
        val audioCodec: AudioCodec
) {
    enum class Authorization {
        NONE,
        BASIC,
        DIGEST
    }

    enum class VideoCodec {
        H264,
        MPEG2,
        MJPEG,
        JPG
    }

    enum class AudioCodec {
        G711,
        AAC,
        NONE
    }
}
