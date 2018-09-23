/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.apiclient.responses;

import com.fasterxml.jackson.annotation.*;

/**
 * The status of a submission.
 */
public enum SubmissionStatus {

	/**
	 * The solution was correct.
	 */
	@JsonProperty("correct")
	CORRECT,

	/**
	 * The solution was incorrect.
	 */
	@JsonEnumDefaultValue
	@JsonProperty("wrong")
	WRONG,

	/**
	 * The time limit was exceeded
	 */
	@JsonProperty("time limit exceeded")
	TIMOUT_ERROR,

	/**
	 * The solution experienced a runtime error.
	 */
	@JsonProperty("runtime error")
	RUNTIME_ERROR,

	/**
	 * The solution experience a compilation error.
	 */
	@JsonProperty("compilation error")
	COMPILATION_ERROR,

	/**
	 * The solution has not yet been evaluated.
	 */
	@JsonProperty("running")
	RUNNING,

	@JsonProperty("queued")
	QUEUED
}
