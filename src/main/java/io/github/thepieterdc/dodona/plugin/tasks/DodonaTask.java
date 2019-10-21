/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */
package io.github.thepieterdc.dodona.plugin.tasks;

/**
 * A long-running job.
 */
public interface DodonaTask {
	/**
	 * Executes the given task.
	 */
	void execute();
}
