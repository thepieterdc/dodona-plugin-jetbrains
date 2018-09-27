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
public final class Api {
	private static final String USER_AGENT = "DodonaPlugin/1.1.0";
	
	@Nullable
	private static DodonaClient instance;
	
	/**
	 * Removes the instance of the dodona client, may be called by the
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
	 * @return
	 */
	public static DodonaClient getInstance() {
		if (instance == null) {
			instance = DodonaBuilder.builder()
				.setApiToken(SettingsHelper.getApiKey())
				.setBaseUrl(SettingsHelper.getDodonaURL())
				.setUserAgent(USER_AGENT)
				.build();
		}
		return instance;
	}
}
