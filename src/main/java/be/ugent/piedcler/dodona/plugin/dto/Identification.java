/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.dto;

import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Optional;

/**
 * An identification of an exercise.
 */
public final class Identification {
	private final Long courseId;
	private final Long exerciseId;
	private final Long seriesId;
	
	/**
	 * Identification constructor.
	 *
	 * @param courseId   the course id
	 * @param seriesId   the series id
	 * @param exerciseId the exercise id
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
