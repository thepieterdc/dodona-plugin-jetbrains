/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.ui.resources.submission;

import io.github.thepieterdc.dodona.plugin.Icons;
import io.reactivex.annotations.NonNull;
import org.jetbrains.annotations.NonNls;

import javax.swing.*;
import java.awt.*;

/**
 * Creates an icon for the given submission status.
 */
public enum SubmissionStatusIcon implements Icon {
	COMPILATION_ERROR("compilation-error"),
	CORRECT("correct"),
	MEMORY_LIMIT_EXCEEDED("memory-limit"),
	RUNTIME_ERROR("runtime-error"),
	TIME_LIMIT_EXCEEDED("time-limit"),
	WRONG("wrong");
	
	private final Icon icon;
	
	/**
	 * SubmissionStatusIcon constructor.
	 *
	 * @param icon the icon
	 */
	SubmissionStatusIcon(@NonNls final String icon) {
		this.icon = get(icon);
	}
	
	/**
	 * Gets the submission icon with the given name.
	 *
	 * @param name the name
	 * @return the icon
	 */
	@NonNull
	private static Icon get(@NonNls final String name) {
		return Icons.getIcon("submission-status/" + name);
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
}
