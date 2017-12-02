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

package com.chimerapps.alexa.home.model.brightness

import com.chimerapps.alexa.home.ActionHandler
import com.chimerapps.alexa.home.error.UnsupportedOperationError
import com.chimerapps.alexa.home.model.*
import java.util.*

/**
 * @param brightness Range: [0, 100]
 */
data class SetBrightnessPayload(
        /**
         * Range: [0, 100]
         */
        val brightness: Int
)

/**
 * @param brightnessDelta Range: [-100, 100]
 */
data class AdjustBrightnessPayload(
        /**
         * Range: [-100, 100]
         */
        val brightnessDelta: Int
)

data class BrightnessState(val value: Int, val uncertaintyInMilliseconds: Long, val time: Date) {
    fun toProperty(): Property {
        return Property(
                namespace = AlexaBrightnessController.namespace,
                name = "brightness",
                timeOfSample = time,
                uncertaintyInMilliseconds = uncertaintyInMilliseconds,
                value = value
        )
    }
}

interface BrightnessController {

    fun setBrightness(token: String, endpointId: String, brightness: Int): List<Property>

    fun adjustBrightness(token: String, endpointId: String, delta: Int): List<Property>

}

class BrightnessControlHandler(val delegate: BrightnessController) : ActionHandler<Any> {

    override fun handleAction(directive: Directive,
                              controller: Controller,
                              action: String,
                              payload: Any): EventWithContext {
        val token = directive.endpoint!!.scope.asType<Scopes.BearerScope>(Scope.ScopeType.BEARER).token
        val id = directive.endpoint.endpointId

        val result = when (action) {
            AlexaBrightnessController.NAME_SET -> delegate.setBrightness(token, id, (payload as SetBrightnessPayload).brightness)
            AlexaBrightnessController.NAME_ADJUST -> delegate.adjustBrightness(token, id, (payload as AdjustBrightnessPayload).brightnessDelta)
            else -> throw UnsupportedOperationError(directive, "$action not supported")
        }

        return EventWithContext(
                event = Event(header = directive.header.toResponseWithNewId("Response").copy(namespace = "Alexa"),
                        payload = EmptyPayload(),
                        endpoint = directive.endpoint.copy(cookie = null)),
                context = Context(result))
    }

}