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
import io.github.thepieterdc.dodona.exceptions.AuthenticationException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Function;

/**
 * Executor that errors on every call.
 */
class MissingExecutorImpl implements DodonaExecutor {
	static DodonaExecutor INSTANCE = new MissingExecutorImpl();
	
	/**
	 * MissingExecutorImpl constructor.
	 */
	private MissingExecutorImpl() {
	}
	
	@Nonnull
	@Override
	public <T> T execute(final Function<? super DodonaClient, T> call,
	                     final ProgressIndicator indicator) {
		throw AuthenticationException.missing();
	}
	
	@Nonnull
	@Override
	public <T> DodonaFuture<T> execute(@Nullable final Project project,
	                                   final String title,
	                                   final boolean cancellable,
	                                   final ProgressIndicator indicator,
	                                   final Function<? super DodonaClient, ? extends T> call) {
		final DodonaFuture<T> ret = new DodonaFuture<>();
		ret.completeExceptionally(AuthenticationException.missing());
		return ret;
	}
	
	@Nonnull
	@Override
	public <T> DodonaFuture<T> execute(final Function<? super DodonaClient, ? extends T> call) {
		final DodonaFuture<T> ret = new DodonaFuture<>();
		ret.completeExceptionally(AuthenticationException.missing());
		return ret;
	}
}
