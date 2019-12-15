/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.ui.resources.course;

import com.intellij.ui.components.JBList;
import com.intellij.ui.components.JBTabbedPane;
import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import io.github.thepieterdc.dodona.plugin.ui.listeners.ItemSelectedListener;
import io.github.thepieterdc.dodona.plugin.ui.panels.async.AsyncPanelContent;
import io.github.thepieterdc.dodona.resources.Course;
import org.jetbrains.annotations.NonNls;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * A list of courses, divided by academic year.
 */
public class TabbedCourseList extends JBTabbedPane implements AsyncPanelContent<List<Course>> {
	private final Collection<ItemSelectedListener<Course>> listeners;
	
	@Nullable
	private Course selectedCourse;
	
	/**
	 * TabbedCourseList constructor.
	 */
	public TabbedCourseList() {
		super();
		this.listeners = new HashSet<>(4);
		this.selectedCourse = null;
	}
	
	/**
	 * Creates a tab for one academic year.
	 *
	 * @param year    the academic year
	 * @param courses the courses in that year
	 * @return the created tab
	 */
	private Component createTab(final String year,
	                            final Collection<? extends Course> courses) {
		final JBList<Course> list = new JBList<>(courses);
		
		list.addListSelectionListener(e -> {
			final Course selected = list.getSelectedValue();
			if (selected != null) {
				this.setSelectedCourse(selected);
			}
		});
		
		list.setCellRenderer(new CourseListCellRenderer());
		list.setEmptyText(DodonaBundle.message("module.course.none"));
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		this.listeners.add(course -> {
			if (course == null || !course.getYear().equals(year)) {
				list.clearSelection();
			}
		});
		
		return list;
	}
	
	/**
	 * Gets the current academic year.
	 *
	 * @return the current year
	 */
	@NonNls
	@Nonnull
	private static String getAcademicYear() {
		final LocalDate now = LocalDate.now();
		if (now.getMonth().compareTo(Month.AUGUST) > 0) {
			// First semester.
			final LocalDate next = now.plusYears(1L);
			return now.getYear() + "-" + next.getYear();
		} else {
			// Second semester.
			final LocalDate previous = now.minusYears(1L);
			return previous.getYear() + "-" + now.getYear();
		}
	}
	
	/**
	 * Gets the selected course, empty if no course is selected.
	 *
	 * @return the selected course
	 */
	@Nonnull
	public Optional<Course> getSelectedCourse() {
		return Optional.ofNullable(this.selectedCourse);
	}
	
	/**
	 * Sets the selected course.
	 *
	 * @param course the selected course
	 */
	private void setSelectedCourse(@Nullable final Course course) {
		if (!Objects.equals(this.selectedCourse, course)) {
			this.selectedCourse = course;
			this.listeners.forEach(listener -> listener.onItemSelected(course));
		}
	}
	
	@Override
	public void update(final List<Course> courses) {
		// Remove the previous tabs.
		this.removeAll();
		
		// Group the courses by academic year.
		final Map<String, List<Course>> coursesPerYear =
			courses.stream().collect(Collectors.groupingBy(Course::getYear));
		
		// Iterate over all the academic years and append a tab for each year.
		coursesPerYear.entrySet().stream()
			.sorted((o1, o2) -> o2.getKey().compareTo(o1.getKey()))
			.forEach(e ->
				this.add(e.getKey(), this.createTab(e.getKey(), e.getValue()))
			);
		
		// Failsafe in case no courses were subscribed to.
		if (courses.isEmpty()) {
			final String year = getAcademicYear();
			this.add(year, this.createTab(year, Collections.emptyList()));
		}
	}
}
