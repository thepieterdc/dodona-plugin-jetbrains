/*
 * Copyright (c) 2018 - Singular-IT. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Julie De Meyer
 * @author Robbe Vanhaesebroeck
 *
 * https://www.limpio.online/
 */
package be.ugent.piedcler.dodona.dto.exercise;

import be.ugent.piedcler.dodona.dto.course.Course;

/**
 * An exercise for Dodona.
 */
public interface Exercise {
	/**
	 * Gets the course this exercise belongs to.
	 *
	 * @return the exercise
	 */
	Course getCourse();
	
	/**
	 * Gets the display name of the exercise.
	 *
	 * @return the display name
	 */
	String getDisplayName();
	
	/**
	 * Gets the id of the exercise.
	 *
	 * @return the id
	 */
	long getId();
}
