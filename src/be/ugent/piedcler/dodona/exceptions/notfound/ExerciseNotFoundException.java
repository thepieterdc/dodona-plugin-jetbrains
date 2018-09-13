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
 * An exercise that can not be found.
 */
public class ExerciseNotFoundException extends ErrorMessageException {
	private static final long serialVersionUID = -200804810539508993L;
	
	/**
	 * ExerciseNotFoundException constructor.
	 */
	public ExerciseNotFoundException() {
		super("The requested exercise could not be found.");
	}
}
