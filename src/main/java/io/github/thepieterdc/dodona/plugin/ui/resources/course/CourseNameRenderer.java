/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.ui.resources.course;

import com.intellij.ui.ColoredListCellRenderer;
import io.github.thepieterdc.dodona.plugin.ui.Icons;
import io.github.thepieterdc.dodona.resources.Course;
import org.jetbrains.annotations.NonNls;

import javax.annotation.Nullable;
import javax.swing.*;

/**
 * Renders the course name.
 */
final class CourseNameRenderer extends ColoredListCellRenderer<Course> {
	@NonNls
	private static final String cellText = "[%s] %s";
	
	@Override
	protected void customizeCellRenderer(final JList<? extends Course> jList,
	                                     @Nullable final Course course,
	                                     final int i,
	                                     final boolean b,
	                                     final boolean b1) {
		if (course != null) {
			this.append(String.format(cellText, course.getYear(), course.getName()));
			this.setIcon(Icons.CIRCLE.color(course.getColor()));
		}
	}
}