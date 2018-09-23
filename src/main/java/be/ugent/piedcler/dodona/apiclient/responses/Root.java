/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.apiclient.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The response from querying the root (/).
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public final class Root {
	private final User user;
	
	/**
	 * Root constructor.
	 *
	 * @param user the user
	 */
	public Root(@JsonProperty("user") final User user) {
		this.user = user;
	}
	
	/**
	 * Gets the user response.
	 *
	 * @return the user response
	 */
	public User getUser() {
		return this.user;
	}
}
