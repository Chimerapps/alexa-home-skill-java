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

/**
 * @author Nicola Verbeeck
 * Date 10/03/2017.
 */
public class SmartHomeReply {

	private final SmartHomeHeader header;
	private final ResponsePayload payload;

	public SmartHomeReply(final SmartHomeHeader header,
	                      final ResponsePayload payload) {
		this.header = header.makeHeader(payload.getName());
		this.payload = payload;
	}

	public SmartHomeHeader getHeader() {
		return header;
	}

	public ResponsePayload getPayload() {
		return payload;
	}
}
