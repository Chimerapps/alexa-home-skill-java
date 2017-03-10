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

import java.util.Map;

/**
 * @author Nicola Verbeeck
 * Date 10/03/2017.
 */
public class Appliance {

	private final String applianceId;
	private final Map<String, String> additionalApplianceDetails;

	@JsonCreator
	public Appliance(@JsonProperty("applianceId") final String applianceId,
	                 @JsonProperty("additionalApplianceDetails") final Map<String, String> additionalApplianceDetails) {
		this.applianceId = applianceId;
		this.additionalApplianceDetails = additionalApplianceDetails;
	}

	public String getApplianceId() {
		return applianceId;
	}

	public Map<String, String> getAdditionalApplianceDetails() {
		return additionalApplianceDetails;
	}
}
