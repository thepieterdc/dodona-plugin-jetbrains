/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.apiclient.responses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A solution to submit to Dodona.
 */
@JsonRootName("submission")
public final class Solution {

	@JsonProperty("code")
	private String code;
	@JsonProperty("course_id")
	private final Long courseId;
	@JsonProperty("series_id")
	private final Long seriesId;
	@JsonProperty("exercise_id")
	private final Long exerciseId;

	/**
	 * Solution constructor.
	 *
	 * @param courseId   the courseId
	 * @param seriesId   the seriesId
	 * @param exerciseId the exerciseId
	 * @param code       the code to submit
	 */
	public Solution(@Nullable @JsonProperty("course_id") final Long courseId,
					@Nullable @JsonProperty("series_id") final Long seriesId,
					@NotNull @JsonProperty("exercise_id") final Long exerciseId,
					@JsonProperty("code") final String code) {
		this.code = code;
		this.courseId = courseId;
		this.seriesId = seriesId;
		this.exerciseId = exerciseId;
	}

	/**
	 * Solution constructor.
	 *
	 * @param courseId   the courseId
	 * @param seriesId   the seriesId
	 * @param exerciseId the exerciseId
	 */
	public Solution(@Nullable @JsonProperty("course_id") final Long courseId,
					@Nullable @JsonProperty("series_id") final Long seriesId,
					@NotNull @JsonProperty("exercise_id") final Long exerciseId) {
		this(courseId, seriesId, exerciseId, "");
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
	 * Gets the courseId.
	 *
	 * @return the courseId
	 */
	public Long getCourseId() {
		return this.courseId;
	}

	/**
	 * Gets the exerciseId.
	 *
	 * @return the exerciseId
	 */
	@NotNull
	public Long getExerciseId() {
		return this.exerciseId;
	}

	/**
	 * Gets the seriesId.
	 *
	 * @return the seriesId
	 */
	public Long getSeriesId() {
		return this.seriesId;
	}


	/**
	 * Sets the code of the solution.
	 *
	 * @param code the code
	 * @return fluent setter
	 */
	public Solution setCode(final String code) {
		this.code = code;
		return this;
	}

}
