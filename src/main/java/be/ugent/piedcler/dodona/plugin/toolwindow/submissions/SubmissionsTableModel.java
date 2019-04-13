/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.toolwindow.submissions;

import be.ugent.piedcler.dodona.resources.submissions.PartialSubmission;
import com.intellij.util.ui.SortableColumnModel;

import javax.annotation.Nonnull;
import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * Renders a list of submissions.
 */
public class SubmissionsTableModel extends AbstractTableModel implements SortableColumnModel {
	private final List<PartialSubmission> submissions;
	
	/**
	 * SubmissionsTableModel constructor.
	 *
	 * @param submissions list of submissions to display
	 */
	SubmissionsTableModel(@Nonnull final List<PartialSubmission> submissions) {
		this.submissions = submissions;
	}
	
	@Override
	public int getRowCount() {
		return this.submissions.size();
	}
}
