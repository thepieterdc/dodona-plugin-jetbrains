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

import java.util.Collection;

/**
 * An exercise series on Dodona.
 */
public interface Series extends Resource {
	@NonNls
	String ENDPOINT_ID = "/series/%d";

	/**
	 * Gets the exercises in this series.
	 *
	 * @return the exercises
	 */
	Collection<Exercise> getExercises();

	/**
	 * Gets the name of the series.
	 *
	 * @return the name
	 */
	String getName();

	@Override
	default String getUrl() {
		return getUrl(this.getId());
	}

	static String getUrl(long id) {
		return SettingsHelper.getDodonaURL(ENDPOINT_ID, id);
	}
}
