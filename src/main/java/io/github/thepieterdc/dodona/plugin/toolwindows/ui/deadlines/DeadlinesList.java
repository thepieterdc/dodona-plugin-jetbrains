/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.toolwindows.ui.deadlines;

import com.intellij.ui.CollectionListModel;
import com.intellij.ui.components.JBList;
import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import io.github.thepieterdc.dodona.plugin.toolwindows.tabs.DeadlinesTab;

import javax.swing.*;
import java.util.List;

/**
 * Renders a list of deadlines.
 */
final class DeadlinesList extends JBList<DeadlinesTab.Deadline> {
	private final CollectionListModel<DeadlinesTab.Deadline> model;
	
	/**
	 * DeadlinesList constructor.
	 *
	 * @param courseId the id of the current active course
	 */
	DeadlinesList(final long courseId) {
		super();
		this.model = new CollectionListModel<>();
		
		this.setBorder(BorderFactory.createEmptyBorder());
		this.setCellRenderer(new DeadlineListCellRenderer(courseId));
		this.setEmptyText(DodonaBundle.message("toolwindow.deadlines.none"));
		this.setModel(this.model);
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
	
	void setDeadlines(final List<? extends DeadlinesTab.Deadline> deadlines) {
		this.model.replaceAll(deadlines);
	}
}
