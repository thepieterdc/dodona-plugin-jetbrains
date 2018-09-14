/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.exceptions.errors;

import be.ugent.piedcler.dodona.exceptions.ErrorMessageException;

/**
 * Something went wrong while submitting the solution.
 */
public class SubmissionException extends ErrorMessageException {
	private static final long serialVersionUID = 7925403987834655938L;
	
	/**
	 * SubmissionException constructor.
	 */
	public SubmissionException() {
		super("Something went wrong while submitting your solution.");
	}
}
