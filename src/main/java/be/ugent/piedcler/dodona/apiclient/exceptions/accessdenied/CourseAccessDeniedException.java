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
 * A course that may not be accessed.
 */
public class CourseAccessDeniedException extends ResourceAccessDeniedException {
	private static final long serialVersionUID = 9124313157493828358L;
	
	/**
	 * CourseAccessDeniedException constructor.
	 */
	public CourseAccessDeniedException() {
		super("You are not allowed to access this course.");
	}
}
