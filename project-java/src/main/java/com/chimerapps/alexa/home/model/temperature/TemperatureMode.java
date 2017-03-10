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

package com.chimerapps.alexa.home.model.temperature;

/**
 * @author Nicola Verbeeck
 * Date 10/03/2017.
 */
public class TemperatureMode {

	private final String value;
	private final String friendlyName;

	public TemperatureMode(final String value, final String friendlyName) {
		this.value = value;
		this.friendlyName = friendlyName;
	}

	public TemperatureMode(final String value) {
		this(value, null);
	}

	public String getValue() {
		return value;
	}

	public String getFriendlyName() {
		return friendlyName;
	}
}
