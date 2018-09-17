/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.dto.exercise;

import be.ugent.piedcler.dodona.dto.Exercise;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * Implementation class of Exercise.
 */
public final class ExerciseImpl implements Exercise {
	@Nullable
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
	public ExerciseImpl(final long id, final String name, @Nullable final String boilerplate) {
		this.boilerplate = boilerplate;
		this.id = id;
		this.name = name;
	}
	
	@Override
	public Optional<String> getBoilerplate() {
		return Optional.ofNullable(this.boilerplate);
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
