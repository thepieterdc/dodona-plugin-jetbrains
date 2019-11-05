/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.broadcast;

import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.project.Project;
import com.intellij.util.messages.MessageBusConnection;

/**
 * Broadcasts custom events.
 */
public class EventBroadcaster {
	/**
	 * EventBroadcaster constructor.
	 *
	 * @param project the current opened project
	 */
	public EventBroadcaster(final Project project) {
		final MessageBusConnection conn = project.getMessageBus().connect();
		
		// Listen for the current opened exercise.
		conn.subscribe(
			FileEditorManagerListener.FILE_EDITOR_MANAGER,
			new CurrentExerciseBroadcaster(project)
		);
	}
}
