/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.tasks;

import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;

/**
 * An abstract task.
 */
abstract class AbstractDodonaTask extends Task.Backgroundable implements DodonaTask {
	/**
	 * AbstractDodonaTask constructor.
	 */
	AbstractDodonaTask(final Project project,
	                   final String title) {
		super(project, title);
	}
}
