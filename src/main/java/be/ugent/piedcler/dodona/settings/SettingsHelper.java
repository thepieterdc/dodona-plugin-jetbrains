/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.settings;

import com.intellij.ide.util.PropertiesComponent;
import org.jetbrains.annotations.NonNls;

/**
 * Provides access to the plugin settings.
 */
public enum SettingsHelper {
	;
	
	@NonNls
	private static final String SETTING_API_KEY = "apiKey";
	
	/**
	 * Gets the api key stored in the IDE settings.
	 *
	 * @return the api key
	 */
	@NonNls
	public static String getApiKey() {
		final PropertiesComponent properties = PropertiesComponent.getInstance();
		return properties.getValue(SettingsHelper.SETTING_API_KEY, "");
	}
	
	/**
	 * Sets the api key and stores it in the settings.
	 *
	 * @param apiKey the api key to set
	 */
	public static void setApiKey(final String apiKey) {
		final PropertiesComponent properties = PropertiesComponent.getInstance();
		properties.setValue(SettingsHelper.SETTING_API_KEY, apiKey);
	}
}
