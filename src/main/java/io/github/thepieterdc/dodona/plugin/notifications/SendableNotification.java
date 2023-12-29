package io.github.thepieterdc.dodona.plugin.notifications;

import com.intellij.notification.BrowseNotificationAction;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.actionSystem.AnAction;

import javax.annotation.Nullable;
import javax.swing.*;
import java.util.Optional;

public class SendableNotification {
	/**
	 * A notification that contains all of the information to be sent.
	 */

	// The notification information.
	@Nullable
	private Icon icon;
	private final String message;
	private final String title;
	private final NotificationType type;

	// The URL information.
	@Nullable
	private String linkLabel;
	@Nullable
	private String linkUrl;

	/**
	 * SendableNotification constructor.
	 *
	 * @param title   title of the balloon
	 * @param message notification contents
	 * @param type    the notification type
	 */
	private SendableNotification(final String title, final String message, final NotificationType type) {
		this.message = message;
		this.title = title;
		this.type = type;
	}

	/**
	 * Creates an error notification.
	 *
	 * @param title   the title of the notification balloon
	 * @param message the notification contents
	 * @return the created notification
	 */
	public static SendableNotification error(final String title, final String message) {
		return new SendableNotification(title, message, NotificationType.ERROR);
	}

	/**
	 * Creates an informational notification.
	 *
	 * @param title   the title of the notification balloon
	 * @param message the notification contents
	 * @return the created notification
	 */
	public static SendableNotification info(final String title, final String message) {
		return new SendableNotification(title, message, NotificationType.WARNING);
	}

	/**
	 * Gets the action of the notification.
	 *
	 * @return the action
	 */
	public Optional<AnAction> getAction() {
		// If nothing is configured, return an empty action.
		if (this.linkLabel == null || this.linkUrl == null) {
			return Optional.empty();
		}

		// Return the action.
		return Optional.of(new BrowseNotificationAction(this.linkLabel, this.linkUrl));
	}

	/**
	 * Gets the icon of the notification.
	 *
	 * @return the icon
	 */
	public Optional<Icon> getIcon() {
		return Optional.ofNullable(this.icon);
	}

	/**
	 * Gets the message of the notification.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return this.message;
	}

	/**
	 * Gets the title of the notification.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * Gets the type of the notification.
	 *
	 * @return the type
	 */
	public NotificationType getType() {
		return this.type;
	}

	/**
	 * Creates a warning notification.
	 *
	 * @param title   the title of the notification balloon
	 * @param message the notification contents
	 * @return the created notification
	 */
	public static SendableNotification warning(final String title, final String message) {
		return new SendableNotification(title, message, NotificationType.WARNING);
	}

	/**
	 * Attaches an icon to the notification.
	 *
	 * @param icon the icon to attach
	 * @return the instance
	 */
	public SendableNotification withIcon(final Icon icon) {
		this.icon = icon;
		return this;
	}

	/**
	 * Attaches a link to the notification.
	 *
	 * @param label the label of the link
	 * @param url   the url of the link
	 * @return the instance
	 */
	public SendableNotification withLink(final String label, final String url) {
		this.linkLabel = label;
		this.linkUrl = url;
		return this;
	}
}
