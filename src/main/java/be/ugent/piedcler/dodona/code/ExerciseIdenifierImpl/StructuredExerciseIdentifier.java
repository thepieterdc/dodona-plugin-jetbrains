/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.code.ExerciseIdenifierImpl;

import java.util.regex.Pattern;

/**
 * Identifies the current exercise from the file.
 */
public class StructuredExerciseIdentifier extends RegexExerciseIdentifier {

	private static final int GROUP_COURSE = 1;
	private static final int GROUP_EXERCISE = 2;

	private static final Pattern regex =
		Pattern.compile("dodona:?\\s*course\\D*(\\d+)\\D*exercise\\D*(\\d+)", Pattern.CASE_INSENSITIVE);

	public StructuredExerciseIdentifier() {
		super(regex, GROUP_COURSE, GROUP_EXERCISE);
	}
}
