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

import com.chimerapps.alexa.home.model.ResponsePayload;
import com.chimerapps.alexa.home.model.Value;

/**
 * @author Nicola Verbeeck
 * @date 10/03/2017.
 */
public class GetTemperatureReadingResponse extends ResponsePayload {

	private final Value targetTemperature;
	private final String applianceResponseTimestamp;

	public GetTemperatureReadingResponse(final Value targetTemperature, final String applianceResponseTimestamp) {
		super("GetTemperatureReadingResponse");
		this.targetTemperature = targetTemperature;
		this.applianceResponseTimestamp = applianceResponseTimestamp;
	}

	public Value getTargetTemperature() {
		return targetTemperature;
	}

	public String getApplianceResponseTimestamp() {
		return applianceResponseTimestamp;
	}
}