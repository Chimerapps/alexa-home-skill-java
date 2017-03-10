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

import com.chimerapps.alexa.home.model.Appliance;
import com.chimerapps.alexa.home.model.ApplianceRequest;
import com.chimerapps.alexa.home.model.Value;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Nicola Verbeeck
 * Date 10/03/2017.
 */
public class IncrementTargetTemperatureRequest extends ApplianceRequest {

	private final Value deltaTemperature;

	@JsonCreator
	public IncrementTargetTemperatureRequest(@JsonProperty("accessToken") final String accessToken,
	                                         @JsonProperty("appliance") final Appliance appliance,
	                                         @JsonProperty("deltaTemperature") final Value deltaTemperature) {
		super(accessToken, appliance);
		this.deltaTemperature = deltaTemperature;
	}

	public Value getDeltaTemperature() {
		return deltaTemperature;
	}
}
