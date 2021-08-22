/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */
package io.github.thepieterdc.dodona.plugin.exercise.naming;

import com.intellij.openapi.application.ApplicationManager;
import io.github.thepieterdc.dodona.resources.activities.Exercise;

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
	Optional<String> generateFileName(Exercise exercise);
	
	/**
	 * Gets an instance of the ExerciseNamingService.
	 *
	 * @return the instance
	 */
	@Nonnull
	static ExerciseNamingService getInstance() {
		return ApplicationManager.getApplication().getService(ExerciseNamingService.class);
	}
}