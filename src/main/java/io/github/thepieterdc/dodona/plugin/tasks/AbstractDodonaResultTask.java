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

import javax.annotation.Nonnull;

/**
 * An abstract task that returns a result.
 *
 * @param <T> type of the result
 */
abstract class AbstractDodonaResultTask<T> extends Task.WithResult<T, RuntimeException>
	implements DodonaResultTask<T> {
	/**
	 * AbstractDodonaTask constructor.
	 */
	AbstractDodonaResultTask(final Project project,
	                         final String title) {
		super(project, title, true);
	}
	
	@Nonnull
	@Override
	public T execute() {
		return ProgressManager.getInstance().run(this);
	}
}
