/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */
package io.github.thepieterdc.dodona.plugin.ui;

import com.intellij.ui.JBColor;
import com.intellij.util.ui.UIUtil;
import org.jetbrains.annotations.NonNls;

import java.awt.*;

/**
 * Default colours used in the plugin.
 */
public enum TextColors {
	;
	@NonNls
	public static final Color PRIMARY = JBColor.namedColor(
		"Table.foreground", UIUtil.getListForeground()
	);
	@NonNls
	public static final Color SECONDARY = JBColor.namedColor(
		"Component.infoForeground", UIUtil.getContextHelpForeground()
	);
}
