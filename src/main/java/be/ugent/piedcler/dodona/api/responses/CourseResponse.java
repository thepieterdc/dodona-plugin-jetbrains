/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.api.responses;

import be.ugent.piedcler.dodona.dto.Course;
import be.ugent.piedcler.dodona.dto.course.CourseImpl;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The response from fetching a course.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CourseResponse {
	private static final Pattern JSON_EXTENSION = Pattern.compile(".json", Pattern.LITERAL);
	
	private final String color;
	private final long id;
	private final String name;
	private final String teacher;
	private final String url;
	private final String year;
	
	/**
	 * CourseResponse constructor.
	 *
	 * @param color   the color of the course
	 * @param id      the id of the course
	 * @param name    the name of the course
	 * @param teacher the teacher of the course
	 * @param url     the url to the course
	 * @param year    the academic year
	 */
	public CourseResponse(@JsonProperty("color") final String color,
	                      @JsonProperty("id") final long id,
	                      @JsonProperty("name") final String name,
	                      @JsonProperty("teacher") final String teacher,
	                      @JsonProperty("url") final String url,
	                      @JsonProperty("year") final String year) {
		this.color = color;
		this.id = id;
		this.name = name;
		this.teacher = teacher;
		this.url = url;
		this.year = year;
	}
	
	/**
	 * Converts the course response to a course.
	 *
	 * @return the course
	 */
	public Course toCourse() {
		return new CourseImpl(
			this.id, this.name, this.teacher, this.color, this.year,
			JSON_EXTENSION.matcher(this.url).replaceAll(Matcher.quoteReplacement(""))
		);
	}
}
