/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.plugin.exceptions.errors;

/**
 * Something went wrong while submitting the solution.
 */
public class SubmissionException extends RuntimeException {
	private static final long serialVersionUID = 7925403987834655938L;
	
	/**
	 * SubmissionException constructor.
	 */
	public SubmissionException() {
		super("Something went wrong while submitting your solution.");
	}
}
