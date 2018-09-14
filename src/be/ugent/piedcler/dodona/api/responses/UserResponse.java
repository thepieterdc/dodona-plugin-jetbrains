/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.api.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

/**
 * The response from fetching the user profile.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserResponse {
	private final Collection<CourseResponse> subscribedCourses;
	
	/**
	 * UserResponse constructor.
	 *
	 * @param subscribedCourses the subscribed courses of the user
	 */
	public UserResponse(@Nullable @JsonProperty("subscribed_courses") final Collection<CourseResponse> subscribedCourses) {
		this.subscribedCourses = Optional.ofNullable(subscribedCourses).orElseGet(() -> new HashSet<>(5));
	}
	
	/**
	 * Gets the subscribed courses of the user.
	 *
	 * @return the subscribed courses
	 */
	public Collection<CourseResponse> getSubscribedCourses() {
		return Collections.unmodifiableCollection(this.subscribedCourses);
	}
}
