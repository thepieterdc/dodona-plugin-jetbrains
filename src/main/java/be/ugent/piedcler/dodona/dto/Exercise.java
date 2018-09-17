/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.dto;

import be.ugent.piedcler.dodona.settings.SettingsHelper;
import org.jetbrains.annotations.NonNls;

/**
 * An exercise for Dodona.
 */
public interface Exercise extends Resource {
	@NonNls
	String ENDPOINT_ID = "/exercises/%d";
	
	/**
	 * Gets the boilerplate code.
	 *
	 * @return the boilerplate code
	 */
	String getBoilerplate();
	
	/**
	 * Gets the name of the exercise.
	 *
	 * @return the name
	 */
	String getName();
	
	@Override
	default String getUrl() {
		return getUrl(this.getId());
	}
	
	/**
	 * Gets the url to an exercise.
	 *
	 * @param id the id of the exercise
	 * @return the url
	 */
	static String getUrl(final long id) {
		return SettingsHelper.getDodonaURL(ENDPOINT_ID, id);
	}
}
