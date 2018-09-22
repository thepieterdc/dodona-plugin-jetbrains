/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
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
	CORRECT,
	/**
	 * The solution was incorrect.
	 */
	INCORRECT,
	/**
	 * The solution has not yet been evaluated.
	 */
	PENDING;
}
