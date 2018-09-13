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
 * A course that can not be found.
 */
public class CourseNotFoundException extends ErrorMessageException {
	private static final long serialVersionUID = 8432355491097983811L;
	
	/**
	 * CourseNotFoundException constructor.
	 */
	public CourseNotFoundException() {
		super("The requested course could not be found.");
	}
}
