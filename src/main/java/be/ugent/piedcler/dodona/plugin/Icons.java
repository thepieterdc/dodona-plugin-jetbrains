/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.plugin;

import com.intellij.openapi.util.IconLoader;
import com.intellij.util.IconUtil;

import javax.swing.*;
import java.awt.*;

/**
 * Contains icons used throughout the plugin.
 */
public enum Icons {
	;
	
	private static final Icon CIRCLE = IconLoader.findIcon("/icons/circle.png");
	
	public static final Icon CORRECT = IconLoader.getIcon("/icons/correct.png");
	public static final Icon DODONA = IconLoader.getIcon("/icons/dodona.png");
	public static final Icon INCORRECT = IconLoader.getIcon("/icons/incorrect.png");
	
	/**
	 * Gets a colored circle.
	 *
	 * @param color the color for the circle
	 * @return the colored circle
	 */
	public static Icon getColoredCircle(final Color color) {
		return IconUtil.colorize(CIRCLE, color);
	}
}
