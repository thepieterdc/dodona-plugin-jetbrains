/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.reporting;

import com.intellij.notification.*;
import com.intellij.openapi.application.ApplicationManager;

/**
 * Reports statuses using the status bar.
 */
public enum NotificationReporter {
	;
	
	private static final NotificationGroup NOTIFICATION_GROUP = new NotificationGroup(
			"Dodona Notifications", NotificationDisplayType.BALLOON, true
	);
	
	/**
	 * Shows an error balloon.
	 *
	 * @param message the message to display
	 */
	public static void error(final String message) {
		NotificationReporter.showBalloon("Error", NotificationType.ERROR, message);
	}
	
	/**
	 * Shows an informative balloon.
	 *
	 * @param message the message to display
	 */
	public static void info(final String message) {
		NotificationReporter.showBalloon("Information", NotificationType.INFORMATION, message);
	}
	
	/**
	 * Shows a balloon.
	 *
	 * @param type    the type of the message
	 * @param message the message to display
	 */
	private static void showBalloon(final String title, final NotificationType type, final String message) {
		ApplicationManager.getApplication().invokeLater(() -> {
			final Notification notification = NotificationReporter.NOTIFICATION_GROUP.createNotification(
					title,
					message,
					type,
					NotificationListener.URL_OPENING_LISTENER);
			Notifications.Bus.notify(notification);
		});
	}
	
	/**
	 * Shows a warning balloon.
	 *
	 * @param message the message to display
	 */
	public static void warning(final String message) {
		NotificationReporter.showBalloon("Warning", NotificationType.WARNING, message);
	}
}
