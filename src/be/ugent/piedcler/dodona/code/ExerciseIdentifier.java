/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.code;

import be.ugent.piedcler.dodona.dto.course.Course;
import be.ugent.piedcler.dodona.dto.course.UnknownCourse;
import be.ugent.piedcler.dodona.dto.exercise.Exercise;
import be.ugent.piedcler.dodona.dto.exercise.UnknownExercise;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Identifies the current exercise from the file.
 */
public enum ExerciseIdentifier {
	;
	
	private static final int GROUP_COURSE = 1;
	private static final int GROUP_EXERCISE = 2;
	
	private static final Pattern regex =
			Pattern.compile("dodona:?\\s*course\\D*(\\d+)\\D*exercise\\D*(\\d+)", Pattern.CASE_INSENSITIVE);
	
	/**
	 * Identifies the current exercise given some code.
	 *
	 * @param code the code to process
	 * @return the exercise found
	 */
	@NotNull
	public static Optional<Exercise> identify(@NotNull final CharSequence code) {
		final Matcher matcher = ExerciseIdentifier.regex.matcher(code);
		if (matcher.find() && (matcher.groupCount() == 2)) {
			final String courseId = matcher.group(ExerciseIdentifier.GROUP_COURSE);
			final String exerciseId = matcher.group(ExerciseIdentifier.GROUP_EXERCISE);
			
			final Course course = new UnknownCourse(Long.parseLong(courseId));
			final Exercise exercise = new UnknownExercise(Long.parseLong(exerciseId), course);
			
			return Optional.of(exercise);
		}
		return Optional.empty();
	}
}
