/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.ui.resources.submission.details;

import com.intellij.icons.AllIcons;
import com.intellij.ide.BrowserUtil;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Disposer;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.labels.LinkLabel;
import com.intellij.util.ui.JBUI;
import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import io.github.thepieterdc.dodona.plugin.api.executor.DodonaExecutorHolder;
import io.github.thepieterdc.dodona.plugin.ui.resources.submission.HumanSubmissionStatus;
import io.github.thepieterdc.dodona.plugin.ui.resources.submission.SubmissionCodeEditor;
import io.github.thepieterdc.dodona.plugin.ui.resources.submission.SubmissionStatusIcon;
import io.github.thepieterdc.dodona.plugin.ui.util.TimeUtils;
import io.github.thepieterdc.dodona.resources.submissions.SubmissionInfo;

import javax.swing.*;
import java.awt.*;

/**
 * Panel that displays the code of a submission.
 */
final class SubmissionDetailsPanel extends JPanel implements Disposable {
	
	private static final int PANEL_HEIGHT = 500;
	private static final int PANEL_WIDTH = 650;
	
	private final SubmissionInfo submissionInfo;
	
	private final SubmissionCodeEditor editorPanel;
	
	/**
	 * SubmissionDetailsPanel constructor.
	 *
	 * @param project        the current project
	 * @param executor       holder for the request executor
	 * @param submissionInfo the submission details
	 */
	SubmissionDetailsPanel(final Project project,
	                       final DodonaExecutorHolder executor,
	                       final SubmissionInfo submissionInfo) {
		super(new BorderLayout(0, 5));
		this.editorPanel = new SubmissionCodeEditor(project, executor, submissionInfo);
		this.submissionInfo = submissionInfo;
		this.setBorder(BorderFactory.createEmptyBorder());
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		this.initialize();
		this.editorPanel.requestUpdate();
	}
	
	@Override
	public void dispose() {
		Disposer.dispose(this.editorPanel);
	}

	/**
	 * Initializes the components.
	 */
	private void initialize() {
		// Create the status label.
		final JLabel status = new JLabel(DodonaBundle.message(
			"dialog.submission_details.status",
			HumanSubmissionStatus.forStatus(this.submissionInfo.getStatus()),
			TimeUtils.fuzzy(this.submissionInfo.getCreatedAt())));
		status.setIcon(SubmissionStatusIcon.forStatus(this.submissionInfo.getStatus()));

		// Create the submission link.
		final JPanel dodonaLink = JBUI.Panels.simplePanel()
			.addToCenter(LinkLabel.create(
				DodonaBundle.message("dialog.submission_details.link"),
				() -> BrowserUtil.browse(this.submissionInfo.getUrl())))
			.addToRight(new JBLabel(AllIcons.Ide.External_link_arrow));

		// Create the top bar.
		final JPanel topPanel = new JPanel(new BorderLayout(5, 0));
		topPanel.add(status, BorderLayout.CENTER);
		topPanel.add(dodonaLink, BorderLayout.LINE_END);

		this.add(topPanel, BorderLayout.PAGE_START);
		this.add(this.editorPanel, BorderLayout.CENTER);
	}
}
