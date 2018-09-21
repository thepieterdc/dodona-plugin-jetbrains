/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.api.bodies;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("submission")
public class SolutionBody {

	@JsonProperty("code")
	private final String code;

	@JsonProperty("course_id")
	private final Long courseId;

	@JsonProperty("series_id")
	private final Long seriesId;

	@JsonProperty("exercise_id")
	private final Long exerciseId;

	public SolutionBody(final String code,
						final Long courseId,
						final Long seriesId,
						final Long exerciseId) {
		this.code = code;
		this.courseId = courseId;
		this.seriesId = seriesId;
		this.exerciseId = exerciseId;
	}

	public String getCode() {
		return code;
	}

	public Long getCourseId() {
		return courseId;
	}

	public Long getSeriesId() {
		return seriesId;
	}

	public Long getExerciseId() {
		return exerciseId;
	}
}
