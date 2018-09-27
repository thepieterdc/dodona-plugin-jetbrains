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
import com.intellij.ui.CollectionListModel;
import com.intellij.ui.components.JBList;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Collection;

/**
 * A dialog that allows the user to select a course.
 */
public class SelectCourseDialog extends JDialog {
	private JPanel contentPane;
	private JBList<Course> coursesList;
	
	@Nullable
	private Course selectedCourse;
	
	/**
	 * SelectCourseDialog constructor.
	 *
	 * @param courses the courses to select from
	 */
	public SelectCourseDialog(final Collection<Course> courses) {
		this.createComponents();
		//this.coursesList.setBorder(BorderFactory.);
		this.coursesList.addListSelectionListener(e -> this.selectedCourse = this.coursesList.getSelectedValue());
		this.coursesList.setCellRenderer(new CourseListRenderer());
		this.coursesList.setEmptyText("No courses were found for your account.");
		this.coursesList.setModel(new CollectionListModel<>(courses));
		this.coursesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
	
	/**
	 * Creates the form components.
	 */
	private void createComponents() {
		this.setContentPane(this.contentPane);
		this.setModal(true);
	}
	
	/**
	 * Gets the selected course.
	 *
	 * @return the selected course
	 */
	@Nullable
	public Course getSelectedCourse() {
		return this.selectedCourse;
	}
}
