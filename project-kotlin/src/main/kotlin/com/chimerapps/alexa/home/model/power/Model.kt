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

package com.chimerapps.alexa.home.model.power

import com.chimerapps.alexa.home.ActionHandler
import com.chimerapps.alexa.home.error.UnsupportedOperationError
import com.chimerapps.alexa.home.model.*
import java.util.*

/**
 * @author Nicola Verbeeck
 * @date 12/11/2017.
 */
typealias TurnOnPayload = EmptyPayload
typealias TurnOffPayload = EmptyPayload

data class PowerState(val value: PowerValue, val uncertaintyInMilliseconds: Long, val time: Date) {
    enum class PowerValue {
        ON, OFF
    }

    fun toProperty(): Property {
        return Property(
                namespace = AlexaPowerController.namespace,
                name = "powerState",
                timeOfSample = time,
                uncertaintyInMilliseconds = uncertaintyInMilliseconds,
                value = value.name
        )
    }
}

interface PowerController {

    fun turnOn(token: String, endpointId: String, cookie: Map<String, String?>): List<Property>

    fun turnOff(token: String, endpointId: String, cookie: Map<String, String?>): List<Property>

}

class PowerControlHandler(val delegate: PowerController) : ActionHandler<EmptyPayload> {

    override fun handleAction(directive: Directive,
                              controller: Controller,
                              action: String,
                              payload: EmptyPayload): EventWithContext {
        val token = directive.endpoint!!.scope.asType<Scopes.BearerScope>(Scope.ScopeType.BEARER).token
        val id = directive.endpoint.endpointId
        val cookie = directive.endpoint.cookie ?: emptyMap()

        val result = when (action) {
            AlexaPowerController.NAME_TURN_ON -> delegate.turnOn(token, id, cookie)
            AlexaPowerController.NAME_TURN_OFF -> delegate.turnOff(token, id, cookie)
            else -> throw UnsupportedOperationError(directive, "$action not supported")
        }

        return EventWithContext(
                event = Event(header = directive.header.toResponseWithNewId("Response").copy(namespace = "Alexa"),
                        payload = EmptyPayload(),
                        endpoint = directive.endpoint.copy(cookie = null)),
                context = Context(result))
    }

}