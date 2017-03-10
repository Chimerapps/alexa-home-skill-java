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
 *         Date 10/03/2017.
 */
public class DecrementTargetTemperatureConfirmation extends ResponsePayload {

	private final PreviousTemperature previousState;
	private final Value targetTemperature;
	private final TemperatureMode temperatureMode;

	public DecrementTargetTemperatureConfirmation(final PreviousTemperature previousState,
	                                              final Value targetTemperature,
	                                              final TemperatureMode temperatureMode) {
		super("DecrementTargetTemperatureConfirmation");
		this.previousState = previousState;
		this.targetTemperature = targetTemperature;
		this.temperatureMode = temperatureMode;
	}

	public PreviousTemperature getPreviousState() {
		return previousState;
	}

	public Value getTargetTemperature() {
		return targetTemperature;
	}

	public TemperatureMode getTemperatureMode() {
		return temperatureMode;
	}
}
