/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.plugin.code.identifiers.getter.impl;

import be.ugent.piedcler.dodona.apiclient.responses.Solution;
import be.ugent.piedcler.dodona.plugin.code.identifiers.getter.ExerciseIdentifierGetter;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Optional.of;

public class RegexExerciseIdentifierGetter implements ExerciseIdentifierGetter {

	private final Pattern courseRegex;
	private final Pattern seriesRegex;
	private final Pattern exerciseRegex;

	/**
	 * RegexExerciseIdentifierGetter constructor.
	 *
	 * @param courseRegex   regex used to identify the course id
	 * @param seriesRegex   regex used to identify the series id
	 * @param exerciseRegex regex used to identify the exercise id
	 */
	public RegexExerciseIdentifierGetter(@NotNull final Pattern courseRegex,
										 @NotNull final Pattern seriesRegex,
										 @NotNull final Pattern exerciseRegex) {
		this.courseRegex = courseRegex;
		this.seriesRegex = seriesRegex;
		this.exerciseRegex = exerciseRegex;
	}

	/**
	 * Identifies a resource using the given regex, and fetches it from the service.
	 *
	 * @param code    the code to find the regex in
	 * @param pattern the regex to match against
	 * @return the matched resource if any
	 */
	@NotNull
	private static Optional<Long> identify(@NotNull final CharSequence code,
										   @NotNull final Pattern pattern) {
		return Optional.of(pattern.matcher(code))
			.filter(Matcher::find)
			.map(matcher -> matcher.group(1))
			.map(Long::parseLong);
	}

	/**
	 * Identifies the current exercise given some code.
	 *
	 * @param code the code to process
	 * @return the exercise found
	 */
	@NotNull
	public Optional<Solution> identify(@NotNull final CharSequence code) {
		final Optional<Long> courseId = identify(code, this.courseRegex);
		final Optional<Long> exerciseId = identify(code, this.exerciseRegex);
		final Optional<Long> seriesId = identify(code, this.seriesRegex);

		// Exercise is required, course and series are is optional.
		return exerciseId.flatMap(
			ex -> of(new Solution(courseId.orElse(null), seriesId.orElse(null), ex))
		);
	}
}
