/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.exceptions.accessdenied;

/**
 * An exercise that may not be accessed.
 */
public class ExerciseAccessDeniedException extends ResourceAccessDeniedException {
	private static final long serialVersionUID = -444432844370134345L;
	
	/**
	 * ExerciseAccessDeniedException constructor.
	 */
	public ExerciseAccessDeniedException() {
		super("You are not allowed to access this exercise.");
	}
}
