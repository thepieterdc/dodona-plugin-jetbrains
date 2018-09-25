/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.apiclient.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;

import java.util.*;

import static be.ugent.piedcler.dodona.plugin.StringUtils.removeJsonExtention;

/**
 * The response from fetching the user profile.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public final class User {
	private final String url;
	private final List<Course> subscribedCourses;

	/**
	 * User constructor.
	 *
	 * @param subscribedCourses the subscribed courses of the user
	 */
	public User(
		@Nullable @JsonProperty("url") final String url,
		@Nullable @JsonProperty("subscribed_courses") final List<Course> subscribedCourses) {
		this.url = removeJsonExtention(url);
		this.subscribedCourses = Optional.ofNullable(subscribedCourses).orElseGet(() -> new ArrayList<>(5));
	}

	/**
	 * Gets the subscribed courses of the user.
	 *
	 * @return the subscribed courses
	 */
	public List<Course> getSubscribedCourses() {
		return Collections.unmodifiableList(this.subscribedCourses);
	}

	public String getUrl() {
		return url;
	}
}
