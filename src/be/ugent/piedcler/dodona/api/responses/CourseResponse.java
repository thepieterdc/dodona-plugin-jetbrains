/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.api.responses;

import be.ugent.piedcler.dodona.dto.course.UnknownCourse;
import be.ugent.piedcler.dodona.dto.series.Series;
import be.ugent.piedcler.dodona.dto.series.UnknownSeries;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

/**
 * The response from fetching a course.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CourseResponse {
	private final long id;
	private final Collection<Series> series;
	
	/**
	 * CourseResponse constructor.
	 *
	 * @param series the exercise series of this course
	 */
	public CourseResponse(@JsonProperty("id") final long id,
	                      @JsonProperty("series") final Collection<UnknownSeries> series) {
		series.forEach(unknownSeries -> unknownSeries.setCourse(new UnknownCourse(id)));
		
		this.id = id;
		this.series = new HashSet<>(series);
	}
	
	/**
	 * Gets the series of the course.
	 *
	 * @return the series
	 */
	public Collection<Series> getSeries() {
		return Collections.unmodifiableCollection(this.series);
	}
}
