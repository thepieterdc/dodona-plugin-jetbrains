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
 * A course on Dodona.
 */
public interface Course extends Resource {
	@NonNls
	String ENDPOINT_ID = "/courses/%d";

	/**
	 * Gets the name of the course.
	 *
	 * @return the name
	 */
	String getName();

	/**
	 * Gets the series in this course.
	 *
	 * @return the series
	 */
	Collection<Series> getSeries();

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

	static String getUrl(long id) {
		return SettingsHelper.getDodonaURL(ENDPOINT_ID, id);
	}
}
