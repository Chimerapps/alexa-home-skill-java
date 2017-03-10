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

package com.chimerapps.alexa.home.error;

import com.chimerapps.alexa.home.model.SmartHomeHeader;

/**
 * @author Nicola Verbeeck
 * @date 10/03/2017.
 */
public class UnsupportedOperationError extends SmartHomeError {

	public UnsupportedOperationError(final SmartHomeHeader header, final String message, final Throwable cause) {
		super(header, "UnsupportedOperationError", message, cause);
	}

	public UnsupportedOperationError(final SmartHomeHeader header, final String message) {
		this(header, message, null);
	}
}
