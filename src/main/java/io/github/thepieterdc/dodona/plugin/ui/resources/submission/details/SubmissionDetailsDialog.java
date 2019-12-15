/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.ui.resources.submission.details;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.util.Disposer;
import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import io.github.thepieterdc.dodona.plugin.api.executor.DodonaExecutorHolder;
import io.github.thepieterdc.dodona.resources.submissions.SubmissionInfo;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import java.awt.*;

/**
 * Dialog that shows the details of a submission.
 */
public final class SubmissionDetailsDialog extends DialogWrapper implements Disposable {
	private static final Action[] NO_ACTIONS = {};
	
	private final SubmissionDetailsPanel detailsPanel;
	
	/**
	 * SubmissionCodeDialog constructor.
	 *
	 * @param project        the current project
	 * @param executor       holder for the request executor
	 * @param submissionInfo the submission details
	 * @param parent         parent component
	 */
	public SubmissionDetailsDialog(final Project project,
	                               final DodonaExecutorHolder executor,
	                               final SubmissionInfo submissionInfo,
	                               @Nullable final Component parent) {
		super(project, parent, false, IdeModalityType.PROJECT);
		this.detailsPanel = new SubmissionDetailsPanel(
			project, executor, submissionInfo
		);
		
		// Set the title of the dialog.
		this.setTitle(DodonaBundle.message("dialog.submission_details.title"));
		
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
