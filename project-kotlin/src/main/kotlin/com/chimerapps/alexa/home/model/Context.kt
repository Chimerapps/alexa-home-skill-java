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

import com.fasterxml.jackson.annotation.JsonFormat
import java.util.*

/**
 * @author Nicola Verbeeck
 * @date 07/11/2017.
 */
data class Context(val properties: List<Property>)

data class Property(val namespace: String,
                    val name: String,
                    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-DD'T'hh:mm:ss.SS'Z'", timezone = "UTC")
                    val timeOfSample: Date,
                    val uncertaintyInMilliseconds: Long,
                    val value: Any)
