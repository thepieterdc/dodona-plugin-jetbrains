/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.apiclient.exceptions.accessdenied;

/**
 * A submission that may not be accessed.
 */
public class SubmissionAccessDeniedException extends ResourceAccessDeniedException {
	private static final long serialVersionUID = 6756844891927940639L;
	
	/**
	 * SubmissionAccessDeniedException constructor.
	 */
	public SubmissionAccessDeniedException() {
		super("You are not allowed to access this submission.");
	}
}
