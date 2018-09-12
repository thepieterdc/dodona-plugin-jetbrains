/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.dto.submission;

/**
 * The status of a submission.
 */
public enum SubmissionStatus {
	/**
	 * The solution was correct.
	 */
	CORRECT("correct"),
	/**
	 * The solution was incorrect.
	 */
	INCORRECT("wrong/compilation error"),
	/**
	 * The solution has not yet been evaluated.
	 */
	PENDING("running");
}
