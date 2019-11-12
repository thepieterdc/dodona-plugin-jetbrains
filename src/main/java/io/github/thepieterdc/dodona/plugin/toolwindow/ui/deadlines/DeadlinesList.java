/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.toolwindow.ui.deadlines;

import com.intellij.ui.CollectionListModel;
import com.intellij.ui.components.JBList;
import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import io.github.thepieterdc.dodona.plugin.ui.Deadline;

import javax.annotation.Nullable;
import javax.swing.*;
import java.util.List;

/**
 * Renders a list of deadlines.
 */
final class DeadlinesList extends JBList<Deadline> {
	private final CollectionListModel<Deadline> model;
	private final DeadlineListCellRenderer renderer;
	
	/**
	 * DeadlinesList constructor.
	 */
	DeadlinesList() {
		super();
		this.model = new CollectionListModel<>();
		this.renderer = new DeadlineListCellRenderer();
		
		this.setBorder(BorderFactory.createEmptyBorder());
		this.setCellRenderer(this.renderer);
		this.setEmptyText(DodonaBundle.message("toolwindow.deadlines.none"));
		this.setModel(this.model);
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
	
	/**
	 * Sets the current course.
	 *
	 * @param course the current course
	 */
	public void setCurrentCourse(@Nullable final Long course) {
		this.renderer.setCurrentCourse(course);
	}
	
	/**
	 * Set the deadlines in the list.
	 *
	 * @param deadlines the deadlines
	 */
	void setDeadlines(final List<? extends Deadline> deadlines) {
		this.model.replaceAll(deadlines);
	}
}
