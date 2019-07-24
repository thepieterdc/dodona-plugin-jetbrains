/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.exceptions.errors;

import be.ugent.piedcler.dodona.plugin.exceptions.ErrorMessageException;

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
