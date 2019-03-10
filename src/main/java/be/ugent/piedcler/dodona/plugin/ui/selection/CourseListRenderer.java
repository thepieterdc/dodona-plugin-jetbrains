/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.ui.selection;

import be.ugent.piedcler.dodona.plugin.Icons;
import be.ugent.piedcler.dodona.resources.Course;

import javax.swing.*;
import java.awt.*;

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
		super.getListCellRendererComponent(list, value, index, isSelected, false);
		if (value instanceof Course) {
			final Course course = (Course) value;
			this.setText(course.getName());
			this.setIcon(Icons.getColoredCircle(course.getColor().getColor()));
		}
		return this;
	}
}
