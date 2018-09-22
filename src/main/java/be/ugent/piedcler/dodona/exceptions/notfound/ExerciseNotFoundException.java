/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.exceptions.notfound;

/**
 * An exercise that can not be found.
 */
public class ExerciseNotFoundException extends ResourceNotFoundException {
	private static final long serialVersionUID = -200804810539508993L;
	
	/**
	 * ExerciseNotFoundException constructor.
	 */
	public ExerciseNotFoundException() {
		super("The requested exercise could not be found.");
	}
}
