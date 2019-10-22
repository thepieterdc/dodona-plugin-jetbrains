/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.exercise.identification.impl;

import io.github.thepieterdc.dodona.plugin.exercise.Identification;
import io.github.thepieterdc.dodona.plugin.exercise.identification.IdentificationService;
import io.github.thepieterdc.dodona.resources.Course;
import io.github.thepieterdc.dodona.resources.Exercise;
import io.github.thepieterdc.dodona.resources.Series;

import javax.annotation.Nonnull;
import java.util.Optional;

/**
 * Default implementation of an IdentificationService. Uses the API library.
 */
public class IdentificationServiceImpl implements IdentificationService {
	@Nonnull
	@Override
	@SuppressWarnings("HardcodedLineSeparator")
	public Optional<Identification> identify(final String code) {
		// Get the first line of the code.
		final String firstLine = code.split("\n")[0];
		
		// Perform the identification.
		final Long course = Course.getId(firstLine).orElse(null);
		final Long series = Series.getId(firstLine).orElse(null);
		final Optional<Long> exercise = Exercise.getId(firstLine);
		
		// Get the identification.
		return exercise.map(id -> new Identification(course, series, id));
	}
}
