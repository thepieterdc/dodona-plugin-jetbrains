/*
 * Copyright (c) 2018 - Singular-IT. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Julie De Meyer
 * @author Robbe Vanhaesebroeck
 *
 * https://www.limpio.online/
 */
package be.ugent.piedcler.dodona.dto.course;

/**
 * A course on Dodona.
 */
public interface Course {
	/**
	 * Gets the display name of the course.
	 *
	 * @return the display name
	 */
	String getDisplayName();
	
	/**
	 * Gets the id of the course.
	 *
	 * @return the id
	 */
	long getId();
}
