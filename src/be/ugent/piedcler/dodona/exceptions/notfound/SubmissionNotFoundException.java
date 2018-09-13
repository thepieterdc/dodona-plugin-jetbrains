/*
 * Copyright (c) 2018 - Singular-IT. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Julie De Meyer
 * @author Robbe Vanhaesebroeck
 *
 * https://www.limpio.online/
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
