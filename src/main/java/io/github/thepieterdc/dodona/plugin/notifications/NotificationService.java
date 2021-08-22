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
import javax.swing.*;

/**
 * Shows notifications.
 */
public interface NotificationService {
	/**
	 * Shows an error notification.
	 *
	 * @param title   the title
	 * @param message the message contents
	 */
	void error(final String title, final String message);
	
	/**
	 * Shows an error notification.
	 *
	 * @param title   the title
	 * @param icon    custom icon
	 * @param message the message contents
	 */
	void error(final String title, final Icon icon, final String message);
	
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
	 * Shows an informational notification.
	 *
	 * @param title   the title
	 * @param icon    custom icon to display
	 * @param message the message contents
	 */
	void info(final String title, final Icon icon, final String message);
	
	/**
	 * Shows a warning notification.
	 *
	 * @param title   the title
	 * @param message the message contents
	 */
	void warning(final String title, final String message);
	
	/**
	 * Shows a warning notification.
	 *
	 * @param title   the title
	 * @param icon    custom icon to display
	 * @param message the message contents
	 */
	void warning(final String title, final Icon icon, final String message);
}
