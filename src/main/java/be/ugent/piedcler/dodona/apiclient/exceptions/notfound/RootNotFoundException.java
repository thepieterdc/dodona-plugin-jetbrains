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
 * A course that can not be found.
 */
public class RootNotFoundException extends ResourceNotFoundException {
	private static final long serialVersionUID = 8432355491097983811L;

	/**
	 * CourseNotFoundException constructor.
	 */
	public RootNotFoundException() {
		super("The requested root resource could not be found.");
	}
}
