/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */
package io.github.thepieterdc.dodona.plugin;

import com.intellij.openapi.util.IconLoader;
import com.intellij.util.IconUtil;
import io.github.thepieterdc.dodona.data.CourseColor;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.*;

/**
 * Icons used throughout the plugin.
 */
public class Icons {
	public static final Icon AVATAR_DEFAULT = IconLoader.getIcon("/icons/avatar-default.svg");
	public static final Icon AVATAR_NAOS = IconLoader.getIcon("/icons/avatar-naos.svg");
	public static final Icon AVATAR_OTHER = IconLoader.getIcon("/icons/avatar-other.svg");
	
	private static final Icon CIRCLE = IconLoader.getIcon("/icons/circle.svg");
	
	public static final Icon DODONA = IconLoader.getIcon("/icons/dodona.svg");
	
	public static final Icon EXERCISE_CORRECT = IconLoader.getIcon("/icons/exercise-correct.svg");
	public static final Icon EXERCISE_WRONG = IconLoader.getIcon("/icons/exercise-wrong.svg");
	
	public static final Icon SUBMIT_ACTION = IconLoader.getIcon("/icons/submit-action.svg");
	
	/**
	 * Colors the given icon in the requested color.
	 *
	 * @param icon the icon to colour
	 * @param color the colour
	 * @return the coloured icon
	 */
	@Nonnull
	public static Icon colorize(final Icon icon, final Color color) {
		return IconUtil.colorize(icon, color);
	}
	
	/**
	 * Colours the circle icon in the given colour.
	 *
	 * @param color the colour for the circle
	 * @return the coloured circle
	 */
	@Nonnull
	public static Icon createColoredCircle(final Color color) {
		return colorize(CIRCLE, color);
	}
	
	/**
	 * Colours the circle icon in the given colour.
	 *
	 * @param color the colour for the circle
	 * @return the coloured circle
	 */
	@Nonnull
	public static Icon createColoredCircle(final CourseColor color) {
		return createColoredCircle(color.getColor());
	}
}
