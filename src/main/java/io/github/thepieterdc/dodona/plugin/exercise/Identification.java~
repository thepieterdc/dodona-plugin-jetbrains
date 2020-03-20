/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.exercise;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

/**
 * Identification of an exercise.
 */
public class Identification {
	@Nullable
	private final Long courseId;
	private final Long exerciseId;
	@Nullable
	private final Long seriesId;
	
	/**
	 * Identification constructor.
	 *
	 * @param courseId   the id of the course
	 * @param seriesId   the id of the series
	 * @param exerciseId the id of the exercise
	 */
	public Identification(@Nullable final Long courseId,
	                      @Nullable final Long seriesId,
	                      final Long exerciseId) {
		this.courseId = courseId;
		this.exerciseId = exerciseId;
		this.seriesId = seriesId;
	}
	
	/**
	 * Gets the course id.
	 *
	 * @return the course id
	 */
	@Nonnull
	public Optional<Long> getCourseId() {
		return Optional.ofNullable(this.courseId);
	}
	
	/**
	 * Gets the exercise id.
	 *
	 * @return the exercise id
	 */
	@Nonnull
	public Long getExerciseId() {
		return this.exerciseId;
	}
	
	/**
	 * Gets the series id.
	 *
	 * @return the series id
	 */
	@Nonnull
	public Optional<Long> getSeriesId() {
		return Optional.ofNullable(this.seriesId);
	}
}
