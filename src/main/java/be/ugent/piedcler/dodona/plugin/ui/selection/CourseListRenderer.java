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
import com.intellij.ui.ColoredListCellRenderer;
import io.github.thepieterdc.dodona.resources.Course;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.swing.*;

/**
 * Renders the course name correctly in a list of Courses.
 */
public class CourseListRenderer extends ColoredListCellRenderer<Course> {
	@Override
	protected void customizeCellRenderer(@NotNull JList<? extends Course> list,
	                                     @Nonnull final Course course,
	                                     int index,
	                                     boolean selected,
	                                     boolean hasFocus) {
		this.append(course.getName());
		this.setIcon(Icons.getColoredCircle(course.getColor().getColor()));
	}
}
