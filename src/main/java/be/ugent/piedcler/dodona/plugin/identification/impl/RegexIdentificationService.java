/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.identification.impl;

import be.ugent.piedcler.dodona.plugin.identification.Identification;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Performs identification using using regexes.
 */
public class RegexIdentificationService extends AbstractIdentificationService {
	private final Pattern courseRegex;
	private final Pattern seriesRegex;
	private final Pattern exerciseRegex;
	
	/**
	 * RegexIdentificationParser constructor.
	 *
	 * @param courseRegex   regex used to identify the course id
	 * @param seriesRegex   regex used to identify the series id
	 * @param exerciseRegex regex used to identify the exercise id
	 */
	public RegexIdentificationService(@Nonnull final Pattern courseRegex,
	                                  @Nonnull final Pattern seriesRegex,
	                                  @Nonnull final Pattern exerciseRegex) {
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
	@Nonnull
	private static Optional<Long> identify(@Nonnull final CharSequence code,
	                                       @Nonnull final Pattern pattern) {
		return Optional.of(pattern.matcher(code))
			.filter(Matcher::find)
			.map(matcher -> matcher.group(1))
			.map(Long::parseLong);
	}
	
	@Nonnull
	@Override
	public Optional<Identification> identify(@Nonnull final CharSequence code) {
		final Optional<Long> courseId = identify(code, this.courseRegex);
		final Optional<Long> exerciseId = identify(code, this.exerciseRegex);
		final Optional<Long> seriesId = identify(code, this.seriesRegex);
		
		// Exercise is required, course and series are optional.
		return exerciseId.flatMap(ex -> Optional.of(new Identification(
				courseId.orElse(null),
				seriesId.orElse(null),
				ex
			))
		);
	}
}