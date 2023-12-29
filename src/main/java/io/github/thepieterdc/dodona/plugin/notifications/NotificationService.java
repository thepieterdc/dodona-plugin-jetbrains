/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */
package io.github.thepieterdc.dodona.plugin.notifications;

import com.intellij.openapi.project.Project;

import javax.annotation.Nonnull;

/**
 * Shows notifications.
 */
public interface NotificationService {
	/**
	 * Gets an instance of the NotificationService.
	 *
	 * @param project the current project
	 * @return the instance
	 */
	@Nonnull
	static NotificationService getInstance(final Project project) {
		return project.getService(NotificationService.class);
	}

	/**
	 * Sends the given notification.
	 *
	 * @param notification the notification to send
	 */
	void send(SendableNotification notification);
}
