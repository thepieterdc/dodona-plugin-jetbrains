/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.toolwindow.ui.submissions;

import io.github.thepieterdc.dodona.data.SubmissionStatus;
import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import io.github.thepieterdc.dodona.plugin.ui.renderers.table.FuzzyTimeCellRenderer;
import io.github.thepieterdc.dodona.plugin.ui.resources.AbstractResourceTableModel;
import io.github.thepieterdc.dodona.plugin.ui.resources.submission.SubmissionStatusCellRenderer;
import io.github.thepieterdc.dodona.resources.submissions.SubmissionInfo;

import javax.swing.table.TableColumnModel;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.function.Function;

/**
 * Model for the submissions of an exercise.
 */
class SubmissionsTableModel extends AbstractResourceTableModel<SubmissionInfo> {
	private static final SubmissionAccessor[] COLUMN_ACCESSORS = {
		SubmissionInfo::getStatus,
		SubmissionInfo::getCreatedAt,
		SubmissionInfo::getSummary
	};
	
	private static final String[] COLUMN_NAMES = {
		DodonaBundle.message("toolwindow.submissions.table.status"),
		DodonaBundle.message("toolwindow.submissions.table.timestamp"),
		DodonaBundle.message("toolwindow.submissions.table.summary")
	};
	
	private static final Class<?>[] COLUMN_TYPES = {
		SubmissionStatus.class,
		ZonedDateTime.class,
		String.class
	};
	
	/**
	 * An accessor for a column.
	 */
	@FunctionalInterface
	interface SubmissionAccessor extends Function<SubmissionInfo, Object> {
	}
	
	/**
	 * SubmissionsTableModel constructor.
	 */
	SubmissionsTableModel() {
		super(COLUMN_NAMES, COLUMN_TYPES, COLUMN_ACCESSORS, Comparator.reverseOrder());
	}
	
	/**
	 * Configures the column renderers.
	 *
	 * @param columnModel the table column model
	 */
	void setColumnRenderers(final TableColumnModel columnModel) {
		columnModel.getColumn(0).setCellRenderer(
			new SubmissionStatusCellRenderer()
		);
		columnModel.getColumn(1).setCellRenderer(
			new FuzzyTimeCellRenderer()
		);
	}
}
