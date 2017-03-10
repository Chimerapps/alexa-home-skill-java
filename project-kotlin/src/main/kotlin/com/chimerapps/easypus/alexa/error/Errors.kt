package com.chimerapps.easypus.alexa.error

import com.chimerapps.easypus.alexa.model.SmartHomeHeader

/**
 * @author Nicola Verbeeck
 * @date 09/03/2017.
 */
open class SmartHomeError(val header: SmartHomeHeader, val errorName: String, message: String?, cause: Throwable?) : Exception(message, cause)

class DriverInternalError(header: SmartHomeHeader, message: String?, cause: Throwable? = null) : SmartHomeError(header, "DriverInternalError", message, cause)

class ExpiredAccessTokenError(header: SmartHomeHeader, message: String, cause: Throwable? = null) : SmartHomeError(header, "ExpiredAccessTokenError", message, cause)

class InvalidAccessTokenError(header: SmartHomeHeader, message: String, cause: Throwable? = null) : SmartHomeError(header, "InvalidAccessTokenError", message, cause)

class UnsupportedTargetError(header: SmartHomeHeader, message: String, cause: Throwable? = null) : SmartHomeError(header, "UnsupportedTargetError", message, cause)

class UnsupportedOperationError(header: SmartHomeHeader, message: String, cause: Throwable? = null) : SmartHomeError(header, "UnsupportedOperationError", message, cause)