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
import io.github.thepieterdc.dodona.resources.Exercise;
import io.github.thepieterdc.dodona.resources.Series;

import javax.annotation.Nonnull;

/**
 * Full identification of an exercise.
 */
public final class FullIdentification {
	private final Course course;
	private final Exercise exercise;
	private final Series series;
	
	/**
	 * FullIdentification constructor.
	 *
	 * @param course   the course
	 * @param series   the series
	 * @param exercise the exercise
	 */
	public FullIdentification(final Course course,
	                          final Series series,
	                          final Exercise exercise) {
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
	public Course getCourse() {
		return this.course;
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
	public Series getSeries() {
		return this.series;
	}
	
	@Override
	public String toString() {
		return String.format(
			"FullIdentification[course=%s, series=%s, exercise=%s]",
			this.course.getId(),
			this.series.getId(),
			this.exercise.getId()
		);
	}
}
