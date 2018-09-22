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

/**
 * The response from fetching a course.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CourseResponse {
	private final long id;
	private final String name;
	private final String teacher;
	private final String color;
	private final String year;
	private final String url;
	
	/**
	 * CourseResponse constructor.
	 *
	 * @param id      the id of the course
	 * @param name    the name of the course
	 * @param teacher the teacher of the course
	 * @param color   the color of the course
	 * @param year    the academic year
	 * @param url     the url to the course
	 */
	public CourseResponse(@JsonProperty("id") final long id,
						  @JsonProperty("name") final String name,
						  @JsonProperty("teacher") final String teacher,
						  @JsonProperty("color") final String color,
						  @JsonProperty("year") final String year,
						  @JsonProperty("url") final String url) {
		this.id = id;
		this.name = name;
		this.teacher = teacher;
		this.color = color;
		this.year = year;
		this.url = url;
	}
	
	/**
	 * Converts the course response to a course.
	 *
	 * @return the course
	 */
	public Course toCourse() {
		return new CourseImpl(this.id, this.name, this.teacher, this.color, this.year, this.url.replace(".json", ""));
	}
}
