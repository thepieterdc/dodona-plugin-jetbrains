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
import java.util.HashMap;
import java.util.Map;

/**
 * Contains icons used throughout the plugin.
 */
public enum Icons {
	;

	public static final Icon CORRECT = IconLoader.getIcon("/icons/correct.png");
	public static final Icon INCORRECT = IconLoader.getIcon("/icons/incorrect.png");
	public static final Icon CIRCLE = IconLoader.findIcon("/icons/circle.png");
	private static Map<String, Color> COLORS = new HashMap<>();

	static {
		COLORS.put("red", Color.decode("#F44336"));
		COLORS.put("pink", Color.decode("#E91E63"));

		COLORS.put("purple", Color.decode("#9C27B0"));
		COLORS.put("deep-purple", Color.decode("#673AB7"));
		COLORS.put("indigo", Color.decode("#3F51B5"));
		COLORS.put("teal", Color.decode("#009688"));
		COLORS.put("orange", Color.decode("#FF5722"));
		COLORS.put("brown", Color.decode("#795548"));
		COLORS.put("blue-grey", Color.decode("#607D8B"));
	}

	public static Icon getColorIcon(String color) {
		if (COLORS.containsKey(color)) {
			return IconUtil.colorize(CIRCLE, COLORS.get(color));
		}
		return IconUtil.colorize(CIRCLE, Color.CYAN);
	}
}
