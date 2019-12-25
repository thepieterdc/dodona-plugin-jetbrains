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
import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import io.github.thepieterdc.dodona.plugin.api.executor.DodonaExecutorHolder;
import io.github.thepieterdc.dodona.plugin.exercise.Identification;
import io.github.thepieterdc.dodona.plugin.submission.SubmissionCreatedListener;
import io.github.thepieterdc.dodona.plugin.submission.SubmissionEvaluatedListener;
import io.github.thepieterdc.dodona.plugin.ui.panels.async.StaticAsyncPanel;
import io.github.thepieterdc.dodona.plugin.ui.resources.submission.details.SubmissionDetailsDialog;
import io.github.thepieterdc.dodona.resources.submissions.SubmissionInfo;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * UI for the Submissions tab.
 */
public final class SubmissionsPanel extends StaticAsyncPanel<List<SubmissionInfo>, SubmissionsTable> {
	private final DodonaExecutorHolder executor;
	private final Identification identification;
	private final Project project;
	
	private final SubmissionsTable table;
	
	/**
	 * SubmissionsPanel constructor.
	 *
	 * @param project        current active project
	 * @param executor       request executor
	 * @param identification the exercise identification
	 */
	public SubmissionsPanel(final Project project,
	                        final DodonaExecutorHolder executor,
	                        final Identification identification) {
		super(project, DodonaBundle.message("toolwindow.submissions.loading"));
		this.setBorder(BorderFactory.createEmptyBorder());
		this.executor = executor;
		this.identification = identification;
		this.project = project;
		this.table = new SubmissionsTable();
		
		// Watch for new submissions.
		final MessageBusConnection bus = this.project.getMessageBus().connect();
		bus.subscribe(
			SubmissionCreatedListener.SUBMISSION_CREATED,
			submission -> this.table.submissionCreated(submission.getInfo())
		);
		bus.subscribe(
			SubmissionEvaluatedListener.SUBMISSION_EVALUATED,
			submission -> this.table.submissionEvaluated(submission.getInfo())
		);
		
		this.table.addListener(this::showSubmissionDialog);
	}
	
	@Nonnull
	@Override
	protected SubmissionsTable createContentPane() {
		return this.table;
	}
	
	@Nonnull
	@Override
	protected CompletableFuture<List<SubmissionInfo>> getData() {
		// Get the information.
		final Optional<Long> optCourse = this.identification.getCourseId();
		final long exercise = this.identification.getExerciseId();
		
		return this.executor.getExecutor().execute(dodona -> optCourse
			.map(course -> dodona.submissions().getAllByMe(course, exercise))
			.orElseGet(() -> dodona.submissions().getAllByMe(exercise))
		);
	}
	
	/**
	 * Shows the submission dialog.
	 *
	 * @param submission submission to show
	 */
	private void showSubmissionDialog(final SubmissionInfo submission) {
		new SubmissionDetailsDialog(
			this.project, this.executor, submission, this.table)
			.show();
	}
}