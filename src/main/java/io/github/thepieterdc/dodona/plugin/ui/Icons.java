/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */
package io.github.thepieterdc.dodona.plugin.ui;

import com.intellij.openapi.util.IconLoader;
import com.intellij.util.IconUtil;
import com.intellij.util.ui.AnimatedIcon;
import io.github.thepieterdc.dodona.data.CourseColor;
import org.jetbrains.annotations.NonNls;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.*;

/**
 * Icons used throughout the plugin.
 */
public enum Icons implements Icon {
	// Actions.
	ACTION_SUBMIT("submit-action"),
	// Avatars.
	AVATAR_DEFAULT("avatar-default"),
	AVATAR_NAOS("avatar-naos"),
	AVATAR_OTHER("avatar-other"),
	// Circles.
	CIRCLE("circle"),
	// Dodona logo.
	DODONA("dodona"),
	// Exercise status.
	EXERCISE_CORRECT("exercise-correct"),
	EXERCISE_WRONG("exercise-wrong"),
	// Other icons.
	CALENDAR("calendar"),
	COURSE("course"),
	DEADLINES_CHECK("deadlines-check"),
	ERROR("error"),
	FILE_CODE("file-code"),
	NO_CONNECTION("no-connection"),
	QUESTION("question"),
	USER_INVALID("user-invalid");
	
	@NonNls
	private static final String COMPONENT_PREFIX = "ICON_%s";
	
	@NonNls
	private static final String ICON_PATH = "/icons/%s.svg";
	
	private final Icon icon;
	
	/**
	 * Icons constructor.
	 *
	 * @param path the path to the icon
	 */
	Icons(@NonNls final String path) {
		this.icon = getIcon(path);
	}
	
	/**
	 * Colors the icon in the given colour.
	 *
	 * @param colour the colour
	 * @return the colored icon
	 */
	@Nonnull
	public Icon color(final Color colour) {
		return IconUtil.colorize(this.icon, colour);
	}
	
	/**
	 * Colors the icon in the given colour.
	 *
	 * @param colour the colour
	 * @return the colored icon
	 */
	@Nonnull
	public Icon color(final CourseColor colour) {
		return this.color(colour.getColor());
	}
	
	/**
	 * Gets the icon at the given path.
	 *
	 * @param path the path to the icon
	 * @return the icon
	 */
	@Nonnull
	public static Icon getIcon(@NonNls final String path) {
		return IconLoader.getIcon(String.format(ICON_PATH, path));
	}
	
	@Override
	public int getIconHeight() {
		return this.icon.getIconHeight();
	}
	
	@Override
	public int getIconWidth() {
		return this.icon.getIconWidth();
	}
	
	@Override
	public void paintIcon(final Component c, final Graphics g, final int x,
	                      final int y) {
		this.icon.paintIcon(c, g, x, y);
	}
	
	/**
	 * Converts the given icon to a component.
	 *
	 * @param icon the icon to convert
	 * @return the component
	 */
	@Nonnull
	public static JComponent toComponent(final Icon icon) {
		final String name = String.format(COMPONENT_PREFIX, icon);
		return new AnimatedIcon(name, new Icon[0], icon, 0);
	}
}
