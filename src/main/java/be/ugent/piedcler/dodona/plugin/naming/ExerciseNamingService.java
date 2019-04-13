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
import java.util.Optional;

/**
 * A naming service for exercises.
 */
@FunctionalInterface
public interface ExerciseNamingService {
	/**
	 * Generates a filename for the given resource.
	 *
	 * @param exercise the exercise to name
	 * @return the generated filename, null if failed
	 */
	@Nonnull
	Optional<String> generateFilename(@Nonnull Exercise exercise);
}
