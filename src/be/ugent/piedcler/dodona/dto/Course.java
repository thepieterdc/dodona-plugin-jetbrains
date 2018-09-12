/*
 * Copyright (c) 2018 - Singular-IT. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Julie De Meyer
 * @author Robbe Vanhaesebroeck
 *
 * https://www.limpio.online/
 */
package be.ugent.piedcler.dodona.dto;

/**
 * DTO: A course on Dodona.
 */
public class Course {
	private final long id;
	private final String name;
	
	/**
	 * Course constructor.
	 *
	 * @param id   the id of the course
	 * @param name the name of the course
	 */
	public Course(final long id, final String name) {
		this.id = id;
		this.name = name;
	}
}
