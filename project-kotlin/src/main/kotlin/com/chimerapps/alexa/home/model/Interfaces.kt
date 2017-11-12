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

import com.chimerapps.alexa.home.model.brightness.AdjustBrightnessPayload
import com.chimerapps.alexa.home.model.brightness.SetBrightnessPayload
import com.chimerapps.alexa.home.model.camera.InitializeCameraStreamsPayload
import com.chimerapps.alexa.home.model.channel.ChangeChannelPayload
import com.chimerapps.alexa.home.model.channel.SkipChannelsPayload
import com.chimerapps.alexa.home.model.color.DecreaseColorTemperature
import com.chimerapps.alexa.home.model.color.IncreaseColorTemperature
import com.chimerapps.alexa.home.model.color.SetColorPayload
import com.chimerapps.alexa.home.model.color.SetColorTemperaturePayload
import com.chimerapps.alexa.home.model.input.SelectInputPayload
import com.chimerapps.alexa.home.model.lock.LockPayload
import com.chimerapps.alexa.home.model.lock.UnlockPayload
import com.chimerapps.alexa.home.model.percentage.AdjustPercentagePayload
import com.chimerapps.alexa.home.model.percentage.SetPercentagePayload
import com.chimerapps.alexa.home.model.playback.PlaybackPayload
import com.chimerapps.alexa.home.model.power.TurnOffPayload
import com.chimerapps.alexa.home.model.power.TurnOnPayload
import com.chimerapps.alexa.home.model.powerlevel.AdjustPowerLevelPayload
import com.chimerapps.alexa.home.model.powerlevel.SetPowerLevelPayload
import com.chimerapps.alexa.home.model.scene.ActivateScenePayload
import com.chimerapps.alexa.home.model.scene.DeactivateScenePayload
import com.chimerapps.alexa.home.model.speaker.AdjustVolumePayload
import com.chimerapps.alexa.home.model.speaker.SetMutePayload
import com.chimerapps.alexa.home.model.speaker.SetVolumePayload
import com.chimerapps.alexa.home.model.speaker.StepSpeakerAdjustVolumePayload
import com.chimerapps.alexa.home.model.temperature.AdjustTargetTemperaturePayload
import com.chimerapps.alexa.home.model.temperature.SetTargetTemperaturePayload
import com.chimerapps.alexa.home.model.temperature.SetThermostatModePayload
import com.chimerapps.alexa.home.model.temperature.TemperatureSensorPayload

/**
 * @author Nicola Verbeeck
 * @date 08/11/2017.
 */

interface Controller {
    val namespace: String
    val actions: List<ControllerAction>
}

class ControllerAction(val name: String, val inputType: Class<*>)

object AlexaBrightnessController : Controller {
    const val NAME_ADJUST = "AdjustBrightness"
    const val NAME_SET = "SetBrightness"

    override val namespace = "Alexa.BrightnessController"
    override val actions = listOf(ControllerAction(NAME_ADJUST, AdjustBrightnessPayload::class.java),
            ControllerAction(NAME_SET, SetBrightnessPayload::class.java))
}

object AlexaCameraStreamController : Controller {
    const val NAME_INITIALIZE_STREAM = "InitializeCameraStreams"

    override val namespace = "Alexa.CameraStreamController"
    override val actions = listOf(ControllerAction(NAME_INITIALIZE_STREAM, InitializeCameraStreamsPayload::class.java))
}

object AlexaChannelController : Controller {
    const val NAME_CHANGE_CHANNEL = "ChangeChannel"
    const val NAME_SKIP_CHANNELS = "SkipChannels"

    override val namespace = "Alexa.ChannelController"
    override val actions = listOf(ControllerAction(NAME_CHANGE_CHANNEL, ChangeChannelPayload::class.java),
            ControllerAction(NAME_SKIP_CHANNELS, SkipChannelsPayload::class.java))
}

object AlexaColorController : Controller {
    const val NAME_SET_COLOR = "SetColor"

    override val namespace = "Alexa.ColorController"
    override val actions = listOf(ControllerAction(NAME_SET_COLOR, SetColorPayload::class.java))
}

object AlexaColorTemperatureController : Controller {
    const val NAME_DECREASE_TEMPERATURE = "DecreaseColorTemperature"
    const val NAME_INCREASE_TEMPERATURE = "IncreaseColorTemperature"
    const val NAME_SET_TEMPERATURE = "SetColorTemperature"

    override val namespace = "Alexa.ColorTemperatureController"
    override val actions = listOf(ControllerAction(NAME_DECREASE_TEMPERATURE, DecreaseColorTemperature::class.java),
            ControllerAction(NAME_INCREASE_TEMPERATURE, IncreaseColorTemperature::class.java),
            ControllerAction(NAME_SET_TEMPERATURE, SetColorTemperaturePayload::class.java))
}

object AlexaEndpointHealth : Controller {
    override val namespace = "Alexa.EndpointHealth"
    override val actions = emptyList<ControllerAction>()
}

object AlexaInputController : Controller {
    const val NAME_SELECT_INPUT = "SelectInput"

    override val namespace = "Alexa.InputController"
    override val actions = listOf(ControllerAction(NAME_SELECT_INPUT, SelectInputPayload::class.java))
}

object AlexaLockController : Controller {
    const val NAME_LOCK = "Lock"
    const val NAME_UNLOCK = "Unlock"

    override val namespace = "Alexa.LockController"
    override val actions = listOf(ControllerAction(NAME_LOCK, LockPayload::class.java),
            ControllerAction(NAME_UNLOCK, UnlockPayload::class.java))
}

