/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
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
