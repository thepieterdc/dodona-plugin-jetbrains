/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.dto.series;

import be.ugent.piedcler.dodona.dto.course.Course;
import be.ugent.piedcler.dodona.dto.exercise.Exercise;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;

/**
 * A course of which the exercises were not yet fetched.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public final class UnknownSeries implements Series {
	// initialized right after construction, since the course is not returned from the JSON response
	@SuppressWarnings("InstanceVariableMayNotBeInitialized")
	private Course course;
	
	private final long id;
	private final String name;
	
	/**
	 * UnknownSeries constructor.
	 *
	 * @param id   the id of the series
	 * @param name the name of the name
	 */
	public UnknownSeries(@JsonProperty("id") final long id,
	                     @JsonProperty("name") final String name) {
		this.id = id;
		this.name = name;
	}
	
	@Override
	public Course getCourse() {
		return this.course;
	}
	
	@Override
	public Collection<Exercise> getExercises() {
		return Collections.emptySet();
	}
	
	@Override
	public long getId() {
		return this.id;
	}
	
	@Override
	public String getName() {
		return this.name;
	}
	
	/**
	 * Sets the course.
	 *
	 * @param nw the course to set
	 * @return fluent
	 */
	public Series setCourse(@NotNull final Course nw) {
		this.course = nw;
		return this;
	}
	
	@Override
	public String toString() {
		return String.format("Series{id=%d, course=%d}", this.id, this.course.getId());
	}
}
