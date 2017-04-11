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

package com.chimerapps.alexa.home.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Nicola Verbeeck
 * Date 11/04/2017.
 */
public class Color {

	private final double hue;
	private final double saturation;
	private final double brightness;

	@JsonCreator
	public Color(@JsonProperty("hue") final double hue,
	             @JsonProperty("saturation") final double saturation,
	             @JsonProperty("brightness") double brightness) {
		this.hue = hue;
		this.saturation = saturation;
		this.brightness = brightness;
	}

	public double getHue() {
		return hue;
	}

	public double getSaturation() {
		return saturation;
	}

	public double getBrightness() {
		return brightness;
	}
}