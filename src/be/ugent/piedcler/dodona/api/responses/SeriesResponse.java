/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.api.responses;

import be.ugent.piedcler.dodona.dto.course.CourseImpl;
import be.ugent.piedcler.dodona.dto.course.UnknownCourse;
import be.ugent.piedcler.dodona.dto.Series;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

/**
 * The response from fetching a series.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SeriesResponse {
	private final
	private final long id;
	private final String name;
	
	/**
	 * SeriesResponse constructor.
	 *
	 * @param course_id the id of the course of the series
	 * @param exercises the exercises in this series
	 * @param id        the id of the series
	 */
	public SeriesResponse(@JsonProperty("id") final long id,
	                      @JsonProperty("exercises") final Collection<CourseImpl> series) {
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
