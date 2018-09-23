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
//
//	/**
//	 * The solution was correct.
//	 */
//	@JsonProperty("correct")
//	CORRECT,
//
//	/**
//	 * The solution was incorrect.
//	 */
//	@JsonProperty("wrong")
//	WRONG,
//
//	/**
//	 * The time limit was exceeded
//	 */
//	@JsonProperty("time limit exceeded")
//	TIMOUT_ERROR,
//
//	/**
//	 * The solution experienced a runtime error.
//	 */
//	@JsonProperty("runtime error")
//	RUNTIME_ERROR,
//
//	/**
//	 * The solution experience a compilation error.
//	 */
//	@JsonProperty("compilation error")
//	COMPILATION_ERROR,
//
//	/**
//	 * The solution has not yet been evaluated.
//	 */
//	@JsonProperty("running")
//	RUNNING;
	;
	public static final String CORRECT = "correct";
	public static final String WRONG = "wrong";
	public static final String TIMEOUT = "time limit exceeded";
	public static final String RUNTIME_ERROR = "runtime error";
	public static final String COMPILATION_ERROR = "compilation error";
	public static final String RUNNING = "running";
	public static final String QUEUED = "queued";

}
