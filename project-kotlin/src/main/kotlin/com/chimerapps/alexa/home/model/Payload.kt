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

import com.google.gson.JsonObject

/**
 * @author Nicola Verbeeck
 * @date 07/11/2017.
 */
data class DiscoveryPayload(val scope: Scopes.BearerScope) {

    companion object {
        fun fromPayload(tree: JsonObject): DiscoveryPayload {
            val scopeObj = tree.get("scope").asJsonObject
            val tokenStr = scopeObj.get("token").asString
            return DiscoveryPayload(Scopes.BearerScope(Scope().apply { put("token", tokenStr) }))
        }
    }

}

class EmptyPayload