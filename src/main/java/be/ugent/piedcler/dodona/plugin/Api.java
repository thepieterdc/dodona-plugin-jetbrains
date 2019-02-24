/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.plugin;

import be.ugent.piedcler.dodona.DodonaClient;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Interface to the API.
 */
public enum Api {
	;
	
	private static final Logger logger = LoggerFactory.getLogger(Api.class);
	
	@Nullable
	private static DodonaClient instance;

//	private static <T> T call(@Nonnull final Project project,
//	                          @Nonnull final DodonaClient client,
//	                          @Nonnull final ProgressIndicator indicator,
//	                          @Nonnull final Function<DodonaClient, T> task) {
//		try {
//			return task.apply(client);
//		} catch (final AuthenticationException ex) {
//			logger.error(ex.getMessage(), ex);
//			final AuthenticationData newAuthData = Authenticator.askCredentials(project, null, indicator);
//			return call(project, indicator, task);
//		}
//	}
	
	/**
	 * Gets the user agent to send in API calls.
	 *
	 * @return the user agent
	 */
	private static String getUserAgent() {
		final String version = BuildConfig.VERSION;
		return String.format("DodonaPlugin/JetBrains-%s", version);
	}
}
