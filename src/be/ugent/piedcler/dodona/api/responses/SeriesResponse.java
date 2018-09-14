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
	private final long courseId;
	private final Collection<ExerciseResponse> exercises;
	private final long id;
	private final String name;
	
	/**
	 * SeriesResponse constructor.
	 *
	 * @param courseId  the id of the course of the series
	 * @param exercises the exercises in this series
	 * @param id        the id of the series
	 * @param name      the name of the series
	 */
	public SeriesResponse(@JsonProperty("course_id") final long courseId,
	                      @Nullable @JsonProperty("exercises") final Collection<ExerciseResponse> exercises,
	                      @JsonProperty("id") final long id,
	                      @JsonProperty("name") final String name) {
		this.courseId = courseId;
		this.exercises = Optional.ofNullable(exercises).orElseGet(() -> new HashSet<>(30));
		this.id = id;
		this.name = name;
	}
	
	/**
	 * Converts the series response to a series.
	 *
	 * @return the series
	 */
	public Series toSeries() {
		final Course course = CourseService.getInstance().get(this.courseId);
		final Collection<Exercise> convertedExercises = this.exercises.stream()
				.map(ExerciseResponse::toExercise)
				.collect(Collectors.toSet());
		return new SeriesImpl(course, this.id, this.name).setExercises(convertedExercises);
	}
}
