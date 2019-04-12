/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.notifications;

import be.ugent.piedcler.dodona.data.SubmissionStatus;
import be.ugent.piedcler.dodona.resources.Exercise;
import be.ugent.piedcler.dodona.resources.submissions.Submission;
import com.intellij.openapi.project.Project;

import javax.annotation.Nonnull;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;

/**
 * Service that provides the user with feedback about an solution.
 */
public class FeedbackServiceImpl implements FeedbackService {
	private final Project project;
	
	private final Map<SubmissionStatus, BiConsumer<Exercise, Submission>> providers = new EnumMap<>(SubmissionStatus.class);
	
	/**
	 * FeedbackService constructor.
	 *
	 * @param project the current project
	 */
	public FeedbackServiceImpl(@Nonnull final Project project) {
		this.project = project;
		
		this.providers.put(SubmissionStatus.COMPILATION_ERROR, this::compilationError);
		this.providers.put(SubmissionStatus.CORRECT, this::correct);
		this.providers.put(SubmissionStatus.INTERNAL_ERROR, this::internalError);
		this.providers.put(SubmissionStatus.MEMORY_LIMIT_EXCEEDED, this::memoryLimitExceeded);
		this.providers.put(SubmissionStatus.RUNTIME_ERROR, this::runtimeError);
		this.providers.put(SubmissionStatus.TIME_LIMIT_EXCEEDED, this::timeLimitExceeded);
		this.providers.put(SubmissionStatus.WRONG, this::wrong);
	}
	
	/**
	 * Compilation error handler.
	 *
	 * @param exercise   the exercise
	 * @param submission the submission
	 */
	private void compilationError(@Nonnull final Exercise exercise, @Nonnull final Submission submission) {
		Notifier.warning(this.project, "Compilation error",
			String.format("Your solution to \"%s\" could not be compiled and contains syntax errors. <a href=\"%s\">More details</a>.",
				exercise.getName(), submission.getUrl()
			)
		);
	}
	
	/**
	 * Correct solution handler.
	 *
	 * @param exercise   the exercise
	 * @param submission the submission
	 */
	private void correct(@Nonnull final Exercise exercise, @Nonnull final Submission submission) {
		Notifier.info(this.project, "Correct solution",
			String.format("Your solution to \"%s\" has been accepted!", exercise.getName())
		);
	}
	
	/**
	 * Internal error handler.
	 *
	 * @param exercise   the exercise
	 * @param submission the submission
	 */
	private void internalError(@Nonnull final Exercise exercise, @Nonnull final Submission submission) {
		Notifier.warning(this.project, "Internal error",
			"Something went wrong while evaluating your solution. The Dodona team has been notified of this error."
		);
	}
	
	/**
	 * Memory limit handler.
	 *
	 * @param exercise   the exercise
	 * @param submission the submission
	 */
	private void memoryLimitExceeded(@Nonnull final Exercise exercise, @Nonnull final Submission submission) {
		Notifier.warning(this.project, "Memory limit exceeded",
			String.format("Your solution to \"%s\" exceeded the allowed memory usage. Try to optimise your allocations and data structures. <a href=\"%s\">More details</a>.",
				exercise.getName(), submission.getUrl()
			)
		);
	}
	
	@Override
	public void notify(@Nonnull final Exercise exercise, @Nonnull final Submission submission) {
		Optional.ofNullable(this.providers.get(submission.getStatus())).ifPresent(p ->
			p.accept(exercise, submission)
		);
	}
	
	/**
	 * Runtime error handler.
	 *
	 * @param exercise   the exercise
	 * @param submission the submission
	 */
	private void runtimeError(@Nonnull final Exercise exercise, @Nonnull final Submission submission) {
		Notifier.warning(this.project, "Runtime error",
			String.format("A runtime error has occurred while evaluating your solution to \"%s\". <a href=\"%s\">More details</a>.",
				exercise.getName(), submission.getUrl()
			)
		);
	}
	
	/**
	 * Time limit handler.
	 *
	 * @param exercise   the exercise
	 * @param submission the submission
	 */
	private void timeLimitExceeded(@Nonnull final Exercise exercise, @Nonnull final Submission submission) {
		Notifier.warning(this.project, "Time limit exceeded",
			String.format("Your solution to \"%s\" was too slow and has exceeded the allowed time limit. Try to optimise your code and reduce its computational complexity. <a href=\"%s\">More details</a>.",
				exercise.getName(), submission.getUrl()
			)
		);
	}
	
	/**
	 * Wrong solution handler.
	 *
	 * @param exercise   the exercise
	 * @param submission the submission
	 */
	private void wrong(@Nonnull final Exercise exercise, @Nonnull final Submission submission) {
		Notifier.warning(this.project, "Incorrect solution",
			String.format("Your solution to \"%s\" was not correct: %s. <a href=\"%s\">More details</a>.",
				exercise.getName(), submission.getSummary(), submission.getUrl())
		);
	}
}