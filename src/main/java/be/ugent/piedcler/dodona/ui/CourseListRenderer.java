/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.ui;

import be.ugent.piedcler.dodona.dto.Course;
import com.intellij.ide.IconProvider;
import com.intellij.openapi.util.IconLoader;
import com.intellij.ui.DrawUtil;
import com.intellij.ui.JBColor;
import com.intellij.util.IconUtil;
import com.intellij.util.ImageLoader;
import org.jetbrains.plugins.ipnb.editor.panels.code.IpnbErrorPanel;

import javax.swing.*;
import java.awt.*;

import static javafx.scene.paint.Color.gray;
import static javafx.scene.paint.Color.web;

/**
 * Renders the course name correctly in a list of Courses.
 */
public class CourseListRenderer extends DefaultListCellRenderer {
	private static final long serialVersionUID = 1474165572278173025L;

	private static final Icon CIRCLE_ICON = IconLoader.findIcon("/icons/circle.png");

	@Override
	public Component getListCellRendererComponent(final JList<?> list,
												  final Object value,
												  final int index,
												  final boolean isSelected,
												  final boolean cellHasFocus) {
		super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		if (value instanceof Course) {
			final Course course = (Course) value;
			Color color;
			try {
				color = (Color) Color.class.getField(course.getColor()).get(null);
			} catch (Exception e) {
				color = Color.BLUE;
			}
			final Icon coloredIcon = IconUtil.colorize(CIRCLE_ICON, color);
			this.setText(course.getName());
			this.setIcon(coloredIcon);
		}
		return this;
	}
}
