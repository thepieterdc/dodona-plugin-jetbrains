/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */
package io.github.thepieterdc.dodona.plugin.tasks;

import javax.annotation.Nonnull;

/**
 * A long-running job that generates a result.
 */
@FunctionalInterface
public interface DodonaResultTask<T> {
	/**
	 * Executes the given task.
	 *
	 * @return the result
	 */
	@Nonnull
	T execute();
}
