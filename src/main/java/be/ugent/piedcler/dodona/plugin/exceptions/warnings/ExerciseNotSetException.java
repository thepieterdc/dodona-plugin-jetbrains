/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.exceptions.warnings;

import be.ugent.piedcler.dodona.plugin.exceptions.WarningMessageException;

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
