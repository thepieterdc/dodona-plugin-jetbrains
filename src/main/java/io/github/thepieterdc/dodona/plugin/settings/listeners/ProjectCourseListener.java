/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.settings.listeners;

import com.intellij.util.messages.Topic;

/**
 * The course of the project was either set or modified.
 */
@FunctionalInterface
public interface ProjectCourseListener {
	Topic<ProjectCourseListener> CHANGED_TOPIC =
		Topic.create("Project course changed", ProjectCourseListener.class);
	
	/**
	 * Called when the course of the project has been set.
	 *
	 * @param course the course
	 */
	void onCourseChanged(Long course);
}
