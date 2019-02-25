/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.notifications;

import com.intellij.openapi.ui.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.awt.*;

/**
 * Displays error/info/warning messages.
 */
public enum Notifier {
	;
	
	private static final Logger logger = LoggerFactory.getLogger(Notifier.class);
	
	/**
	 * Shows an informative message in a component.
	 *
	 * @param component the component
	 * @param message the message to display
	 */
	public static void info(@Nonnull final Component component, @Nonnull final String message) {
		logger.info(message);
		Messages.showInfoMessage(component, message, "Information");
	}
	
	/**
	 * Shows an informative message in a component.
	 *
	 * @param component the component
	 * @param message the message to display
	 */
	public static void success(@Nonnull final Component component, @Nonnull final String message) {
		logger.info(message);
		Messages.showInfoMessage(component, message, "Success");
	}
}
