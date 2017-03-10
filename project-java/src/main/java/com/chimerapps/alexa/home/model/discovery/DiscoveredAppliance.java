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

package com.chimerapps.alexa.home.model.discovery;

import com.chimerapps.alexa.home.model.Appliance;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;
import java.util.Map;

/**
 * @author Nicola Verbeeck
 * @date 10/03/2017.
 */
public class DiscoveredAppliance extends Appliance {
	private final Collection<String> actions;
	private final String friendlyDescription;
	private final String friendlyName;
	private final boolean isReachable;
	private final String manufacturerName;
	private final String modelName;
	private final String version;

	@JsonCreator
	public DiscoveredAppliance(
			@JsonProperty("actions") final Collection<String> actions,
			@JsonProperty("additionalApplianceDetails") final Map<String, String> additionalApplianceDetails,
			@JsonProperty("applianceId") final String applianceId,
			@JsonProperty("friendlyDescription") final String friendlyDescription,
			@JsonProperty("friendlyName") final String friendlyName,
			@JsonProperty("isReachable") final boolean isReachable,
			@JsonProperty("manufacturerName") final String manufacturerName,
			@JsonProperty("modelName") final String modelName,
			@JsonProperty("version") final String version) {
		super(applianceId, additionalApplianceDetails);
		this.actions = actions;
		this.friendlyDescription = friendlyDescription;
		this.friendlyName = friendlyName;
		this.isReachable = isReachable;
		this.manufacturerName = manufacturerName;
		this.modelName = modelName;
		this.version = version;
	}

	public Collection<String> getActions() {
		return actions;
	}

	public String getFriendlyDescription() {
		return friendlyDescription;
	}

	public String getFriendlyName() {
		return friendlyName;
	}

	public boolean isReachable() {
		return isReachable;
	}

	public String getManufacturerName() {
		return manufacturerName;
	}

	public String getModelName() {
		return modelName;
	}

	public String getVersion() {
		return version;
	}
}
