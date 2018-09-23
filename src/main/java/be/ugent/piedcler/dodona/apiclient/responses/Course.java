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

/**
 * The response from fetching a course.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public final class Course {

	private final String color;
	private final long id;
	private final String name;
	private final String teacher;
	private final String url;
	private final String series;
	private final String year;

	/**
	 * Course constructor.
	 *
	 * @param color   the color of the course
	 * @param id      the id of the course
	 * @param name    the name of the course
	 * @param teacher the teacher of the course
	 * @param url     the url to the course
	 * @param series  the url to the series
	 * @param year    the academic year
	 */
	public Course(@JsonProperty("color") final String color,
				  @JsonProperty("id") final long id,
				  @JsonProperty("name") final String name,
				  @JsonProperty("teacher") final String teacher,
				  @JsonProperty("url") final String url,
				  @JsonProperty("series") final String series,
				  @JsonProperty("year") final String year) {
		this.color = color;
		this.id = id;
		this.name = name;
		this.teacher = teacher;
		this.url = url;
		this.series = series;
		this.year = year;
	}

	public String getColor() {
		return color;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getTeacher() {
		return teacher;
	}

	public String getUrl() {
		return url;
	}

	public String getSeries() {
		return series;
	}

	public String getYear() {
		return year;
	}

}
