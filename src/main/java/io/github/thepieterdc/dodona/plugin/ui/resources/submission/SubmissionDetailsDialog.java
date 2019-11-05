/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.ui.resources.submission;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.util.Disposer;
import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import io.github.thepieterdc.dodona.resources.Exercise;
import io.github.thepieterdc.dodona.resources.submissions.PartialSubmission;
import io.github.thepieterdc.dodona.resources.submissions.Submission;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import java.awt.*;
import java.util.concurrent.CompletableFuture;

/**
 * Dialog that shows the details of a submission.
 */
public final class SubmissionDetailsDialog extends DialogWrapper implements Disposable {
	private static final Action[] NO_ACTIONS = {};
	
	private final SubmissionDetailsPanel detailsPanel;
	
	/**
	 * SubmissionCodeDialog constructor.
	 *
	 * @param project           the current project
	 * @param parent            parent component
	 * @param submissionDetails the submission details
	 * @param futureExercise    exercise request
	 * @param futureSubmission  submission request
	 */
	public SubmissionDetailsDialog(final Project project,
	                               @Nullable final Component parent,
	                               final PartialSubmission submissionDetails,
	                               final CompletableFuture<Exercise> futureExercise,
	                               final CompletableFuture<Submission> futureSubmission) {
		super(project, parent, false, IdeModalityType.PROJECT);
		
		this.detailsPanel = new SubmissionDetailsPanel(
			project, submissionDetails, futureExercise, futureSubmission
		);
		
		// Set the default title of the dialog.
		this.setTitle(DodonaBundle.message("dialog.submission_details.title.default"));
		
		// When the exercise is loaded, change the title.
		futureExercise.whenComplete((exercise, error) -> this.setTitle(
			DodonaBundle.message("dialog.submission_details.title.exercise",
				exercise.getName())));
		
		this.init();
	}
	
	@NotNull
	@Override
	protected Action[] createActions() {
		return NO_ACTIONS;
	}
	
	@Nonnull
	@Override
	protected JComponent createCenterPanel() {
		return this.detailsPanel;
	}
	
	@Override
	public void dispose() {
		Disposer.dispose(this.detailsPanel);
		super.dispose();
	}
	
	@Override
	public boolean isTypeAheadEnabled() {
		return true;
	}
}
