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
 * @date 10/03/2017.
 */
public class SmartHomeHeader {

	private final String messageId;
	private String name;
	private final String namespace;
	private final String payloadVersion;

	@JsonCreator
	public SmartHomeHeader(@JsonProperty("messageId") final String messageId,
	                       @JsonProperty("name") final String name,
	                       @JsonProperty("namespace") final String namespace,
	                       @JsonProperty("payloadVersion") String payloadVersion) {
		this.messageId = messageId;
		this.name = name;
		this.namespace = namespace;
		this.payloadVersion = payloadVersion;
	}

	public String getMessageId() {
		return messageId;
	}

	public String getName() {
		return name;
	}

	public String getNamespace() {
		return namespace;
	}

	public String getPayloadVersion() {
		return payloadVersion;
	}

	public void setName(String name) {
		this.name = name;
	}
}
