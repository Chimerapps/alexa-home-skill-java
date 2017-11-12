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

package com.chimerapps.alexa.home.error

import com.chimerapps.alexa.home.model.Directive

/**
 * @author Nicola Verbeeck
 * Date 09/03/2017.
 */
data class ErrorPayload(val type: String, val message: String)

open class SmartHomeError(val directive: Directive,
                          message: String?,
                          cause: Throwable?,
                          val payload: ErrorPayload) : Exception(message, cause) {
    override fun toString(): String {
        return "SmartHomeError(directive=$directive, payload=$payload). $message $cause"
    }
}

class DriverInternalError(directive: Directive,
                          message: String,
                          cause: Throwable? = null)
    : SmartHomeError(directive, message, cause, ErrorPayload("INTERNAL_ERROR", message))


class ExpiredAccessTokenError(directive: Directive,
                              message: String,
                              cause: Throwable? = null)
    : SmartHomeError(directive, message, cause, ErrorPayload("EXPIRED_AUTHORIZATION_CREDENTIAL", message))

class InvalidAccessTokenError(directive: Directive,
                              message: String,
                              cause: Throwable? = null)
    : SmartHomeError(directive, message, cause, ErrorPayload("INVALID_AUTHORIZATION_CREDENTIAL", message))


class UnsupportedOperationError(directive: Directive,
                                message: String,
                                cause: Throwable? = null)
    : SmartHomeError(directive, message, cause, ErrorPayload("NO_SUCH_ENDPOINT", message))
//
//class NotSupportedInCurrentModeError(directive: Directive, message: String, mode: DeviceMode, cause: Throwable? = null)
//    : SmartHomeError(directive, "NotSupportedInCurrentModeError", message, cause, CurrentModePayload("NotSupportedInCurrentModeError", mode.name))