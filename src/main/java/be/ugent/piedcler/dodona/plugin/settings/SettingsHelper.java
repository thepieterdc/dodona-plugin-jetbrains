/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.plugin.settings;

import com.intellij.ide.util.PropertiesComponent;
import org.jetbrains.annotations.NonNls;

/**
 * Provides access to the plugin settings.
 */
public enum SettingsHelper {
	;

	@NonNls
	private static final String SETTING_API_KEY = "apiKey";

	private static final String SETTING_DODONA_URL = "dodonaURL";

	private static final String DEFAULT_DODONA_URL = "https://dodona.ugent.be";

	/**
	 * Gets the apiclient key stored in the IDE settings.
	 *
	 * @return the apiclient key
	 */
	@NonNls
	public static String getApiKey() {
		final PropertiesComponent properties = PropertiesComponent.getInstance();
		return properties.getValue(SettingsHelper.SETTING_API_KEY, "");
	}


	/**
	 * Gets the Dodona URL stored in the IDE settings.
	 *
	 * @return the URL
	 */
	public static String getDodonaURL() {
		final PropertiesComponent properties = PropertiesComponent.getInstance();
		return properties.getValue(SettingsHelper.SETTING_DODONA_URL, DEFAULT_DODONA_URL);
	}


	public static String getDodonaURL(String format, Object... args) {
		return getDodonaURL() + String.format(format, args);
	}

	/**
	 * Sets the apiclient key and stores it in the settings.
	 *
	 * @param apiKey the apiclient key to set
	 */
	public static void setApiKey(final String apiKey) {
		final PropertiesComponent properties = PropertiesComponent.getInstance();
		properties.setValue(SettingsHelper.SETTING_API_KEY, apiKey);
	}

	/**
	 * Sets the dodona url and stores it in the settings.
	 *
	 * @param apiKey the dodona URL to set
	 */
	public static void setDodonaURL(final String apiKey) {
		final PropertiesComponent properties = PropertiesComponent.getInstance();
		properties.setValue(SettingsHelper.SETTING_DODONA_URL, apiKey);
	}
}
