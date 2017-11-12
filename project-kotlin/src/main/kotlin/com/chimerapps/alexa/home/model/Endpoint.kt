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

/**
 * @author Nicola Verbeeck
 * @date 07/11/2017.
 */
data class Endpoint(val scope: Scope,
                    val endpointId: String,
                    val cookie: Map<String, String>)

sealed class Scopes {
    class BearerScope(token: String) : Scope() {
        init {
            put("token", token)
            put("type", Scope.ScopeType.BEARER.typeName)
        }

        val token: String
            get() = get("token")!!
    }
}

open class Scope : HashMap<String, String>() {

    enum class ScopeType(val typeName: String, val creator: ((Scope) -> Any)?) {
        UNKNOWN("", null), BEARER("BearerToken", { it as Scopes.BearerScope })
    }

    fun type(): ScopeType {
        val type = get("type") ?: return ScopeType.UNKNOWN
        return ScopeType.values().find { it.typeName == type } ?: ScopeType.UNKNOWN
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> asType(type: ScopeType): T {
        return type.creator!!.invoke(this) as T
    }

}