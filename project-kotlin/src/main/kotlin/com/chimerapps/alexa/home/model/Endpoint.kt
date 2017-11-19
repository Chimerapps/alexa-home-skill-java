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

import com.chimerapps.alexa.home.utils.readStringMap
import com.chimerapps.alexa.home.utils.value
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter

/**
 * @author Nicola Verbeeck
 * @date 07/11/2017.
 */
data class Endpoint(val scope: Scope,
                    val endpointId: String,
                    val cookie: Map<String, String?>?)

sealed class Scopes {
    class BearerScope(scope: Scope) : Scope() {
        init {
            putAll(scope)
        }

        val token: String
            get() = get("token")!!
    }
}

open class Scope : HashMap<String, String?>() {

    enum class ScopeType(val typeName: String, val creator: ((Scope) -> Any)?) {
        UNKNOWN("", null), BEARER("BearerToken", { Scopes.BearerScope(it) })
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

object ScopeAdapter : TypeAdapter<Scope>() {

    override fun read(source: JsonReader): Scope {
        source.beginObject()

        val scope = Scope()
        while (source.hasNext()) {
            scope.put(source.nextName(), source.nextString())
        }

        source.endObject()
        return scope
    }

    override fun write(out: JsonWriter, value: Scope) {
        out.value(value)
    }
}

object EndpointAdapter : TypeAdapter<Endpoint>() {

    override fun read(source: JsonReader): Endpoint {
        source.beginObject()

        var scope: Scope? = null
        var endpointId: String? = null
        var cookie: Map<String, String?>? = null

        while (source.hasNext()) {
            when (source.nextName()) {
                "scope" -> scope = ScopeAdapter.read(source)
                "endpointId" -> endpointId = source.nextString()
                "cookie" -> cookie = source.readStringMap()
                else -> source.skipValue()
            }
        }

        source.endObject()
        return Endpoint(scope!!, endpointId!!, cookie)
    }

    override fun write(out: JsonWriter, value: Endpoint?) {
        if (value == null) {
            out.nullValue()
            return
        }
        out.beginObject()

        out.name("scope")
        ScopeAdapter.write(out, value.scope)

        out.name("endpointId").value(value.endpointId)
        out.name("cookie").value(value.cookie)

        out.endObject()
    }
}