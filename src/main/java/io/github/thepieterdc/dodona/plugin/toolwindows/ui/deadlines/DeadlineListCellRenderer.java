/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.toolwindows.ui.deadlines;

import com.intellij.util.ui.JBUI;
import com.intellij.util.ui.UIUtil;
import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import io.github.thepieterdc.dodona.plugin.toolwindows.tabs.DeadlinesTab;
import io.github.thepieterdc.dodona.plugin.ui.AbstractListCellRenderer;
import io.github.thepieterdc.dodona.plugin.ui.util.FontUtils;
import org.jetbrains.annotations.Nls;

import javax.annotation.Nonnull;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Renders a deadline.
 */
final class DeadlineListCellRenderer extends AbstractListCellRenderer<DeadlinesTab.Deadline> {
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
		this.deadline.setFont(this.deadline.getFont().deriveFont(DeadlineListCellRenderer.DEADLINE_SIZE));
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
	private static Color getBackground(final JList<? extends DeadlinesTab.Deadline> list,
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
	public Component getListCellRendererComponent(final JList<? extends DeadlinesTab.Deadline> list,
	                                              final DeadlinesTab.Deadline value,
	                                              final int index,
	                                              final boolean isSelected,
	                                              final boolean cellHasFocus) {
		// Set the upper border.
		if (index > 0) {
			this.setBorder(DeadlineListCellRenderer.divider);
		}
		
		// Set the cell background color.
		UIUtil.setBackgroundRecursively(
			this,
			DeadlineListCellRenderer.getBackground(list, value.deadline)
		);
		
		// Set the course name.
		FontUtils.boldenIf(this.course, value.courseId == this.activeCourseId);
		this.course.setForeground(DeadlineListCellRenderer.secondary);
		this.course.setText(value.courseName);
		
		// Set the series name.
		FontUtils.boldenIf(this.series, value.courseId == this.activeCourseId);
		this.series.setForeground(DeadlineListCellRenderer.secondary);
		this.series.setText(value.seriesName);
		
		// Set the deadline text.
		this.deadline.setFont(FontUtils.boldenIf(
			this.deadline.getFont().deriveFont(DeadlineListCellRenderer.DEADLINE_SIZE),
			value.courseId == this.activeCourseId
		));
		this.deadline.setForeground(DeadlineListCellRenderer.primary);
		this.deadline.setText(DeadlineListCellRenderer.humanize(value.deadline));
		
		return this;
	}
	
	/**
	 * Converts the deadline timestamp to a "from now" string.
	 *
	 * @param deadline the deadline
	 * @return the time remaining until the deadline
	 */
	@Nls
	@Nonnull
	static String humanize(final ChronoZonedDateTime<LocalDate> deadline) {
		final ZonedDateTime now = ZonedDateTime.now();
		
		// Ensure the deadline is in the future.
		if (deadline.isBefore(now)) {
			return DodonaBundle.message("deadline.missed");
		}
		
		// Check for deadlines in the next minute.
		if (deadline.isBefore(now.plusMinutes(1L))) {
			return DodonaBundle.message("deadline.now");
		}
		
		// Within 1 hour.
		if (deadline.isBefore(now.plusHours(1L))) {
			final long minutesLeft = now.until(deadline, ChronoUnit.MINUTES);
			if (minutesLeft == 1L) {
				return DodonaBundle.message("deadline.one_minute_left");
			}
			
			return DodonaBundle.message("deadline.minutes_left", minutesLeft);
		}
		
		// Within 24 hours.
		if (deadline.isBefore(now.plusHours(HOURS_PER_DAY))) {
			final long hoursLeft = now.until(deadline, ChronoUnit.HOURS);
			if (hoursLeft == 1L) {
				return DodonaBundle.message("deadline.one_hour_left");
			}
			
			return DodonaBundle.message("deadline.hours_left", hoursLeft);
		}
		
		// Far away.
		final long daysLeft = now.until(deadline, ChronoUnit.DAYS);
		if (daysLeft == 1L) {
			return DodonaBundle.message("deadline.one_day_left");
		}
		
		return DodonaBundle.message("deadline.days_left", daysLeft);
	}
}
