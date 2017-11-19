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
import com.google.gson.JsonParser
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter

/**
 * @author Nicola Verbeeck
 * @date 08/11/2017.
 */
data class Directive(val header: Header, val endpoint: Endpoint?, val payload: JsonObject)

data class DirectiveRequest(val directive: Directive)


object DirectiveAdapter : TypeAdapter<Directive>() {

    override fun read(source: JsonReader): Directive {
        source.beginObject()

        var header: Header? = null
        var endpoint: Endpoint? = null
        var payload: JsonObject? = null

        while (source.hasNext()) {
            when (source.nextName()) {
                "header" -> header = HeaderAdapter.read(source)
                "endpoint" -> endpoint = EndpointAdapter.read(source)
                "payload" -> payload = JsonParser().parse(source).asJsonObject
                else -> source.skipValue()
            }
        }

        source.endObject()

        return Directive(header!!, endpoint, payload!!)
    }

    override fun write(out: JsonWriter?, value: Directive?) {
        TODO("not implemented")
    }
}

object DirectiveRequestAdapter : TypeAdapter<DirectiveRequest>() {

    override fun read(source: JsonReader): DirectiveRequest {
        source.beginObject()

        var directive: Directive? = null

        while (source.hasNext()) {
            when (source.nextName()) {
                "directive" -> directive = DirectiveAdapter.read(source)
                else -> source.skipValue()
            }
        }

        source.endObject()
        return DirectiveRequest(directive!!)
    }

    override fun write(out: JsonWriter?, value: DirectiveRequest?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}