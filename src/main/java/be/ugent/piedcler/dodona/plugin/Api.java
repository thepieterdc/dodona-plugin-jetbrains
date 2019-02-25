/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin;

import be.ugent.piedcler.dodona.DodonaBuilder;
import be.ugent.piedcler.dodona.DodonaClient;
import be.ugent.piedcler.dodona.resources.User;

import javax.annotation.Nonnull;

/**
 * Interface to the API.
 */
public enum Api {
	;
	
	
	
	/**
	 * Gets the user agent to send in API calls.
	 *
	 * @return the user agent
	 */
	@Nonnull
	private static String getUserAgent() {
		final String version = BuildConfig.VERSION;
		return String.format("DodonaPlugin/JetBrains-%s", version);
	}
	
	/**
	 * Tests whether the given access token can be used to authenticate.
	 *
	 * @param token the access token to test
	 * @return the user belonging to this access token
	 */
	@Nonnull
	public User testCredentials(final String token) {
		final DodonaClient client = DodonaBuilder.builder()
			.setApiToken(token)
			.setUserAgent(getUserAgent())
			.build();
		
		return client.me();
	}
}
