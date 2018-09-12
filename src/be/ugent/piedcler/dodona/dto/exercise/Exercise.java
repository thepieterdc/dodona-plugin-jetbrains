/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 *
 * https://github.com/thepieterdc/ugent-dodona/
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
