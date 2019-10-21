/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.notifications;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Displays error notifications and logs them.
 */
public class ErrorReporter {
	private static final Logger logger = LoggerFactory.getLogger(ErrorReporter.class);
	
	/**
	 * Displays an error message and logs the error.
	 *
	 * @param message the error message to log
	 * @param cause   the error cause
	 */
	public static void report(final String message, final Throwable cause) {
		ErrorReporter.logger.error(message, cause);
	}
	
	/**
	 * Displays an error message and logs the error.
	 *
	 * @param cause the error cause
	 */
	public static void report(final Throwable cause) {
		report(cause.getMessage(), cause);
	}
}
