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

package com.chimerapps.alexa.home.utils

import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter

/**
 * @author Nicola Verbeeck
 * Date 10/03/2017.
 */
inline fun <reified T> Gson.readJson(text: String): T {
    return fromJson(text, T::class.java)
}

fun JsonReader.nextStringOrNull(): String? {
    if (peek() == JsonToken.NULL)
        return null
    return nextString()
}

fun JsonReader.readStringMap(): Map<String, String?>? {
    if (peek() == JsonToken.NULL)
        return null

    beginObject()

    val map = hashMapOf<String, String?>()

    while (hasNext()) {
        map.put(nextName(), nextStringOrNull())
    }

    endObject()

    return map
}

fun JsonWriter.value(map: Map<String, String?>?): JsonWriter {
    if (map == null) {
        nullValue()
    } else {
        beginObject()
        map.forEach { name(it.key).value(it.value) }
        endObject()
    }

    return this
}