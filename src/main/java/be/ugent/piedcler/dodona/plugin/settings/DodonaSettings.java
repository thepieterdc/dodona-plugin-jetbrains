/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.settings;

import com.intellij.openapi.components.ServiceManager;

import javax.annotation.Nonnull;

/**
 * Manages the configured settings.
 */
public interface DodonaSettings {
	/**
	 * Gets the configured host.
	 *
	 * @return the host
	 */
	@Nonnull
	String getHost();
	
	/**
	 * Gets an instance of the settings.
	 *
	 * @return instance
	 */
	@Nonnull
	static DodonaSettings getInstance() {
		return ServiceManager.getService(DodonaSettings.class);
	}
	
	/**
	 * Gets the authentication token.
	 *
	 * @return the authentication token
	 */
	@Nonnull
	String getToken();
	
	/**
	 * Gets whether correct exercises should be hidden by default or not.
	 *
	 * @return true if they should be hidden
	 */
	boolean hideCorrectExercises();
	
	/**
	 * Hides or shows correct exercises by default.
	 *
	 * @param hide true to hide exercises
	 */
	void setHideCorrectExercises(boolean hide);
	
	/**
	 * Sets the authentication token.
	 *
	 * @param token the authentication token to set
	 */
	void setToken(@Nonnull String token);
}
