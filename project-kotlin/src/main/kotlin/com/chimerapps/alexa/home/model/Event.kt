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

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter

/**
 * @author Nicola Verbeeck
 * @date 08/11/2017.
 */
data class Event(val header: Header, val payload: Any, val endpoint: Endpoint?)

data class EventWithContext(val event: Event, val context: Context?)

object EventAdapter : TypeAdapter<Event>() {
    override fun read(`in`: JsonReader?): Event {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun write(out: JsonWriter, value: Event) {
        out.beginObject()

        out.name("header")
        HeaderAdapter.write(out, value.header)

        out.name("endpoint")
        EndpointAdapter.write(out, value.endpoint)

        out.name("payload")
        val gson = Gson()
        gson.toJson(gson.toJsonTree(value.payload), out)

        out.endObject()
    }

}

object EventWithContextAdapter : TypeAdapter<EventWithContext>() {

    override fun read(`in`: JsonReader?): EventWithContext {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun write(out: JsonWriter, value: EventWithContext?) {
        if (value == null) {
            out.nullValue()
            return
        }

        out.beginObject()

        out.name("context")
        ContextAdapter.write(out, value.context)

        out.name("event")
        EventAdapter.write(out, value.event)

        out.endObject()
    }

}