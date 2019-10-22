/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.naming.impl;

import io.github.thepieterdc.dodona.plugin.naming.ExerciseNamingService;
import io.github.thepieterdc.dodona.resources.Exercise;
import io.github.thepieterdc.dodona.resources.ProgrammingLanguage;
import org.jetbrains.annotations.NonNls;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Default implementation of an ExerciseNamingService.
 */
public class ExerciseNamingServiceImpl implements ExerciseNamingService {
	private static final String EXT_JAVA = "java";
	
	private static final Pattern JAVA_BOILERPLATE_REGEX = Pattern.compile("class (\\w*)");
	private static final Pattern NON_ALPHA = Pattern.compile("[^a-zA-Z0-9-_. ]", Pattern.CASE_INSENSITIVE);
	private static final Pattern WHITESPACE = Pattern.compile("\\s");
	
	/**
	 * Generates a default filename by cleaning up the exercise name.
	 *
	 * @param exercise the exercise
	 * @return the filename
	 */
	@Nonnull
	private static Optional<String> generateDefaultFilename(@Nonnull final Exercise exercise) {
		final String name = NON_ALPHA.matcher(exercise.getName()).replaceAll("");
		return Optional.of(WHITESPACE.matcher(name))
			.map(matcher -> matcher.replaceAll("_"))
			.filter(s -> !s.isEmpty());
	}
	
	@NonNls
	@Nonnull
	@Override
	public Optional<String> generateFileName(final Exercise exercise) {
		final String extension = exercise.getProgrammingLanguage()
			.map(ProgrammingLanguage::getExtension)
			.orElse("");
		
		if (extension.equalsIgnoreCase(ExerciseNamingServiceImpl.EXT_JAVA)) {
			return generateJavaFilename(exercise).map(s -> s + "." + extension);
		}
		return generateDefaultFilename(exercise).map(s -> s + "." + extension);
	}
	
	/**
	 * Generates a class name given an exercise name.
	 *
	 * @param name the name of the exercise
	 * @return name of the class
	 */
	@NonNls
	@Nonnull
	private static String generateJavaClassName(@Nonnull final CharSequence name) {
		return Arrays.stream(WHITESPACE.split(NON_ALPHA.matcher(name).replaceAll("")))
			.map(s -> s.substring(0, 1) + s.substring(1))
			.collect(Collectors.joining());
	}
	
	/**
	 * Generates a filename for a Java exercise by checking the boilerplate or name.
	 *
	 * @param exercise the exercise
	 * @return the filename
	 */
	@Nonnull
	private static Optional<String> generateJavaFilename(@Nonnull final Exercise exercise) {
		return Optional.of(exercise.getBoilerplate()
			.map(JAVA_BOILERPLATE_REGEX::matcher)
			.filter(Matcher::find)
			.map(matcher -> matcher.group(1))
			.orElseGet(() -> generateJavaClassName(exercise.getName()))
		).filter(s -> !s.isEmpty());
	}
}
