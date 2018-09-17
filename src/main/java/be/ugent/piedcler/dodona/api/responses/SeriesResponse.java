/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.api.responses;

import be.ugent.piedcler.dodona.dto.Course;
import be.ugent.piedcler.dodona.dto.Exercise;
import be.ugent.piedcler.dodona.dto.Series;
import be.ugent.piedcler.dodona.dto.series.SeriesImpl;
import be.ugent.piedcler.dodona.services.CourseService;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The response from fetching a series.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SeriesResponse {
	private final long id;
	private final String name;
	
	/**
	 * SeriesResponse constructor.
	 *
	 * @param id        the id of the series
	 * @param name      the name of the series
	 */
	public SeriesResponse(@JsonProperty("id") final long id,
	                      @JsonProperty("name") final String name) {
		this.id = id;
		this.name = name;
	}
	
	/**
	 * Converts the series response to a series.
	 *
	 * @return the series
	 */
	public Series toSeries() {
		return new SeriesImpl(this.id, this.name);
	}
}
