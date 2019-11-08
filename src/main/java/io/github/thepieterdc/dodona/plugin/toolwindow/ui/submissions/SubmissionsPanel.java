/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.toolwindow.ui.submissions;

import com.intellij.openapi.project.Project;
import com.intellij.util.messages.MessageBusConnection;
import io.github.thepieterdc.dodona.plugin.api.DodonaExecutor;
import io.github.thepieterdc.dodona.plugin.exercise.Identification;
import io.github.thepieterdc.dodona.plugin.submission.SubmissionCreatedListener;
import io.github.thepieterdc.dodona.plugin.submission.SubmissionEvaluatedListener;
import io.github.thepieterdc.dodona.plugin.ui.AsyncContentPanel;
import io.github.thepieterdc.dodona.plugin.ui.resources.submission.SubmissionDetailsDialog;
import io.github.thepieterdc.dodona.resources.Exercise;
import io.github.thepieterdc.dodona.resources.submissions.Submission;
import io.github.thepieterdc.dodona.resources.submissions.SubmissionInfo;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * UI for the Submissions tab.
 */
public final class SubmissionsPanel extends AsyncContentPanel<SubmissionsTable> {
	private final DodonaExecutor executor;
	private final Project project;
	
	/**
	 * SubmissionsPanel constructor.
	 *
	 * @param project           current active project
	 * @param executor          request executor
	 * @param table             the submissions table
	 * @param futureSubmissions submissions request
	 */
	private SubmissionsPanel(final Project project,
	                         final DodonaExecutor executor,
	                         final SubmissionsTable table,
	                         final CompletionStage<? extends List<SubmissionInfo>> futureSubmissions) {
		super(table, "toolwindow.submissions.loading", true);
		this.setBorder(BorderFactory.createEmptyBorder());
		
		this.executor = executor;
		this.project = project;
		
		// Watch for new submissions.
		final MessageBusConnection bus = this.project.getMessageBus().connect();
		bus.subscribe(
			SubmissionCreatedListener.SUBMISSION_CREATED,
			submission -> this.content.submissionCreated(submission.getInfo())
		);
		bus.subscribe(
			SubmissionEvaluatedListener.SUBMISSION_EVALUATED,
			submission -> this.content.submissionEvaluated(submission.getInfo())
		);
		
		this.content.addListener(this::showSubmissionDialog);
		
		futureSubmissions.whenComplete((submissions, error) -> {
			this.content.setSubmissions(submissions);
			this.showContentCard();
		});
	}
	
	/**
	 * Creates a SubmissionsPanel to render submissions of an exercise.
	 *
	 * @param project        current active project
	 * @param executor       request executor
	 * @param identification exercise identification
	 * @return the panel
	 */
	@Nonnull
	public static SubmissionsPanel create(final Project project,
	                                      final DodonaExecutor executor,
	                                      final Identification identification) {
		final long exerciseId = identification.getExercise();
		final CompletableFuture<List<SubmissionInfo>> submissions = executor
			.execute(dodona -> identification.getCourse()
				.map(course -> dodona.submissions().getAllByMe(course, exerciseId))
				.orElseGet(() -> dodona.submissions().getAllByMe(exerciseId))
			);
		
		final SubmissionsTable table = new SubmissionsTable();
		
		return new SubmissionsPanel(project, executor, table, submissions);
	}
	
	/**
	 * Shows the submission dialog.
	 *
	 * @param submission submission to show
	 */
	private void showSubmissionDialog(final SubmissionInfo submission) {
		final CompletableFuture<Exercise> futureExercise = this.executor
			.execute(dodona -> dodona.exercises().get(submission));
		final CompletableFuture<Submission> futureSubmission = this.executor
			.execute(dodona -> dodona.submissions().get(submission));
		
		new SubmissionDetailsDialog(
			this.project, this.content, submission, futureExercise, futureSubmission)
			.show();
	}
}