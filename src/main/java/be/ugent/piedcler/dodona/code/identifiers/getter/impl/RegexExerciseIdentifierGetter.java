package be.ugent.piedcler.dodona.code.identifiers.getter.impl;

import be.ugent.piedcler.dodona.code.identifiers.getter.ExerciseIdentifierGetter;
import be.ugent.piedcler.dodona.dto.*;
import be.ugent.piedcler.dodona.services.CourseService;
import be.ugent.piedcler.dodona.services.ExerciseService;
import be.ugent.piedcler.dodona.services.ResourceService;
import be.ugent.piedcler.dodona.services.SeriesService;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Optional.of;

public class RegexExerciseIdentifierGetter implements ExerciseIdentifierGetter {
	private static final CourseService courseSrv = CourseService.getInstance();
	private static final ExerciseService exerciseSrv = ExerciseService.getInstance();
	private static final SeriesService seriesSrv = SeriesService.getInstance();
	
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
	 * @param service the resource service
	 * @param <T>     type class of the resource to fetch
	 * @return the matched resource if any
	 */
	@NotNull
	private static <T extends Resource> Optional<T> identify(@NotNull final CharSequence code,
	                                                         @NotNull final Pattern pattern,
	                                                         @NotNull final ResourceService<T> service) {
		return Optional.of(pattern.matcher(code))
			.filter(Matcher::find)
			.map(matcher -> matcher.group(1))
			.map(Long::parseLong)
			.map(service::get);
	}
	
	/**
	 * Identifies the current exercise given some code.
	 *
	 * @param code the code to process
	 * @return the exercise found
	 */
	@NotNull
	public Optional<Solution> identify(@NotNull final CharSequence code) {
		final Optional<Course> course = identify(code, this.courseRegex, courseSrv);
		final Optional<Exercise> exercise = identify(code, this.exerciseRegex, exerciseSrv);
		final Optional<Series> series = identify(code, this.seriesRegex, seriesSrv);
		
		// Exercise is required, course and series are is optional.
		return exercise.flatMap(
			ex -> of(new Solution(course.orElse(null), series.orElse(null), ex))
		);
	}
}
