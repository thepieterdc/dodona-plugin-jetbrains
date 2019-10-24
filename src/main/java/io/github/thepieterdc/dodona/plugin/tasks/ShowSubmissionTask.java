/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.tasks;

import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.project.Project;
import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import io.github.thepieterdc.dodona.plugin.api.DodonaExecutor;
import io.github.thepieterdc.dodona.plugin.authentication.DodonaAuthenticator;
import io.github.thepieterdc.dodona.plugin.ui.browser.BrowserDialog;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.swing.*;

/**
 * Shows a submission result.
 */
public class ShowSubmissionTask extends AbstractDodonaBackgroundTask {
	private final DodonaExecutor executor;
	private final long submissionId;
	
	/**
	 * ShowSubmissionTask constructor.
	 *
	 * @param project      the current project
	 * @param submissionId the submission id
	 */
	private ShowSubmissionTask(final Project project,
	                           final long submissionId) {
		super(project, DodonaBundle.message("tasks.show_submission.title"));
		this.executor = DodonaAuthenticator.getInstance().getExecutor();
		this.submissionId = submissionId;
	}
	
	/**
	 * Creates a submission rendering task.
	 *
	 * @param project      the current project
	 * @param submissionId the id of the submission to display
	 * @return the task
	 */
	@Nonnull
	public static DodonaBackgroundTask create(final Project project,
	                                          final long submissionId) {
		return new ShowSubmissionTask(project, submissionId);
	}
	
	@Override
	public void run(@NotNull final ProgressIndicator progress) {
		// Update the progressbar.
		progress.setIndeterminate(true);
		progress.setText(DodonaBundle.message("tasks.show_submission.loading"));
		
		// Fetch the url of the submission.
		final String url = this.executor.execute(dodona ->
			dodona.submissions().get(this.submissionId), progress)
			.getUrl();
		
		// Show a BrowserDialog with the submission
		SwingUtilities.invokeLater(() -> {
			// Create the window.
			final BrowserDialog browser = new BrowserDialog(
				this.myProject,
				DodonaBundle.message("tasks.show_submission.title")
			);
			
			// Append the authentication data.
			DodonaAuthenticator.getInstance().authenticate(browser);
			
			// Load the url and show the browser.
			browser.loadUrl(url);
			browser.show();
		});
	}
}
