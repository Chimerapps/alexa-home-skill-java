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

package com.chimerapps.alexa.home.model.percentage

import com.chimerapps.alexa.home.ActionHandler
import com.chimerapps.alexa.home.error.UnsupportedOperationError
import com.chimerapps.alexa.home.model.*
import java.util.*


/**
 * @param percentage Range: [0, 100]
 */
data class SetPercentagePayload(
        /**
         * Range: [0, 100]
         */
        val percentage: Int
)

/**
 * @param percentageDelta Range: [-100, 100]
 */
data class AdjustPercentagePayload(
        /**
         * Range: [-100, 100]
         */
        val percentageDelta: Int
)

data class PercentageState(val value: Int, val uncertaintyInMilliseconds: Long, val time: Date) {
    fun toProperty(): Property {
        return Property(
                namespace = AlexaPercentageController.namespace,
                name = "percentage",
                timeOfSample = time,
                uncertaintyInMilliseconds = uncertaintyInMilliseconds,
                value = value
        )
    }
}


interface PercentageController {

    fun setPercentage(token: String, endpointId: String, percentage: Int): List<Property>

    fun adjustPercentage(token: String, endpointId: String, delta: Int): List<Property>

}

class PercentageControlHandler(val delegate: PercentageController) : ActionHandler<Any> {

    override fun handleAction(directive: Directive,
                              controller: Controller,
                              action: String,
                              payload: Any): EventWithContext {
        val token = directive.endpoint!!.scope.asType<Scopes.BearerScope>(Scope.ScopeType.BEARER).token
        val id = directive.endpoint.endpointId

        val result = when (action) {
            AlexaPercentageController.NAME_SET_PERCENTAGE -> delegate.setPercentage(token, id, (payload as SetPercentagePayload).percentage)
            AlexaPercentageController.NAME_ADJUST_PERCENTAGE -> delegate.adjustPercentage(token, id, (payload as AdjustPercentagePayload).percentageDelta)
            else -> throw UnsupportedOperationError(directive, "$action not supported")
        }

        return EventWithContext(
                event = Event(header = directive.header.toResponseWithNewId("Response").copy(namespace = "Alexa"),
                        payload = EmptyPayload(),
                        endpoint = directive.endpoint.copy(cookie = null)),
                context = Context(result))
    }

}