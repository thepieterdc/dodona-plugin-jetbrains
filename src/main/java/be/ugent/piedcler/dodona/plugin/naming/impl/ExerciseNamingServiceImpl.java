/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.naming.impl;

import be.ugent.piedcler.dodona.plugin.naming.ExerciseNamingService;
import be.ugent.piedcler.dodona.resources.Exercise;
import be.ugent.piedcler.dodona.resources.ProgrammingLanguage;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Implementation of a naming service for exercises.
 */
public class ExerciseNamingServiceImpl implements ExerciseNamingService {
	private static final String EXT_JAVA = "java";
	
	private static final Pattern JAVA_BOILERPLATE_REGEX = Pattern.compile("class (\\w*)");
	
	@Nonnull
	@Override
	public Optional<String> generateFilename(@Nonnull final Exercise exercise) {
		final String extension = exercise.getProgrammingLanguage()
			.map(ProgrammingLanguage::getExtension)
			.orElse("");
		
		if (extension.equalsIgnoreCase(EXT_JAVA)) {
			return generateJavaFilename(exercise).map(s -> s + "." + extension);
		}
		return generateDefaultFilename(exercise).map(s -> s + "." + extension);
	}
	
	/**
	 * Generates a default filename by cleaning up the exercise name.
	 *
	 * @param exercise the exercise
	 * @return the filename
	 */
	@Nonnull
	private static Optional<String> generateDefaultFilename(@Nonnull final Exercise exercise) {
		String name = exercise.getName().toLowerCase(Locale.getDefault());
		name = name.replaceAll("[^a-zA-Z0-9-_. ]", "");
		return Optional.of(name.replaceAll("\\s", "_")).filter(s -> !s.isEmpty());
	}
	
	/**
	 * Generates a class name given an exercise name.
	 *
	 * @param name the name of the exercise
	 * @return name of the class
	 */
	@Nonnull
	private static String generateJavaClassName(@Nonnull final String name) {
		return Arrays.stream(name.replaceAll("[^a-zA-Z0-9_ ]", "").split("\\s"))
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
