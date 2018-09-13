/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.api;

import be.ugent.piedcler.dodona.api.responses.CourseResponse;
import be.ugent.piedcler.dodona.dto.Course;

import java.util.Collection;

/**
 * Utilities to submit exercises to Dodona.
 */
public enum Series {
	;
	
	/**
	 * Gets all exercises in a given course.
	 *
	 * @param course the course
	 * @return the exercises
	 */
	public static Collection<be.ugent.piedcler.dodona.dto.Series> getAll(final Course course) {
		return Http.get(course.getUrl(), CourseResponse.class).getSeries();
	}
}
