/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.plugin.dto;

import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Optional;

/**
 * A solution to submit to Dodona.
 */
public final class Solution {
	private String code;
	private final Long courseId;
	private final Long exerciseId;
	private final Long seriesId;
	
	/**
	 * Solution constructor.
	 *
	 * @param courseId   the course id
	 * @param seriesId   the series id
	 * @param exerciseId the exercise id
	 * @param code       the code to submit
	 */
	public Solution(@Nullable final Long courseId,
	                @Nullable final Long seriesId,
	                final Long exerciseId,
	                @Nullable final String code) {
		this.code = code;
		this.courseId = courseId;
		this.exerciseId = exerciseId;
		this.seriesId = seriesId;
	}
	
	/**
	 * Solution constructor.
	 *
	 * @param courseId   the course id
	 * @param seriesId   the series id
	 * @param exerciseId the exercise id
	 */
	public Solution(@Nullable final Long courseId,
	                @Nullable final Long seriesId,
	                final Long exerciseId) {
		this(courseId, seriesId, exerciseId, null);
	}
	
	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	public String getCode() {
		return this.code;
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
	
	/**
	 * Sets the code of the solution.
	 *
	 * @param code the code
	 * @return fluent setter
	 */
	@Nonnull
	public Solution setCode(final String code) {
		this.code = code;
		return this;
	}
}
