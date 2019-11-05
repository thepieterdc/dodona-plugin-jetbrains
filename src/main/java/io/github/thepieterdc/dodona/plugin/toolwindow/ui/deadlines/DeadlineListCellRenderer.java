/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.toolwindow.ui.deadlines;

import com.intellij.util.ui.JBUI;
import com.intellij.util.ui.UIUtil;
import io.github.thepieterdc.dodona.plugin.ui.Deadline;
import io.github.thepieterdc.dodona.plugin.ui.renderers.list.AbstractListCellRenderer;
import io.github.thepieterdc.dodona.plugin.ui.util.FontUtils;
import io.github.thepieterdc.dodona.plugin.ui.util.TimeUtils;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoZonedDateTime;

/**
 * Renders a deadline.
 */
final class DeadlineListCellRenderer extends AbstractListCellRenderer<Deadline> {
	private static final float DEADLINE_SIZE = 20.0f;
	
	private static final long HOURS_PER_DAY = 24L;
	
	private static final Color primary =
		AbstractListCellRenderer.getForeground(false, false);
	
	private static final Color secondary =
		AbstractListCellRenderer.getSecondaryForeground(false, false);
	
	private static final Border divider = BorderFactory.createMatteBorder(
		1, 0, 0, 0, JBUI.CurrentTheme.Focus.focusColor()
	);
	
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
		super();
		this.activeCourseId = activeCourseId;
		this.course = new JLabel("", SwingConstants.CENTER);
		this.deadline = new JLabel("", SwingConstants.CENTER);
		this.series = new JLabel("", SwingConstants.CENTER);
		this.createLayout();
	}
	
	/**
	 * Creates the layout of the cell.
	 */
	private void createLayout() {
		// Main panel.
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		
		// Content panel.
		final JPanel content = new JPanel();
		content.setBorder(JBUI.Borders.empty(30, 6));
		content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));
		
		// Configure the course field.
		this.course.setAlignmentX(Component.CENTER_ALIGNMENT);
		content.add(this.course);
		
		// Configure the series field.
		content.add(Box.createRigidArea(new Dimension(0, 2)));
		this.series.setAlignmentX(Component.CENTER_ALIGNMENT);
		content.add(this.series);
		
		// Configure the deadline field.
		content.add(Box.createRigidArea(new Dimension(0, 5)));
		this.deadline.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.deadline.setFont(this.deadline.getFont().deriveFont(DEADLINE_SIZE));
		content.add(this.deadline);
		
		// Append the content panel to the cell.
		this.add(content);
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
		if (deadline.isBefore(now.plusHours(HOURS_PER_DAY))) {
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
		if (index > 0) {
			this.setBorder(divider);
		}
		
		// Set the cell background color.
		UIUtil.setBackgroundRecursively(
			this,
			getBackground(list, value.getDeadline())
		);
		
		// Set the course name.
		FontUtils.boldenIf(this.course, value.getCourseId() == this.activeCourseId);
		this.course.setForeground(secondary);
		this.course.setText(value.getCourseName());
		
		// Set the series name.
		FontUtils.boldenIf(this.series, value.getCourseId() == this.activeCourseId);
		this.series.setForeground(secondary);
		this.series.setText(value.getSeriesName());
		
		// Set the deadline text.
		this.deadline.setFont(FontUtils.boldenIf(
			this.deadline.getFont().deriveFont(DEADLINE_SIZE),
			value.getCourseId() == this.activeCourseId
		));
		this.deadline.setForeground(primary);
		this.deadline.setText(TimeUtils.fuzzy(value.getDeadline()));
		
		return this;
	}
}
