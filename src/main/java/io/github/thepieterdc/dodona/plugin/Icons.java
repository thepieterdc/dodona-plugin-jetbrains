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

import javax.swing.*;
import java.awt.*;

/**
 * Icons used throughout the plugin.
 */
public class Icons {
	public static final Icon AVATAR_DEFAULT = IconLoader.getIcon("/icons/avatar-default.svg");
	public static final Icon AVATAR_NAOS = IconLoader.getIcon("/icons/avatar-naos.svg");
	public static final Icon AVATAR_OTHER = IconLoader.getIcon("/icons/avatar-other.svg");
	
	private static final Icon CIRCLE = IconLoader.getIcon("/icons/circle.png");
	
	public static final Icon DODONA = IconLoader.getIcon("/icons/dodona.png");
	
	/**
	 * Colors the white circle in the given color.
	 *
	 * @param color the color for the circle
	 * @return the colored circle
	 */
	public static Icon createColoredCircle(final Color color) {
		return IconUtil.colorize(Icons.CIRCLE, color);
	}
}
