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

import java.util.*

/**
 * @author Nicola Verbeeck
 * @date 07/11/2017.
 */
data class DirectiveHeader(val namespace: String,
                           val name: String,
                           val messageId: String,
                           val payloadVersion: String,
                           val correlationToken: String) {
    fun toResponseWithName(name: String): ResponseHeader {
        return ResponseHeader(namespace = namespace,
                name = name,
                messageId = messageId,
                correlationToken = correlationToken,
                payloadVersion = payloadVersion)
    }

    fun toResponseWithNewId(): ResponseHeader {
        return ResponseHeader(namespace = namespace,
                name = name,
                messageId = UUID.randomUUID().toString(),
                correlationToken = correlationToken,
                payloadVersion = payloadVersion)
    }
}

data class ResponseHeader(val namespace: String,
                          val name: String,
                          val messageId: String,
                          val payloadVersion: String,
                          val correlationToken: String? = null)