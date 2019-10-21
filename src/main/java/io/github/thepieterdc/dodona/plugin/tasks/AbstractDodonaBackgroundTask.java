/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.tasks;

import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;

/**
 * An abstract task that runs in the background.
 */
abstract class AbstractDodonaBackgroundTask extends Task.Backgroundable implements DodonaBackgroundTask {
	/**
	 * AbstractDodonaBackgroundTask constructor.
	 */
	AbstractDodonaBackgroundTask(final Project project,
	                             final String title) {
		super(project, title);
	}
	
	/**
	 * Executes the given task in the background.
	 */
	public void execute() {
		ProgressManager.getInstance().run(this);
	}
}
