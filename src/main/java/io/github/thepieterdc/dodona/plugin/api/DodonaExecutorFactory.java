/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.api;

import io.github.thepieterdc.dodona.DodonaBuilder;
import io.github.thepieterdc.dodona.DodonaClient;
import io.github.thepieterdc.dodona.plugin.BuildConfig;
import org.jetbrains.annotations.NonNls;

import javax.annotation.Nonnull;

/**
 * Factory for DodonaExecutors.
 */
public final class DodonaExecutorFactory {
	@NonNls
	private static final String UA_PATTERN = "Plugin/JetBrains-%s";
	
	/**
	 * DodonaRequestFactory constructor.
	 */
	private DodonaExecutorFactory() {
	
	}
	
	/**
	 * Creates a new DodonaExecutor for the given server and token.
	 *
	 * @param server the Dodona server address
	 * @param token  the authentication token
	 * @return the request executor
	 */
	public static DodonaExecutor create(final DodonaServer server, final String token) {
		final DodonaClient client = DodonaBuilder.builder()
			.authenticate(token)
			.setHost(server.getUrl())
			.setUserAgent(DodonaExecutorFactory.getUserAgent())
			.build();
		return new DodonaExecutorImpl(client);
	}
	
	/**
	 * Gets the user agent to send in API calls.
	 *
	 * @return the user agent
	 */
	@NonNls
	@Nonnull
	private static String getUserAgent() {
		return String.format(DodonaExecutorFactory.UA_PATTERN, BuildConfig.VERSION);
	}
}
