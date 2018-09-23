/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.apiclient.exceptions.notfound;

/**
 * A submission that can not be found.
 */
public class SubmissionNotFoundException extends ResourceNotFoundException {
	private static final long serialVersionUID = 2049032511080978489L;
	
	/**
	 * SubmissionNotFoundException constructor.
	 */
	public SubmissionNotFoundException() {
		super("The requested submission could not be found.");
	}
}
