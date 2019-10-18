/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.notifications;

import com.intellij.openapi.ui.Messages;
import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;

/**
 * Displays error notifications and logs them.
 */
public final class ErrorReporter {
	private static final String TITLE = DodonaBundle.message("alerts.error");
	
	private static final Logger logger = LoggerFactory.getLogger(ErrorReporter.class);
	
	/**
	 * Displays an error message and logs the error.
	 *
	 * @param message the error message to log
	 * @param cause   the error cause
	 */
	public static void error(final String message, @Nullable final Throwable cause) {
		if (cause != null) {
			ErrorReporter.logger.error(message, cause);
		}
		Messages.showErrorDialog(message, ErrorReporter.TITLE);
	}
	
	/**
	 * Displays an error message.
	 *
	 * @param message the error message to log
	 */
	public static void error(final String message) {
		ErrorReporter.error(message, null);
	}
}
