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
 * A course on Dodona.
 */
public interface Course extends Resource {
	@NonNls
	String ENDPOINT_ID = "/courses/%d";
	
	@NonNls
	String ENDPOINT_SERIES_ID = "/courses/%d/series";
	
	/**
	 * Gets the color of the course.
	 *
	 * @return the color
	 */
	String getColor();
	
	/**
	 * Gets the name of the course.
	 *
	 * @return the name
	 */
	String getName();
	
	/**
	 * Gets the url to the series of a course.
	 *
	 * @param id the id of the course
	 * @return the url to the series
	 */
	static String getSeriesUrl(final long id) {
		return SettingsHelper.getDodonaURL(ENDPOINT_SERIES_ID, id);
	}
	
	/**
	 * Gets the series in this course.
	 *
	 * @return the series
	 */
	List<Series> getSeries();
	
	/**
	 * Gets the teacher of the course.
	 *
	 * @return the teacher
	 */
	String getTeacher();
	
	/**
	 * Gets the academic year this course is taught.
	 *
	 * @return the academic year
	 */
	String getYear();
	
	@Override
	default String getUrl() {
		return getUrl(this.getId());
	}
	
	/**
	 * Gets the url to a course.
	 *
	 * @param id the id of the course
	 * @return the url
	 */
	static String getUrl(final long id) {
		return SettingsHelper.getDodonaURL(ENDPOINT_ID, id);
	}
	
	/**
	 * Sets the exercise series of this course.
	 *
	 * @param series the exercise series
	 * @return fluent setter
	 */
	@NotNull
	Course setSeries(Collection<Series> series);
}
