/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
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
	private final boolean lastSolutionCorrect;
	private final boolean hasCorrectSolution;
	private final String url;

	/**
	 * ExerciseImpl constructor.
	 *
	 * @param id                  the id of the course
	 * @param name                the name of the course
	 * @param boilerplate         the boilerplate code
	 * @param lastSolutionCorrect indication if the last submission was correct
	 * @param hasCorrectSolution  indication if there ever was a correct solution submitted
	 */
	public ExerciseImpl(final long id,
						final String name,
						@Nullable final String boilerplate,
						final boolean lastSolutionCorrect,
						final boolean hasCorrectSolution,
						final String url) {
		this.boilerplate = boilerplate;
		this.id = id;
		this.name = name;
		this.lastSolutionCorrect = lastSolutionCorrect;
		this.hasCorrectSolution = hasCorrectSolution;
		this.url = url;
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
	public boolean getLastSolutionCorrect() {
		return lastSolutionCorrect;
	}

	@Override
	public boolean getHasCorrectSolution() {
		return hasCorrectSolution;
	}

	@Override
	public String getUrl() {
		return this.url;
	}

	@Override
	public String toString() {
		return String.format("Exercise{id=%d}", this.id);
	}
}