object AlexaPercentageController : Controller {
    const val NAME_SET_PERCENTAGE = "SetPercentage"
    const val NAME_ADJUST_PERCENTAGE = "AdjustPercentage"

    override val namespace = "Alexa.PercentageController"
    override val actions = listOf(ControllerAction(NAME_SET_PERCENTAGE, SetPercentagePayload::class.java),
            ControllerAction(NAME_ADJUST_PERCENTAGE, AdjustPercentagePayload::class.java))
}

object AlexaPlaybackController : Controller {
    const val NAME_PLAY = "Play"
    const val NAME_PAUSE = "Pause"
    const val NAME_STOP = "Stop"
    const val NAME_START_OVER = "StartOver"
    const val NAME_PREVIOUS = "Previous"
    const val NAME_NEXT = "Next"
    const val NAME_REWIND = "Rewind"
    const val NAME_FAST_FORWARD = "FastForward"

    override val namespace = "Alexa.PlaybackController"
    override val actions = listOf(ControllerAction(NAME_PLAY, PlaybackPayload::class.java),
            ControllerAction(NAME_PAUSE, PlaybackPayload::class.java),
            ControllerAction(NAME_STOP, PlaybackPayload::class.java),
            ControllerAction(NAME_START_OVER, PlaybackPayload::class.java),
            ControllerAction(NAME_PREVIOUS, PlaybackPayload::class.java),
            ControllerAction(NAME_NEXT, PlaybackPayload::class.java),
            ControllerAction(NAME_REWIND, PlaybackPayload::class.java),
            ControllerAction(NAME_FAST_FORWARD, PlaybackPayload::class.java))
}

object AlexaPowerController : Controller {
    const val NAME_TURN_ON = "TurnOn"
    const val NAME_TURN_OFF = "TurnOff"

    override val namespace = "Alexa.PowerController"
    override val actions = listOf(ControllerAction(NAME_TURN_ON, TurnOnPayload::class.java),
            ControllerAction(NAME_TURN_OFF, TurnOffPayload::class.java))
}

object AlexaPowerLevelController : Controller {
    const val NAME_SET_POWER_LEVEL = "SetPowerLevel"
    const val NAME_ADJUST_POWER_LEVEL = "AdjustPowerLevel"

    override val namespace = "Alexa.PowerLevelController"
    override val actions = listOf(ControllerAction(NAME_ADJUST_POWER_LEVEL, SetPowerLevelPayload::class.java),
            ControllerAction(NAME_ADJUST_POWER_LEVEL, AdjustPowerLevelPayload::class.java))
}

object AlexaSceneController : Controller {
    const val NAME_ACTIVATE = "Activate"
    const val NAME_DEACTIVATE = "Deactivate"

    override val namespace = "Alexa.SceneController"
    override val actions = listOf(ControllerAction(NAME_ACTIVATE, ActivateScenePayload::class.java),
            ControllerAction(NAME_DEACTIVATE, DeactivateScenePayload::class.java))
}

object AlexaSpeaker : Controller {
    const val NAME_SET_VOLUME = "SetVolume"
    const val NAME_ADJUST_VOLUME = "AdjustVolume"
    const val NAME_SET_MUTE = "SetMute"

    override val namespace = "Alexa.Speaker"
    override val actions = listOf(ControllerAction(NAME_SET_VOLUME, SetVolumePayload::class.java),
            ControllerAction(NAME_ADJUST_VOLUME, AdjustVolumePayload::class.java),
            ControllerAction(NAME_SET_MUTE, SetMutePayload::class.java))
}

object AlexaStepSpeaker : Controller {
    const val NAME_ADJUST_VOLUME = "AdjustVolume"
    const val NAME_SET_MUTE = "SetMute"

    override val namespace = "Alexa.StepSpeaker"
    override val actions = listOf(ControllerAction(NAME_ADJUST_VOLUME, StepSpeakerAdjustVolumePayload::class.java),
            ControllerAction(NAME_SET_MUTE, SetMutePayload::class.java))
}

object AlexaTemperatureSensor : Controller {
    const val NAME_REPORT_STATE = "ReportState"

    override val namespace = "Alexa.TemperatureSensor"
    override val actions = listOf(ControllerAction(NAME_REPORT_STATE, TemperatureSensorPayload::class.java))
}

object AlexaThermostatController : Controller {
    const val NAME_SET_TARGET_TEMPERATURE = "SetTargetTemperature"
    const val NAME_ADJUST_TARGET_TEMPERATURE = "AdjustTargetTemperature"
    const val NAME_SET_THERMOSTAT_MODE = "SetThermostatMode"

    override val namespace = "Alexa.ThermostatController"
    override val actions = listOf(ControllerAction(NAME_SET_TARGET_TEMPERATURE, SetTargetTemperaturePayload::class.java),
            ControllerAction(NAME_ADJUST_TARGET_TEMPERATURE, AdjustTargetTemperaturePayload::class.java),
            ControllerAction(NAME_SET_THERMOSTAT_MODE, SetThermostatModePayload::class.java))
}

val controllers = arrayOf(AlexaBrightnessController,
        AlexaCameraStreamController,
        AlexaChannelController,
        AlexaColorController,
        AlexaColorTemperatureController,
        AlexaEndpointHealth,
        AlexaInputController,
        AlexaLockController,
        AlexaPercentageController,
        AlexaPlaybackController,
        AlexaPowerController,
        AlexaPowerLevelController,
        AlexaSceneController,
        AlexaSpeaker,
        AlexaStepSpeaker,
        AlexaTemperatureSensor,
        AlexaThermostatController)