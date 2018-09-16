package be.ugent.piedcler.dodona.code.ExerciseIdenifierImpl;

import be.ugent.piedcler.dodona.code.ExerciseIdentifier;
import be.ugent.piedcler.dodona.dto.Course;
import be.ugent.piedcler.dodona.dto.Exercise;
import be.ugent.piedcler.dodona.dto.Solution;
import be.ugent.piedcler.dodona.services.CourseService;
import be.ugent.piedcler.dodona.services.ExerciseService;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexExerciseIdentifier implements ExerciseIdentifier {

	private final int groupCourse;
	private final int groupExercise;
	private final Pattern regex;

	public RegexExerciseIdentifier(Pattern regex, int groupCourse, int groupExercise) {
		this.regex = regex;
		this.groupCourse = groupCourse;
		this.groupExercise = groupExercise;
	}

	/**
	 * Identifies the current exercise given some code.
	 *
	 * @param code the code to process
	 * @return the exercise found
	 */
	@NotNull
	public Optional<Solution> identify(@NotNull final CharSequence code) {
		final Matcher matcher = regex.matcher(code);
		if (matcher.find() && (matcher.groupCount() == 2)) {
			final String courseId = matcher.group(groupCourse);
			final String exerciseId = matcher.group(groupExercise);

			final Course course = CourseService.getInstance().get(Long.parseLong(courseId));
			final Exercise exercise = ExerciseService.getInstance().get(Long.parseLong(exerciseId));

			return Optional.of(new Solution(course, exercise));
		}
		return Optional.empty();
	}
}
