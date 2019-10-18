/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.api;

import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.project.Project;
import io.github.thepieterdc.dodona.DodonaClient;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Function;

/**
 * Executes calls using the Dodona API.
 */
public interface DodonaExecutor {
	/**
	 * Executes the given call, synchronously.
	 *
	 * @param call      the call to execute
	 * @param indicator the progress indicator
	 * @param <T>       return type of the response
	 * @return the response from the call
	 */
	@Nonnull
	<T> T execute(Function<? super DodonaClient, T> call,
	              ProgressIndicator indicator);
	
	/**
	 * Executes the given call, asynchronously.
	 *
	 * @param project     the project
	 * @param title       the dialog title
	 * @param cancellable whether this request can be cancelled
	 * @param indicator   the progress indicator
	 * @param call        the call to execute
	 * @param <T>         return type of the response
	 * @return the response from the call
	 */
	@Nonnull
	<T> DodonaFuture<T> execute(@Nullable Project project,
	                            String title,
	                            boolean cancellable,
	                            ProgressIndicator indicator,
	                            Function<? super DodonaClient, ? extends T> call);
	
	/**
	 * Executes the given call, asynchronously.
	 *
	 * @param call the call to execute
	 * @param <T>  return type of the response
	 * @return the response from the call
	 */
	@Nonnull
	<T> DodonaFuture<T> execute(Function<? super DodonaClient, ? extends T> call);
}