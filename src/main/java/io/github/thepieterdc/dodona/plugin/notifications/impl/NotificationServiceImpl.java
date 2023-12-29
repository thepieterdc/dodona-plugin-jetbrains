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
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.NotificationGroupManager;
import com.intellij.openapi.project.Project;
import io.github.thepieterdc.dodona.plugin.notifications.NotificationService;
import io.github.thepieterdc.dodona.plugin.notifications.SendableNotification;
import org.jetbrains.annotations.NonNls;

/**
 * Default implementation of a NotificationService.
 */
public class NotificationServiceImpl implements NotificationService {
	@NonNls
	private static final String GROUP_ID = "Dodona Notifications";

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
	public void send(final SendableNotification notification) {
		// Get the group.
		final NotificationGroup group = NotificationGroupManager.getInstance()
			.getNotificationGroup(GROUP_ID);

		// Build the notification.
		final Notification built = group
			.createNotification(notification.getMessage(), notification.getType())
			.setTitle(notification.getTitle());

		// Add the action.
		notification.getAction().ifPresent(built::addAction);

		// Add the icon.
		notification.getIcon().ifPresent(built::setIcon);

		// Send the notification.
		built.notify(this.project);
	}
}
