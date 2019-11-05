/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.ui.resources.course;

import com.intellij.util.ui.GridBag;
import com.intellij.util.ui.JBUI;
import io.github.thepieterdc.dodona.plugin.ui.Icons;
import io.github.thepieterdc.dodona.plugin.ui.renderers.list.IconListCellRenderer;
import io.github.thepieterdc.dodona.resources.Course;

import javax.swing.*;
import java.awt.*;

/**
 * Renders the course name and color.
 */
final class CourseListCellRenderer extends IconListCellRenderer<Course> {
	@SuppressWarnings("InstanceVariableMayNotBeInitialized")
	private JLabel name;
	@SuppressWarnings("InstanceVariableMayNotBeInitialized")
	private JLabel teacher;
	
	/**
	 * CourseListCellRenderer constructor.
	 */
	CourseListCellRenderer() {
		super(JBUI.Borders.empty(5, 4));
	}
	
	@Override
	protected void addDetails(final JPanel container, final GridBag insets) {
		container.add(this.name, insets.nextLine().next());
		container.add(this.teacher, insets.nextLine().coverLine());
	}
	
	@Override
	protected void initialize() {
		this.name = new JLabel();
		this.teacher = new JLabel();
	}
	
	@Override
	protected void renderValue(final Course course,
	                           final Color primary,
	                           final Color secondary) {
		// Set the circle.
		this.icon.setIcon(Icons.CIRCLE.color(course.getColor()));
		
		// Set the name field.
		this.name.setForeground(primary);
		this.name.setText(course.getName());
		
		// Set the teacher if available.
		this.teacher.setForeground(secondary);
		this.teacher.setVisible(course.getTeacher().isPresent());
		course.getTeacher().ifPresent(this.teacher::setText);
	}
}
