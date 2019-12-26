/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */
package io.github.thepieterdc.dodona.plugin.notifications.impl;

import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.NotificationListener;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.project.Project;
import io.github.thepieterdc.dodona.plugin.notifications.NotificationService;
import org.jetbrains.annotations.NonNls;

import javax.annotation.Nullable;
import javax.swing.*;

/**
 * Default implementation of a NotificationService.
 */
public class NotificationServiceImpl implements NotificationService {
	@NonNls
	private static final String DODONA_NOTIFICATIONS = "Dodona Notifications";
	
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
		this.notify(NotificationType.ERROR, null, title, message);
	}
	
	@Override
	public void error(final String title, final Icon icon,
	                  final String message) {
		this.notify(NotificationType.ERROR, icon, title, message);
	}
	
	@Override
	public void info(final String title, final Icon icon, final String message) {
		this.notify(NotificationType.INFORMATION, icon, title, message);
	}
	
	/**
	 * Sends a notification.
	 *
	 * @param type    the type of the notification
	 * @param icon    the icon
	 * @param title   the title
	 * @param message the contents of the notification
	 */
	private void notify(final NotificationType type,
	                    @Nullable final Icon icon,
	                    final String title,
	                    final String message) {
		GROUP
			.createNotification(title, message, type, NotificationListener.URL_OPENING_LISTENER)
			.setIcon(icon)
			.notify(this.project);
	}
	
	@Override
	public void warning(final String title, final String message) {
		this.notify(NotificationType.WARNING, null, title, message);
	}
	
	@Override
	public void warning(final String title, final Icon icon,
	                    final String message) {
		this.notify(NotificationType.WARNING, icon, title, message);
	}
}
