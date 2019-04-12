/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.ui.selection;

import be.ugent.piedcler.dodona.resources.Course;
import com.intellij.ui.CollectionListModel;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBList;
import com.intellij.ui.components.JBTabbedPane;

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
	
	/**
	 * CourseSelectionDialog constructor.
	 *
	 * @param courses the courses to select from
	 */
	public CourseSelectionDialog(@Nonnull final Collection<Course> courses) {
		super(null);
		this.hasItems = !courses.isEmpty();
		
		this.setContentPane(this.contentPane);
		this.setModal(true);
		
		if (courses.isEmpty()) {
			this.contentPane.add(createEmptyDialog());
			this.contentPane.setPreferredSize(new Dimension(250, -1));
		} else {
			this.contentPane.add(this.createSelectionDialog(courses));
			this.contentPane.setPreferredSize(new Dimension(400, 150));
		}
	}
	
	/**
	 * Creates an empty dialog that contains no courses.
	 *
	 * @return the empty label
	 */
	@Nonnull
	private static Component createEmptyDialog() {
		return new JBLabel("No courses were found for your account.", SwingConstants.CENTER);
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
			yearTabs.add(year, this.createSelectionTab(year, coursesPerYear.get(year)))
		);
		
		return yearTabs;
	}
	
	/**
	 * Creates a dialog that contains the courses in 1 year.
	 *
	 * @param year    the year
	 * @param courses the courses
	 * @return the dialog pane
	 */
	@Nonnull
	private Component createSelectionTab(@Nonnull final String year,
	                                     @Nonnull final Collection<Course> courses) {
		final JBList<Course> list = new JBList<>(new CollectionListModel<>(courses));
		list.addListSelectionListener(e -> this.setValue(list.getSelectedValue()));
		list.setCellRenderer(new CourseListRenderer());
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		this.addListener(course -> {
			if (course == null || !course.getYear().equals(year)) {
				list.clearSelection();
			}
		});
		
		return list;
	}
	
	@Override
	public boolean hasItems() {
		return this.hasItems;
	}
}
