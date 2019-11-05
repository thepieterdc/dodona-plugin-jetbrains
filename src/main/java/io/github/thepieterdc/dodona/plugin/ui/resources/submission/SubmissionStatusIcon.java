/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.ui.resources.submission;

import io.github.thepieterdc.dodona.data.SubmissionStatus;
import io.github.thepieterdc.dodona.exceptions.SubmissionStatusNotFoundException;
import io.github.thepieterdc.dodona.plugin.ui.Icons;
import io.reactivex.annotations.NonNull;
import org.jetbrains.annotations.NonNls;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

/**
 * Creates an icon for the given submission status.
 */
public enum SubmissionStatusIcon implements Icon {
	COMPILATION_ERROR(SubmissionStatus.COMPILATION_ERROR, "compilation-error"),
	CORRECT(SubmissionStatus.CORRECT, "correct"),
	INTERNAL_ERROR(SubmissionStatus.INTERNAL_ERROR, "internal-error"),
	MEMORY_LIMIT_EXCEEDED(SubmissionStatus.MEMORY_LIMIT_EXCEEDED, "memory-limit"),
	RUNTIME_ERROR(SubmissionStatus.RUNTIME_ERROR, "runtime-error"),
	TIME_LIMIT_EXCEEDED(SubmissionStatus.TIME_LIMIT_EXCEEDED, "time-limit"),
	WRONG(SubmissionStatus.WRONG, "wrong");
	
	private final Icon icon;
	private final SubmissionStatus status;
	
	/**
	 * SubmissionStatusIcon constructor.
	 *
	 * @param status the status
	 * @param icon   the icon
	 */
	SubmissionStatusIcon(final SubmissionStatus status,
	                     @NonNls final String icon) {
		this.icon = get(icon);
		this.status = status;
	}
	
	/**
	 * Gets the icon for the given status.
	 *
	 * @param status the status
	 * @return the icon
	 */
	@Nonnull
	public static SubmissionStatusIcon forStatus(final SubmissionStatus status) {
		return Arrays.stream(SubmissionStatusIcon.values())
			.filter(icon -> icon.status == status)
			.findAny()
			.orElseThrow(() -> new SubmissionStatusNotFoundException(status.getName()));
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
