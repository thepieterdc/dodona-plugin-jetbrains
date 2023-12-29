/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.feedback.impl;

import com.intellij.openapi.project.Project;
import io.github.thepieterdc.dodona.data.SubmissionStatus;
import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import io.github.thepieterdc.dodona.plugin.feedback.FeedbackService;
import io.github.thepieterdc.dodona.plugin.notifications.NotificationService;
import io.github.thepieterdc.dodona.plugin.ui.resources.submission.SubmissionStatusIcon;
import io.github.thepieterdc.dodona.resources.activities.Exercise;
import io.github.thepieterdc.dodona.resources.submissions.SubmissionInfo;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;

import static io.github.thepieterdc.dodona.plugin.notifications.SendableNotification.*;

public class FeedbackServiceImpl implements FeedbackService {
	private final NotificationService notifications;
	private final Map<SubmissionStatus, BiConsumer<Exercise, SubmissionInfo>>
		providers = new EnumMap<>(SubmissionStatus.class);

	/**
	 * FeedbackService constructor.
	 */
	public FeedbackServiceImpl(final Project project) {
		this.notifications = NotificationService.getInstance(project);

		this.providers.put(SubmissionStatus.COMPILATION_ERROR,
			this::compilationError);
		this.providers.put(SubmissionStatus.CORRECT, this::correct);
		this.providers.put(SubmissionStatus.INTERNAL_ERROR,
			(ex, sub) -> this.internalError(ex));
		this.providers.put(SubmissionStatus.MEMORY_LIMIT_EXCEEDED,
			this::memoryLimitExceeded);
		this.providers.put(SubmissionStatus.OUTPUT_LIMIT_EXCEEDED,
			this::outputLimitExceeded);
		this.providers.put(SubmissionStatus.RUNTIME_ERROR, this::runtimeError);
		this.providers.put(SubmissionStatus.TIME_LIMIT_EXCEEDED,
			this::timeLimitExceeded);
		this.providers.put(SubmissionStatus.WRONG, this::wrong);
	}

	/**
	 * Compilation error handler.
	 *
	 * @param exercise   the exercise
	 * @param submission the submission
	 */
	private void compilationError(final Exercise exercise,
								  final SubmissionInfo submission) {
		this.notifications.send(
			error(DodonaBundle.message("feedback.compile_error.title"),
				DodonaBundle.message("feedback.compile_error.message",
					exercise.getName())).withIcon(
					SubmissionStatusIcon.COMPILATION_ERROR)
				.withLink(
					DodonaBundle.message("feedback.compile_error.link_label"),
					submission.getUrl()));
	}

	/**
	 * Correct solution handler.
	 *
	 * @param exercise the exercise
	 */
	private void correct(final Exercise exercise,
						 final SubmissionInfo submission) {
		this.notifications.send(
			info(DodonaBundle.message("feedback.correct.title"),
				DodonaBundle.message("feedback.correct.message",
					exercise.getName())).withIcon(SubmissionStatusIcon.CORRECT)
				.withLink(DodonaBundle.message("feedback.correct.link_label"),
					submission.getUrl()));
	}

	/**
	 * Internal error handler.
	 *
	 * @param exercise the exercise
	 */
	private void internalError(final Exercise exercise) {
		this.notifications.send(
			warning(DodonaBundle.message("feedback.internal_error.title"),
				DodonaBundle.message("feedback.internal_error.message",
					exercise)));
	}

	/**
	 * Memory limit handler.
	 *
	 * @param exercise   the exercise
	 * @param submission the submission
	 */
	private void memoryLimitExceeded(final Exercise exercise,
									 final SubmissionInfo submission) {
		this.notifications.send(
			error(DodonaBundle.message("feedback.memory_limit.title"),
				DodonaBundle.message("feedback.memory_limit.message",
					exercise.getName())).withIcon(
					SubmissionStatusIcon.MEMORY_LIMIT_EXCEEDED)
				.withLink(
					DodonaBundle.message("feedback.memory_limit.link_label"),
					submission.getUrl()));
	}

	/**
	 * Output limit handler.
	 *
	 * @param exercise   the exercise
	 * @param submission the submission
	 */
	private void outputLimitExceeded(final Exercise exercise,
									 final SubmissionInfo submission) {
		this.notifications.send(
			error(DodonaBundle.message("feedback.output_limit.title"),
				DodonaBundle.message("feedback.output_limit.message",
					exercise.getName())).withIcon(
					SubmissionStatusIcon.OUTPUT_LIMIT_EXCEEDED)
				.withLink(
					DodonaBundle.message("feedback.output_limit.link_label"),
					submission.getUrl()));
	}

	@Override
	public void notify(final Exercise exercise,
					   final SubmissionInfo submission) {
		Optional.ofNullable(this.providers.get(submission.getStatus()))
			.ifPresent(p -> p.accept(exercise, submission));
	}

	/**
	 * Runtime error handler.
	 *
	 * @param exercise   the exercise
	 * @param submission the submission
	 */
	private void runtimeError(final Exercise exercise,
							  final SubmissionInfo submission) {
		this.notifications.send(
			error(DodonaBundle.message("feedback.runtime_error.title"),
				DodonaBundle.message("feedback.runtime_error.message",
					exercise.getName())).withIcon(
					SubmissionStatusIcon.RUNTIME_ERROR)
				.withLink(
					DodonaBundle.message("feedback.runtime_error.link_label"),
					submission.getUrl()));
	}

	/**
	 * Time limit handler.
	 *
	 * @param exercise   the exercise
	 * @param submission the submission
	 */
	private void timeLimitExceeded(final Exercise exercise,
								   final SubmissionInfo submission) {
		this.notifications.send(
			error(DodonaBundle.message("feedback.time_limit.title"),
				DodonaBundle.message("feedback.time_limit.message",
					exercise.getName())).withIcon(
					SubmissionStatusIcon.TIME_LIMIT_EXCEEDED)
				.withLink(
					DodonaBundle.message("feedback.time_limit.link_label"),
					submission.getUrl()));
	}

	/**
	 * Wrong solution handler.
	 *
	 * @param exercise   the exercise
	 * @param submission the submission
	 */
	private void wrong(final Exercise exercise,
					   final SubmissionInfo submission) {
		this.notifications.send(
			warning(DodonaBundle.message("feedback.wrong.title"),
				DodonaBundle.message("feedback.wrong.message",
					exercise.getName(), submission.getSummary())).withIcon(
					SubmissionStatusIcon.COMPILATION_ERROR)
				.withLink(DodonaBundle.message("feedback.wrong.link_label"),
					submission.getUrl()));
	}
}
