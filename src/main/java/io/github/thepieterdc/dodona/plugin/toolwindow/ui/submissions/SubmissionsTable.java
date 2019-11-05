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
import io.github.thepieterdc.dodona.resources.submissions.PartialSubmission;

import java.util.Collection;
import java.util.function.Consumer;

/**
 * Renders a table of submissions.
 */
public final class SubmissionsTable extends JBTable {
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
	public void addListener(final Consumer<? super PartialSubmission> handler) {
		this.addMouseListener((DoubleClickListener) e -> {
			final int row = this.rowAtPoint(e.getPoint());
			this.model.getItemAtRow(row).ifPresent(handler);
		});
	}
	
	/**
	 * Sets the submissions in the table.
	 *
	 * @param submissions the submissions to set
	 */
	void setSubmissions(final Collection<? extends PartialSubmission> submissions) {
		this.model.replaceItems(submissions);
	}
}
