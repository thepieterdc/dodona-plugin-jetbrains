/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.plugin;

import be.ugent.piedcler.dodona.DodonaBuilder;
import be.ugent.piedcler.dodona.DodonaClient;
import be.ugent.piedcler.dodona.plugin.settings.SettingsHelper;
import org.jetbrains.annotations.Nullable;

/**
 * Interface to the API.
 */
@Deprecated
public final class Api {
	@Nullable
	private static DodonaClient instance;
	
	/**
	 * Removes the instance of the Dodona client, may be called by the
	 * SettingsHelper.
	 */
	public static void clearInstance() {
		instance = null;
	}
	
	/**
	 * Gets an instance of the Dodona client. This instance may not be saved by
	 * the caller since the instance will become invalid upon changing the
	 * settings.
	 *
	 * @return Dodona instance
	 */
	public static DodonaClient getInstance() {
		if (instance == null) {
			instance = DodonaBuilder.builder()
				.setApiToken(SettingsHelper.getApiKey())
				.setBaseUrl(SettingsHelper.getDodonaUrl())
				.setUserAgent(getUserAgent())
				.build();
		}
		return instance;
	}
	
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
