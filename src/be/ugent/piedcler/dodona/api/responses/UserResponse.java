/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.api.responses;

import be.ugent.piedcler.dodona.dto.course.Course;
import be.ugent.piedcler.dodona.dto.course.KnownCourse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

/**
 * The response from fetching the user profile.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserResponse {
	private final Collection<Course> subscribed_courses;
	
	/**
	 * UserResponse constructor.
	 *
	 * @param subscribedCourses the subscribed courses of the user
	 */
	public UserResponse(@JsonProperty("subscribed_courses") final Collection<KnownCourse> subscribedCourses) {
		this.subscribed_courses = new HashSet<>(subscribedCourses);
	}
	
	/**
	 * Gets the subscribed courses of the user.
	 *
	 * @return the subscribed courses
	 */
	public Collection<Course> getSubscribedCourses() {
		return Collections.unmodifiableCollection(this.subscribed_courses);
	}
}
