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

import com.chimerapps.alexa.home.utils.nextStringOrNull
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.util.*

/**
 * @author Nicola Verbeeck
 * @date 07/11/2017.
 */
data class Header(val namespace: String,
                  val name: String,
                  val messageId: String,
                  val payloadVersion: String,
                  val correlationToken: String?) {
    fun toResponseWithName(name: String): Header {
        return Header(namespace = namespace,
                name = name,
                messageId = messageId,
                correlationToken = correlationToken,
                payloadVersion = payloadVersion)
    }

    fun toResponseWithNewId(name: String): Header {
        return Header(namespace = namespace,
                name = name,
                messageId = UUID.randomUUID().toString(),
                correlationToken = correlationToken,
                payloadVersion = payloadVersion)
    }
}

object HeaderAdapter : TypeAdapter<Header>() {

    override fun read(source: JsonReader): Header {
        source.beginObject()

        var namespace: String? = null
        var name: String? = null
        var messageId: String? = null
        var payloadVersion: String? = null
        var correlationToken: String? = null

        while (source.hasNext()) {
            when (source.nextName()) {
                "namespace" -> namespace = source.nextString()
                "name" -> name = source.nextString()
                "messageId" -> messageId = source.nextString()
                "payloadVersion" -> payloadVersion = source.nextString()
                "correlationToken" -> correlationToken = source.nextStringOrNull()
                else -> source.skipValue()
            }
        }

        source.endObject()

        return Header(namespace!!, name!!, messageId!!, payloadVersion!!, correlationToken)
    }

    override fun write(out: JsonWriter, value: Header) {
        out.beginObject()

        out.name("namespace").value(value.namespace)
        out.name("name").value(value.name)
        out.name("messageId").value(value.messageId)
        out.name("payloadVersion").value(value.payloadVersion)
        out.name("correlationToken").value(value.correlationToken)

        out.endObject()
    }

}