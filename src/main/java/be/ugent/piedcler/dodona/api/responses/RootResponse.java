/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.api.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The response from querying the root (/).
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RootResponse {
	private final UserResponse user;
	
	/**
	 * RootResponse constructor.
	 *
	 * @param user the user
	 */
	public RootResponse(@JsonProperty("user") final UserResponse user) {
		this.user = user;
	}
	
	/**
	 * Gets the user response.
	 *
	 * @return the user response
	 */
	public UserResponse getUser() {
		return this.user;
	}
}
