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

package com.chimerapps.alexa.home.model.lock

import com.chimerapps.alexa.home.ActionHandler
import com.chimerapps.alexa.home.error.UnsupportedOperationError
import com.chimerapps.alexa.home.model.*
import java.util.*

/**
 * @author Nicola Verbeeck
 * @date 12/11/2017.
 */
typealias LockPayload = EmptyPayload
typealias UnlockPayload = EmptyPayload

data class LockState(val value: LockValue, val uncertaintyInMilliseconds: Long, val time: Date) {

    enum class LockValue {
        LOCKED, UNLOCKED, JAMMED
    }

    fun toProperty(): Property {
        return Property(
                namespace = AlexaLockController.namespace,
                name = "percentage",
                timeOfSample = time,
                uncertaintyInMilliseconds = uncertaintyInMilliseconds,
                value = value.name
        )
    }
}


interface LockController {

    fun lock(token: String, endpointId: String): List<Property>

    fun unlock(token: String, endpointId: String): List<Property>

}

class LockControlHandler(val delegate: LockController) : ActionHandler<Any> {

    override fun handleAction(directive: Directive,
                              controller: Controller,
                              action: String,
                              payload: Any): EventWithContext {
        val token = directive.endpoint!!.scope.asType<Scopes.BearerScope>(Scope.ScopeType.BEARER).token
        val id = directive.endpoint.endpointId

        val result = when (action) {
            AlexaLockController.NAME_LOCK -> delegate.lock(token, id)
            AlexaLockController.NAME_UNLOCK -> delegate.unlock(token, id)
            else -> throw UnsupportedOperationError(directive, "$action not supported")
        }

        return EventWithContext(
                event = Event(header = directive.header.toResponseWithNewId("Response").copy(namespace = "Alexa"),
                        payload = EmptyPayload(),
                        endpoint = directive.endpoint.copy(cookie = null)),
                context = Context(result))
    }

}