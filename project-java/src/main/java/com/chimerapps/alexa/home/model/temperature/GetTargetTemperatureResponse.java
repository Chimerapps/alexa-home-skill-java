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
 * Date 10/03/2017.
 */
public class GetTargetTemperatureResponse extends ResponsePayload {

	private final Value targetTemperature;
	private final Value coolingTargetTemperature;
	private final Value heatingTargetTemperature;
	private final TemperatureMode temperatureMode;
	private final String applianceResponseTimestamp;

	public GetTargetTemperatureResponse(
			final Value targetTemperature,
			final Value coolingTargetTemperature,
			final Value heatingTargetTemperature,
			final TemperatureMode temperatureMode,
			final String applianceResponseTimestamp) {
		super("GetTargetTemperatureResponse");
		this.targetTemperature = targetTemperature;
		this.coolingTargetTemperature = coolingTargetTemperature;
		this.heatingTargetTemperature = heatingTargetTemperature;
		this.temperatureMode = temperatureMode;
		this.applianceResponseTimestamp = applianceResponseTimestamp;
	}

	public Value getTargetTemperature() {
		return targetTemperature;
	}

	public Value getCoolingTargetTemperature() {
		return coolingTargetTemperature;
	}

	public Value getHeatingTargetTemperature() {
		return heatingTargetTemperature;
	}

	public TemperatureMode getTemperatureMode() {
		return temperatureMode;
	}

	public String getApplianceResponseTimestamp() {
		return applianceResponseTimestamp;
	}
}
