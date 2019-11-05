/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.ui.resources.submission;

import com.intellij.icons.AllIcons;
import com.intellij.ide.BrowserUtil;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.project.Project;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.labels.LinkLabel;
import com.intellij.ui.components.panels.Wrapper;
import com.intellij.util.ui.JBUI;
import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import io.github.thepieterdc.dodona.plugin.ui.util.TimeUtils;
import io.github.thepieterdc.dodona.resources.Exercise;
import io.github.thepieterdc.dodona.resources.submissions.PartialSubmission;
import io.github.thepieterdc.dodona.resources.submissions.Submission;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.CompletionStage;

/**
 * Panel that displays the code of a submission.
 */
final class SubmissionDetailsPanel extends Wrapper implements Disposable {
	private static final int PANEL_HEIGHT = 500;
	private static final int PANEL_WIDTH = 650;
	
	private final SubmissionCodeEditor editorPanel;
	
	/**
	 * SubmissionDetailsPanel constructor.
	 *
	 * @param project           the current project
	 * @param submissionDetails the submission details
	 * @param futureExercise    exercise request
	 * @param futureSubmission  submission request
	 */
	SubmissionDetailsPanel(final Project project,
	                       final PartialSubmission submissionDetails,
	                       final CompletionStage<? extends Exercise> futureExercise,
	                       final CompletionStage<? extends Submission> futureSubmission) {
		this.setLayout(new BorderLayout(0, 5));
		this.setBorder(BorderFactory.createEmptyBorder());
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		
		this.editorPanel = new SubmissionCodeEditor(project);
		
		futureExercise.whenComplete((exercise, exerciseError) ->
			futureSubmission.whenComplete(((submission, submissionError) ->
				this.editorPanel.submissionLoaded(exercise, submission)
			))
		);
		
		this.initialize(submissionDetails);
	}
	
	@Override
	public void dispose() {
		this.editorPanel.dispose();
	}
	
	/**
	 * Initializes the components.
	 *
	 * @param submissionDetails the submission details
	 */
	private void initialize(final PartialSubmission submissionDetails) {
		// Create the status label.
		final JLabel status = new JLabel(DodonaBundle.message(
			"dialog.submission_details.status",
			HumanSubmissionStatus.forStatus(submissionDetails.getStatus()),
			TimeUtils.fuzzy(submissionDetails.getCreatedAt())));
		status.setIcon(SubmissionStatusIcon.forStatus(submissionDetails.getStatus()));
		
		// Create the submission link.
		final JPanel dodonaLink = JBUI.Panels.simplePanel()
			.addToCenter(LinkLabel.create(
				DodonaBundle.message("dialog.submission_details.link"),
				() -> BrowserUtil.browse(submissionDetails.getUrl())))
			.addToRight(new JBLabel(AllIcons.Ide.External_link_arrow));
		
		// Create the top bar.
		final JPanel topPanel = new JPanel(new BorderLayout(5, 0));
		topPanel.add(status, BorderLayout.CENTER);
		topPanel.add(dodonaLink, BorderLayout.LINE_END);
		
		this.add(topPanel, BorderLayout.PAGE_START);
		this.add(this.editorPanel, BorderLayout.CENTER);
	}
}
