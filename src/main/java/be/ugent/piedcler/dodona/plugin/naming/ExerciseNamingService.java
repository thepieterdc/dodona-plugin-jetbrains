/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.naming;

import be.ugent.piedcler.dodona.resources.Exercise;

import javax.annotation.Nonnull;

/**
 * A naming service for exercises.
 */
@FunctionalInterface
public interface ExerciseNamingService {
	/**
	 * Generates a filename for the given resource.
	 *
	 * @param exercise the exercise to name
	 * @return the generated filename
	 */
	@Nonnull
	String generateFileName(@Nonnull Exercise exercise);
}
