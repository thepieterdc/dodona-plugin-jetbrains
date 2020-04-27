/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.exercise;

import io.github.thepieterdc.dodona.resources.Course;
import io.github.thepieterdc.dodona.resources.activities.Exercise;
import io.github.thepieterdc.dodona.resources.Series;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

/**
 * Full identification of an exercise.
 */
public final class FullIdentification extends Identification {
	@Nullable
	private final Course course;
	private final Exercise exercise;
	@Nullable
	private final Series series;
	
	/**
	 * FullIdentification constructor.
	 *
	 * @param course   the course
	 * @param series   the series
	 * @param exercise the exercise
	 */
	public FullIdentification(@Nullable final Course course,
	                          @Nullable final Series series,
	                          final Exercise exercise) {
		super(
			course != null ? course.getId() : null,
			series != null ? series.getId() : null,
			exercise.getId()
		);
		this.course = course;
		this.exercise = exercise;
		this.series = series;
	}
	
	/**
	 * Gets the course.
	 *
	 * @return the course
	 */
	@Nonnull
	public Optional<Course> getCourse() {
		return Optional.ofNullable(this.course);
	}
	
	/**
	 * Gets the exercise.
	 *
	 * @return the exercise
	 */
	@Nonnull
	public Exercise getExercise() {
		return this.exercise;
	}
	
	/**
	 * Gets the series.
	 *
	 * @return the series
	 */
	@Nonnull
	public Optional<Series> getSeries() {
		return Optional.ofNullable(this.series);
	}
}
