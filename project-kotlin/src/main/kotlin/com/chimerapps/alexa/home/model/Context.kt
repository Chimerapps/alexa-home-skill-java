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
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author Nicola Verbeeck
 * @date 07/11/2017.
 */
data class Context(val properties: List<Property>)

data class Property(val namespace: String,
                    val name: String,
                    val timeOfSample: Date,
                    val uncertaintyInMilliseconds: Long,
                    val value: Any)

object PropertyAdapter : TypeAdapter<Property>() {

    private val gson = Gson()
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)

    init {
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"))
    }

    override fun write(out: JsonWriter, value: Property) {
        out.beginObject()
        out.name("namespace").value(value.namespace)
        out.name("name").value(value.name)
        out.name("timeOfSample").value(dateFormat.format(value.timeOfSample))
        out.name("uncertaintyInMilliseconds").value(value.uncertaintyInMilliseconds)

        out.name("value")
        gson.toJson(gson.toJsonTree(value.value), out)
        out.endObject()
    }

    override fun read(`in`: JsonReader?): Property {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

object ContextAdapter : TypeAdapter<Context>() {

    override fun read(source: JsonReader): Context {
        TODO("Not implemented")
    }

    override fun write(out: JsonWriter, value: Context?) {
        if (value == null) {
            out.nullValue()
            return
        }

        out.beginObject()

        out.name("properties")
        value.properties.forEach {
            PropertyAdapter.write(out, it)
        }

        out.endObject()
    }

}