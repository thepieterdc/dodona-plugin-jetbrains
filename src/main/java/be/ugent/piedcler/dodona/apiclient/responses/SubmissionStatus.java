/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.apiclient.responses;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * The status of a submission.
 */
public enum SubmissionStatus {
	/**
	 * The solution was correct.
	 */
	@JsonProperty("ok")
	CORRECT,
	/**
	 * The solution was incorrect.
	 */
	@JsonEnumDefaultValue()
	INCORRECT,
	/**
	 * The solution has not yet been evaluated.
	 */
	@JsonProperty("running")
	PENDING;
}
