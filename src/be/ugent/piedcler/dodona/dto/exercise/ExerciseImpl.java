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
	private final long id;
	private final String name;
	
	/**
	 * ExerciseImpl constructor.
	 *
	 * @param id      the id of the course
	 * @param name    the name of the course
	 */
	public ExerciseImpl(final long id, final String name) {
		this.id = id;
		this.name = name;
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
