/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.dto.exercise;

import be.ugent.piedcler.dodona.dto.Exercise;

/**
 * Implementation class of Exercise.
 */
public final class ExerciseImpl implements Exercise {
	private final String boilerplate;
	private final long id;
	private final String name;
	
	/**
	 * ExerciseImpl constructor.
	 *
	 * @param id          the id of the course
	 * @param name        the name of the course
	 * @param boilerplate the boilerplate code
	 */
	public ExerciseImpl(final long id, final String name, final String boilerplate) {
		this.boilerplate = boilerplate;
		this.id = id;
		this.name = name;
	}
	
	@Override
	public String getBoilerplate() {
		return this.boilerplate;
	}
	
	@Override
	public long getId() {
		return this.id;
	}
	
	@Override
	public String getName() {
		return this.name;
	}
	
	@Override
	public String toString() {
		return String.format("Exercise{id=%d}", this.id);
	}
}
