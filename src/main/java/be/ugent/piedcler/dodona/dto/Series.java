/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.dto;

import be.ugent.piedcler.dodona.settings.SettingsHelper;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

/**
 * An exercise series on Dodona.
 */
public interface Series extends Resource {
	@NonNls
	String ENDPOINT_ID = "/series/%d";
	
	@NonNls
	String ENDPOINT_EXERCISES_ID = "/series/%d/exercises";
	
	/**
	 * Gets the url to the exercises of a series.
	 *
	 * @param id the id of the series
	 * @return the url to the exercises
	 */
	static String getExercisesUrl(final long id) {
		return SettingsHelper.getDodonaURL(ENDPOINT_EXERCISES_ID, id);
	}
	
	/**
	 * Gets the exercises in this series.
	 *
	 * @return the exercises
	 */
	List<Exercise> getExercises();
	
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
	
	/**
	 * Gets the url to a series.
	 *
	 * @param id the id of the series
	 * @return the url
	 */
	static String getUrl(final long id) {
		return SettingsHelper.getDodonaURL(ENDPOINT_ID, id);
	}
	
	/**
	 * Sets the exercises of this series.
	 *
	 * @param exercises the exercises
	 * @return fluent setter
	 */
	@NotNull
	Series setExercises(Collection<Exercise> exercises);
}
