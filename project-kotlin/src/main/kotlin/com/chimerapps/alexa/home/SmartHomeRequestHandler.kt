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

package com.chimerapps.alexa.home

import com.amazonaws.services.lambda.runtime.Context
import com.chimerapps.alexa.home.error.SmartHomeError
import com.chimerapps.alexa.home.error.UnsupportedOperationError
import com.chimerapps.alexa.home.model.*
import com.chimerapps.alexa.home.model.discovery.DiscoveryResultPayload

/**
 * @author Nicola Verbeeck
 * Date 09/03/2017.
 */
@Suppress("unused", "UNUSED_PARAMETER")
abstract class SmartHomeRequestHandler : RawSmartHomeRequestHandler() {

    private val actionHandlers: MutableList<Pair<String, ActionHandler<*>>> = mutableListOf()

    fun registerActionHandler(controller: Controller, action: String, actionHandler: ActionHandler<*>) {
        actionHandlers.add(Pair(key(controller, action), actionHandler))
    }

    @Throws(SmartHomeError::class)
    protected open fun handleDiscovery(directive: Directive,
                                       accessToken: String,
                                       context: Context)
            : DiscoveryResultPayload = throw UnsupportedOperationError(directive, "Operation not supported")

    @Throws(SmartHomeError::class)
    override fun onDiscovery(request: Directive, context: Context): EventWithContext {
        val actualRequest = DiscoveryPayload.fromPayload(request.payload!!)
        return EventWithContext(
                event = Event(header = request.header.toResponseWithNewId("Discover.Response"),
                        payload = handleDiscovery(request,
                                actualRequest.scope.asType<Scopes.BearerScope>(Scope.ScopeType.BEARER).token, context),
                        endpoint = null),
                context = null
        )
    }

    override fun onControl(controller: Controller, request: Directive, context: Context): EventWithContext {
        val name = request.header.name
        val action = controller.actions.find { it.name == name }
                ?: throw UnsupportedOperationError(request, "$name not supported")

        val actionHandler = getActionHandler(controller, action)
                ?: throw UnsupportedOperationError(request, "$name not supported")

        @Suppress("UNCHECKED_CAST")
        val mapped = gson.fromJson<Any>(request.payload, action.inputType as Class<Any>)
        return actionHandler.handleAction(request, controller, name, mapped)
    }

    protected fun getActionHandler(controller: Controller, action: ControllerAction): ActionHandler<Any>? {
        val key = key(controller, action.name)
        @Suppress("UNCHECKED_CAST")
        return actionHandlers.find { it.first == key }?.second as ActionHandler<Any>?
    }

    companion object {
        private fun key(controller: Controller, action: String): String {
            return controller.namespace + "__+__" + action
        }
    }
}

interface ActionHandler<T> {

    fun handleAction(directive: Directive, controller: Controller, action: String, payload: T): EventWithContext

}