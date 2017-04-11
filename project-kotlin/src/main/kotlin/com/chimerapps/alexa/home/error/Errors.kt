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

import com.chimerapps.alexa.home.model.CurrentModePayload
import com.chimerapps.alexa.home.model.DeviceMode
import com.chimerapps.alexa.home.model.ResponsePayload
import com.chimerapps.alexa.home.model.SmartHomeHeader

/**
 * @author Nicola Verbeeck
 * Date 09/03/2017.
 */
open class SmartHomeError(val header: SmartHomeHeader, val errorName: String, message: String?, cause: Throwable?, val payload: ResponsePayload? = null) : Exception(message, cause)

class DriverInternalError(header: SmartHomeHeader, message: String?, cause: Throwable? = null) : SmartHomeError(header, "DriverInternalError", message, cause)

class ExpiredAccessTokenError(header: SmartHomeHeader, message: String, cause: Throwable? = null) : SmartHomeError(header, "ExpiredAccessTokenError", message, cause)

class InvalidAccessTokenError(header: SmartHomeHeader, message: String, cause: Throwable? = null) : SmartHomeError(header, "InvalidAccessTokenError", message, cause)

class UnsupportedTargetError(header: SmartHomeHeader, message: String, cause: Throwable? = null) : SmartHomeError(header, "UnsupportedTargetError", message, cause)

class UnsupportedOperationError(header: SmartHomeHeader, message: String, cause: Throwable? = null) : SmartHomeError(header, "UnsupportedOperationError", message, cause)

class NotSupportedInCurrentModeError(header: SmartHomeHeader, message: String, mode: DeviceMode, cause: Throwable? = null)
    : SmartHomeError(header, "NotSupportedInCurrentModeError", message, cause, CurrentModePayload("NotSupportedInCurrentModeError", mode.name))