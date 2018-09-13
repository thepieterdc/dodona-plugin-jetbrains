/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.exceptions.notfound;

import be.ugent.piedcler.dodona.exceptions.ErrorMessageException;

/**
 * A submission that can not be found.
 */
public class SubmissionNotFoundException extends ErrorMessageException {
	private static final long serialVersionUID = 2049032511080978489L;
	
	/**
	 * SubmissionNotFoundException constructor.
	 */
	public SubmissionNotFoundException() {
		super("The requested submission could not be found.");
	}
}
