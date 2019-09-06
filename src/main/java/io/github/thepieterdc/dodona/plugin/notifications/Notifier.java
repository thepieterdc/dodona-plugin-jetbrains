/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.notifications;

import com.intellij.notification.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;

/**
 * Displays error/info/warning messages.
 */
public enum Notifier {
	;
	
	private static final Logger logger = LoggerFactory.getLogger(Notifier.class);
	
	private static final NotificationGroup NOTIFICATION_GROUP = new NotificationGroup(
		"Dodona Notifications", NotificationDisplayType.BALLOON, true
	);
	
	/**
	 * Shows an error message in a component.
	 *
	 * @param component the component
	 * @param message   the message to display
	 * @param cause     exception that lead to this error
	 */
	public static void error(@Nonnull final Component component, @Nonnull final String message, @Nonnull final Throwable cause) {
		logger.error(message, cause);
		Messages.showErrorDialog(component, message, "Error");
	}
	
	/**
	 * Shows an error message.
	 *
	 * @param project the project to display the message in
	 * @param title   title of the message
	 * @param message the message to display
	 * @param cause   cause of the error
	 */
	public static void error(@Nonnull final Project project, @Nonnull final String title,
	                         @Nonnull final String message, @Nonnull final Throwable cause) {
		logger.error(String.format("%s; %s", title, message), cause);
		notify(project, NotificationType.ERROR, title, message);
	}
	
	/**
	 * Shows an error message.
	 *
	 * @param project the project to display the message in
	 * @param message the message to display
	 * @param cause   the cause
	 */
	public static void error(@Nonnull final Project project, @Nonnull final String message,
	                         @Nonnull final Throwable cause) {
		logger.error(message, cause);
		notify(project, NotificationType.ERROR, "Error", message);
	}
	
	/**
	 * Shows an informative message in a component.
	 *
	 * @param component the component
	 * @param message   the message to display
	 */
	public static void info(@Nonnull final Component component, @Nonnull final String message) {
		logger.info(message);
		Messages.showInfoMessage(component, message, "Information");
	}
	
	/**
	 * Shows an informative message.
	 *
	 * @param project the project to display the message in
	 * @param title   title of the message
	 * @param message the message to display
	 */
	public static void info(@Nonnull final Project project, @Nonnull final String title, @Nonnull final String message) {
		logger.info(String.format("%s; %s", title, message));
		notify(project, NotificationType.INFORMATION, title, message);
	}
	
	/**
	 * Shows a notification.
	 *
	 * @param project the project to dislpay the notification in
	 * @param type    the type of the message
	 * @param title   the title of the notification
	 * @param message the message to display
	 */
	private static void notify(@Nonnull final Project project,
	                           @Nonnull final NotificationType type,
	                           @Nonnull final String title,
	                           @Nonnull final String message) {
		final Notification notification = NOTIFICATION_GROUP.createNotification(
			title,
			message,
			type,
			NotificationListener.URL_OPENING_LISTENER
		);
		
		notification.notify(project);
	}
	
	/**
	 * Shows an informative message in a component.
	 *
	 * @param component the component
	 * @param message   the message to display
	 */
	public static void success(@Nonnull final Component component, @Nonnull final String message) {
		logger.info(message);
		Messages.showInfoMessage(component, message, "Success");
	}
	
	/**
	 * Shows a warning message.
	 *
	 * @param project the project to display the message in
	 * @param title   title of the message
	 * @param message the message to display
	 * @param cause   error cause
	 */
	public static void warning(@Nonnull final Project project, @Nonnull final String title,
	                           @Nonnull final String message,
	                           @Nonnull final Throwable cause) {
		logger.warn(String.format("%s; %s", title, message), cause);
		notify(project, NotificationType.WARNING, title, message);
	}
	
	/**
	 * Shows a warning message.
	 *
	 * @param project the project to display the message in
	 * @param title   title of the message
	 * @param message the message to display
	 */
	public static void warning(@Nonnull final Project project, @Nonnull final String title, @Nonnull final String message) {
		logger.warn(String.format("%s; %s", title, message));
		notify(project, NotificationType.WARNING, title, message);
	}
	
	/**
	 * Shows a warning message.
	 *
	 * @param project the project to display the message in
	 * @param message the message to display
	 * @param cause   error cause
	 */
	public static void warning(@Nonnull final Project project, @Nonnull final String message,
	                           @Nonnull final Throwable cause) {
		logger.warn(message, cause);
		notify(project, NotificationType.WARNING, "Warning", message);
	}
}
