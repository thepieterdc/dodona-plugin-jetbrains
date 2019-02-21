/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.plugin.ui;

import be.ugent.piedcler.dodona.plugin.Icons;
import be.ugent.piedcler.dodona.resources.Course;

import javax.swing.*;
import java.awt.*;

import static java.lang.String.format;

/**
 * Renders the course name correctly in a list of Courses.
 */
public class CourseListRenderer extends DefaultListCellRenderer {
	private static final long serialVersionUID = 1474165572278173025L;
	
	@Override
	public Component getListCellRendererComponent(final JList<?> list,
	                                              final Object value,
	                                              final int index,
	                                              final boolean isSelected,
	                                              final boolean cellHasFocus) {
		super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		if (value instanceof Course) {
			final Course course = (Course) value;
			this.setText(format("%s (%s)", course.getName(), course.getYear()));
			this.setIcon(Icons.getColoredCircle(course.getColor().getColor()));
		}
		return this;
	}
}
