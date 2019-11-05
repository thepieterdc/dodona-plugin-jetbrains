/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.ui.resources.submission;

import com.intellij.util.ui.table.IconTableCellRenderer;
import io.github.thepieterdc.dodona.data.SubmissionStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Renders a submission status and its icon.
 */
public class SubmissionStatusCellRenderer extends IconTableCellRenderer<SubmissionStatus> {
	@Nullable
	@Override
	protected Icon getIcon(@NotNull final SubmissionStatus submissionStatus, final JTable jTable, final int i) {
		return SubmissionStatusIcon.forStatus(submissionStatus);
	}
	
	@Override
	protected void setValue(final Object value) {
		if (value instanceof SubmissionStatus) {
			super.setValue(
				HumanSubmissionStatus.forStatus((SubmissionStatus) value)
			);
		}
	}
}
