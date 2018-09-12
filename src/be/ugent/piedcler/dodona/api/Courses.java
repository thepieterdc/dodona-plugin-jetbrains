/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.api;

import be.ugent.piedcler.dodona.Configuration;
import be.ugent.piedcler.dodona.api.responses.RootResponse;
import be.ugent.piedcler.dodona.dto.course.Course;

import java.util.Collection;

/**
 * Utilities to fetch courses from Dodona.
 */
public enum Courses {
	;
	
	/**
	 * Gets all courses the user is subscribed to (synchronously).
	 *
	 * @return the courses
	 */
	public static Collection<Course> getAllSubscribed() {
		return Http.get(Configuration.DODONA_URL, RootResponse.class).getUser().getSubscribedCourses();
	}
}
