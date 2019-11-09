/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.toolwindow.ui.deadlines;

import com.intellij.util.IconUtil;
import com.intellij.util.ui.JBUI;
import com.intellij.util.ui.UIUtil;
import io.github.thepieterdc.dodona.plugin.ui.Deadline;
import io.github.thepieterdc.dodona.plugin.ui.Icons;
import io.github.thepieterdc.dodona.plugin.ui.renderers.list.AbstractListCellRenderer;
import io.github.thepieterdc.dodona.plugin.ui.util.FontUtils;
import io.github.thepieterdc.dodona.plugin.ui.util.TimeUtils;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoZonedDateTime;

/**
 * Renders a deadline.
 */
final class DeadlineListCellRenderer extends AbstractListCellRenderer<Deadline> {
	private static final Color primary =
		AbstractListCellRenderer.getForeground(false, false);
	
	private static final Color secondary =
		AbstractListCellRenderer.getSecondaryForeground(false, false);
	
	private static final Border DIVIDER = BorderFactory.createMatteBorder(
		1, 5, 5, 5, secondary
	);
	
	private static final float DEADLINE_FONT_RATIO = 1.4f;
	
	private final long activeCourseId;
	
	private final JLabel course;
	private final JLabel deadline;
	private final JLabel series;
	
	/**
	 * DeadlineListCellRenderer constructor.
	 *
	 * @param activeCourseId the id of the current active course
	 */
	DeadlineListCellRenderer(final long activeCourseId) {
		super(new GridBagLayout());
		this.activeCourseId = activeCourseId;
		this.course = new JLabel("", SwingConstants.LEFT);
		this.deadline = new JLabel("", SwingConstants.RIGHT);
		this.series = new JLabel("", SwingConstants.LEFT);
		this.createLayout();
	}
	
	/**
	 * Creates the layout of the cell.
	 */
	private void createLayout() {
		final GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = JBUI.insets(4, 0, 0, 0);
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;
		
		// Course field.
		constraints.anchor = GridBagConstraints.LINE_START;
		constraints.gridx = 0;
		constraints.gridy = 0;
		this.course.setForeground(primary);
		this.add(this.course, constraints);
		
		// Series field.
		constraints.anchor = GridBagConstraints.LINE_START;
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.weighty = 1.0;
		this.series.setForeground(primary);
		this.add(this.series, constraints);
		
		// Deadline field.
		constraints.anchor = GridBagConstraints.LINE_END;
		constraints.gridheight = 2;
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.weighty = 2.0;
		final float deadlineFontSize =
			(float) this.course.getFont().getSize() * DEADLINE_FONT_RATIO;
		this.deadline.setFont(this.deadline.getFont().deriveFont(deadlineFontSize));
		this.deadline.setForeground(primary);
		this.deadline.setIcon(IconUtil.scaleByFont(
			Icons.CALENDAR.color(primary), null, deadlineFontSize
		));
		this.add(this.deadline, constraints);
	}
	
	/**
	 * Gets the background color of the cell.
	 *
	 * @param list     the list
	 * @param deadline the deadline
	 * @return the background color
	 */
	private static Color getBackground(final JList<? extends Deadline> list,
	                                   final ChronoZonedDateTime<LocalDate> deadline) {
		final ZonedDateTime now = ZonedDateTime.now();
		
		// Deadline is within one hour.
		if (deadline.isBefore(now.plusHours(1L))) {
			return JBUI.CurrentTheme.Validator.errorBackgroundColor();
		}
		
		// Deadline is today.
		if (deadline.isBefore(now.plusHours(TimeUtils.HOURS_PER_DAY))) {
			return JBUI.CurrentTheme.Validator.warningBackgroundColor();
		}
		
		// Deadline is far away.
		return AbstractListCellRenderer.getBackground(list, false);
	}
	
	@Override
	public Component getListCellRendererComponent(final JList<? extends Deadline> list,
	                                              final Deadline value,
	                                              final int index,
	                                              final boolean isSelected,
	                                              final boolean cellHasFocus) {
		// Set the upper border.
		if (index < 1) {
			this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		} else {
			this.setBorder(DIVIDER);
		}
		
		// Set the cell background color.
		UIUtil.setBackgroundRecursively(
			this,
			getBackground(list, value.getDeadline())
		);
		
		// Set the course name.
		FontUtils.boldenIf(this.course, value.getCourseId() == this.activeCourseId);
		this.course.setText(value.getCourseName());
		
		// Set the series name.
		FontUtils.boldenIf(this.series, value.getCourseId() == this.activeCourseId);
		this.series.setText(value.getSeriesName());
		
		// Set the deadline text.
		this.deadline.setFont(FontUtils.boldenIf(
			this.deadline.getFont(),
			value.getCourseId() == this.activeCourseId
		));
		this.deadline.setText(TimeUtils.fuzzy(Duration.between(
			LocalDateTime.now(),
			value.getDeadline()
		)));
		
		return this;
	}
}
