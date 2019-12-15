/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.toolwindow.ui.submissions;

import com.intellij.ui.table.JBTable;
import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import io.github.thepieterdc.dodona.plugin.ui.listeners.DoubleClickListener;
import io.github.thepieterdc.dodona.plugin.ui.panels.async.AsyncPanelContent;
import io.github.thepieterdc.dodona.resources.submissions.SubmissionInfo;

import java.util.List;
import java.util.function.Consumer;

/**
 * Renders a table of submissions.
 */
public final class SubmissionsTable extends JBTable implements AsyncPanelContent<List<SubmissionInfo>> {
	private final SubmissionsTableModel model;
	
	/**
	 * SubmissionsTable constructor.
	 */
	public SubmissionsTable() {
		this(new SubmissionsTableModel());
	}
	
	/**
	 * SubmissionsTable constructor.
	 *
	 * @param model the table model
	 */
	private SubmissionsTable(final SubmissionsTableModel model) {
		super(model);
		this.model = model;
		
		this.getEmptyText().setText(
			DodonaBundle.message("toolwindow.submissions.none")
		);
		
		this.model.setColumnRenderers(this.getColumnModel());
	}
	
	/**
	 * Configures the listener.
	 *
	 * @param handler handler to call
	 */
	public void addListener(final Consumer<? super SubmissionInfo> handler) {
		this.addMouseListener((DoubleClickListener) e -> {
			final int row = this.rowAtPoint(e.getPoint());
			this.model.getItemAtRow(row).ifPresent(handler);
		});
	}
	
	/**
	 * Adds a newly created submission.
	 *
	 * @param submission the created submission
	 */
	public void submissionCreated(final SubmissionInfo submission) {
		this.model.addItem(submission);
	}
	
	/**
	 * Updates an evaluated submission.
	 *
	 * @param submission the evaluated submission
	 */
	public void submissionEvaluated(final SubmissionInfo submission) {
		this.model.update(submission);
	}
	
	@Override
	public void update(final List<SubmissionInfo> submissions) {
		this.model.replaceItems(submissions);
	}
}
