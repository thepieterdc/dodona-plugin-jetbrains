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
import com.intellij.ui.JBColor;
import com.intellij.ui.table.JBTable;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.List;

/**
 * Renders a list of submissions.
 */
class SubmissionsTable extends JBTable {
	private final SubmissionsTableModel model;
	
	/**
	 * SubmissionsTableModel constructor.
	 *
	 * @param submissions list of submissions to display
	 */
	SubmissionsTable(@Nonnull final List<PartialSubmission> submissions) {
		this.model = new SubmissionsTableModel(submissions);
		this.setModel(this.model);
	}

	@Override
	@NotNull
	public Component prepareRenderer(@NotNull TableCellRenderer renderer, int row, int column) {
		final Component result = super.prepareRenderer(renderer, row, column);
		
		final PartialSubmission submission = this.model.getRowValue(row);
		
		// Background color.
		result.setBackground(submission.isAccepted() ? JBColor.GREEN: JBColor.RED);

		return result;
	}
}
