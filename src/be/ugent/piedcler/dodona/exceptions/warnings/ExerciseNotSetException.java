/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.exceptions.warnings;

import be.ugent.piedcler.dodona.exceptions.WarningMessageException;

/**
 * Thrown when the course and/or exercise id are not found in the code.
 */
public class ExerciseNotSetException extends WarningMessageException {
	private static final long serialVersionUID = -8013381022764225379L;
	
	/**
	 * ExerciseNotSetException constructor.
	 */
	public ExerciseNotSetException() {
		super("The course and/or exercise id were not set in your code.");
	}
}
