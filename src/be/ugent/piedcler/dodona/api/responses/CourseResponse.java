/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.api.responses;

import be.ugent.piedcler.dodona.dto.Course;
import be.ugent.piedcler.dodona.dto.course.CourseImpl;
import be.ugent.piedcler.dodona.dto.Series;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * The response from fetching a course.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CourseResponse {
	private final long id;
	private final String name;
	private final String teacher
	private final Collection<SeriesResponse> series;
	private final String year;
	
	/**
	 * CourseResponse constructor.
	 *
	 * @param id      the id of the course
	 * @param name    the name of the course
	 * @param teacher the teacher of the course
	 * @param series  the exercise series
	 * @param year    the academic year
	 */
	public CourseResponse(@JsonProperty("id") final long id,
	                      @JsonProperty("name") final String name,
	                      @JsonProperty("teacher") final String teacher,
	                      @JsonProperty("series") final Collection<SeriesResponse> series,
	                      @JsonProperty("year") final String year) {
		this.id = id;
		this.name = name;
		this.teacher = teacher;
		this.series = Collections.unmodifiableCollection(series);
		this.year = year;
	}
	
	/**
	 * Converts the course response to a course.
	 *
	 * @return the course
	 */
	public Course toCourse() {
		final Collection<Series> series = this.series.stream()
				.map(SeriesResponse::toSeries)
				.collect(Collectors.toSet());
		return new CourseImpl(this.id, this.name, this.teacher, this.year).setSeries(series);
	}
}
