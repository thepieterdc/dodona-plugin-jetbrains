/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */
package io.github.thepieterdc.dodona.plugin.notifications.impl;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.NotificationListener;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.project.Project;
import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import io.github.thepieterdc.dodona.plugin.notifications.ErrorReporter;
import io.github.thepieterdc.dodona.plugin.notifications.NotificationService;
import org.jetbrains.annotations.NonNls;

/**
 * Default implementation of a NotificationService.
 */
public class NotificationServiceImpl implements NotificationService {
	@NonNls
	private static final String DODONA_NOTIFICATIONS = "Dodona Notifications";
	
	private static final String DEFAULT_ERROR_TITLE =
		DodonaBundle.message("alerts.error");
	
	private static final NotificationGroup GROUP = new NotificationGroup(
		DODONA_NOTIFICATIONS, NotificationDisplayType.BALLOON, true
	);
	
	private final Project project;
	
	/**
	 * NotificationServiceImpl constructor.
	 *
	 * @param project the project
	 */
	public NotificationServiceImpl(final Project project) {
		this.project = project;
	}
	
	@Override
	public void error(final String title, final String message) {
		this.notify(NotificationType.ERROR, title, message);
	}
	
	@Override
	public void error(final String message, final Throwable cause) {
		this.error(NotificationServiceImpl.DEFAULT_ERROR_TITLE, message);
		ErrorReporter.report(message, cause);
	}
	
	@Override
	public void info(final String title, final String message) {
		this.notify(NotificationType.INFORMATION, title, message);
	}
	
	/**
	 * Sends a notification.
	 *
	 * @param type    the type of the notification
	 * @param title   the title
	 * @param message the contents of the notification
	 */
	private void notify(final NotificationType type,
	                    final String title,
	                    final String message) {
		final Notification notification = NotificationServiceImpl.GROUP.createNotification(
			title,
			message,
			type,
			NotificationListener.URL_OPENING_LISTENER
		);
		
		notification.notify(this.project);
	}
	
	@Override
	public void warning(final String title, final String message) {
		this.notify(NotificationType.WARNING, title, message);
	}
}
