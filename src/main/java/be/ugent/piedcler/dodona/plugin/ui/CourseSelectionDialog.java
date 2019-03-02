/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.plugin.ui;

import be.ugent.piedcler.dodona.resources.Course;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBTabbedPane;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A dialog that allows the user to select a course.
 */
public class CourseSelectionDialog extends SelectionDialog<Course> {
	private JPanel contentPane;
	
	private final boolean hasItems;
	
	@Nullable
	private Course selectedCourse;
	
	/**
	 * SelectCourseDialog constructor.
	 *
	 * @param courses the courses to select from
	 */
	public CourseSelectionDialog(final Collection<Course> courses) {
		this.hasItems = !courses.isEmpty();
		
		this.setContentPane(this.contentPane);
		this.setModal(true);
		
		this.contentPane.add(courses.isEmpty() ? createEmptyDialog() : this.createSelectionDialog(courses));
	}
	
	/**
	 * Creates an empty dialog that contains no courses.
	 *
	 * @return the empty label
	 */
	@Nonnull
	private static Component createEmptyDialog() {
		return new JBLabel("No courses were found for your account.");
	}
	
	/**
	 * Creates a dialog that contains courses, separated by academic year using
	 * tabs.
	 *
	 * @param courses the courses to select from
	 */
	@Nonnull
	private Component createSelectionDialog(@Nonnull final Collection<Course> courses) {
		final Map<String, List<Course>> coursesPerYear = courses.stream().collect(Collectors.groupingBy(Course::getYear));
		
		final JBTabbedPane yearTabs = new JBTabbedPane();
		coursesPerYear.keySet().stream().sorted(Comparator.reverseOrder()).forEach(year ->
			yearTabs.add(this.createSelectionTab(coursesPerYear.get(year)))
		);
		
		return yearTabs;
	}
	
	/**
	 * Creates a dialog that contains the courses in 1 year.
	 *
	 * @param courses the courses
	 * @return the dialog pane
	 */
	@Nonnull
	private JPanel createSelectionTab(@Nonnull final Collection<Course> courses) {
		//		this.yearsTabs.addTab("2018-2019", new JPanel());
//		this.yearsTabs.addTab("2017-2018", new JPanel());
//
//		this.coursesList.addListSelectionListener(e -> this.selectedCourse = this.coursesList.getSelectedValue());
//		this.coursesList.setCellRenderer(new CourseListRenderer());
//		this.coursesList.setEmptyText("No courses were found for your account.");
//		this.coursesList.setModel(new CollectionListModel<>(courses));
//		this.coursesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
	
	@Nullable
	@Override
	public Course getSelectedItem() {
		return this.selectedCourse;
	}
	
	@Override
	public boolean hasItems() {
		return this.hasItems;
	}
}
