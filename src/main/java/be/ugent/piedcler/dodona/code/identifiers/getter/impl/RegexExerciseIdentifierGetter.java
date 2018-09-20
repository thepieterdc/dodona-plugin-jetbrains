package be.ugent.piedcler.dodona.code.identifiers.getter.impl;

import be.ugent.piedcler.dodona.code.identifiers.getter.ExerciseIdentifierGetter;
import be.ugent.piedcler.dodona.dto.Course;
import be.ugent.piedcler.dodona.dto.Exercise;
import be.ugent.piedcler.dodona.dto.Series;
import be.ugent.piedcler.dodona.dto.Solution;
import be.ugent.piedcler.dodona.services.CourseService;
import be.ugent.piedcler.dodona.services.ExerciseService;
import be.ugent.piedcler.dodona.services.SeriesService;
import gherkin.formatter.model.Match;
import gherkin.lexer.Pa;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;

public class RegexExerciseIdentifierGetter implements ExerciseIdentifierGetter {

	private final Pattern groupRegex;
	private final Pattern seriesRegex;
	private final Pattern exerciseRegex;

	public RegexExerciseIdentifierGetter(Pattern groupRegex, Pattern seriesRegex, Pattern exerciseRegex) {
		this.groupRegex = groupRegex;
		this.seriesRegex = seriesRegex;
		this.exerciseRegex = exerciseRegex;
	}

	/**
	 * Identifies the current exercise given some code.
	 *
	 * @param code the code to process
	 * @return the exercise found
	 */
	@NotNull
	public Optional<Solution> identify(@NotNull final CharSequence code) {
		final Matcher groupMatcher = groupRegex.matcher(code);
		final Matcher seriesMatcher = seriesRegex.matcher(code);
		final Matcher exerciseMatcher = exerciseRegex.matcher(code);

		final Optional<String> courseId = groupMatcher.matches() ? ofNullable(groupMatcher.group(0)) : empty();
		final Optional<String> seriesId = seriesMatcher.matches() ? ofNullable(seriesMatcher.group(0)) : empty();
		final Optional<String> exerciseId = exerciseMatcher.matches() ? ofNullable(exerciseMatcher.group(0)) : empty();

		final CourseService courseService = CourseService.getInstance();
		final SeriesService seriesService = SeriesService.getInstance();
		final ExerciseService exerciseService = ExerciseService.getInstance();

		final Optional<Course> optCourse = courseId.map(Long::parseLong).map(courseService::get);
		final Optional<Series> optSeries = seriesId.map(Long::parseLong).map(seriesService::get);
		final Optional<Exercise> optExercise = exerciseId.map(Long::parseLong).map(exerciseService::get);

		// exercise is necessary the rest is optional!
		return optExercise.flatMap(
			ex -> of(new Solution(optCourse.orElse(null), optSeries.orElse(null), ex))
		);

	}
}
