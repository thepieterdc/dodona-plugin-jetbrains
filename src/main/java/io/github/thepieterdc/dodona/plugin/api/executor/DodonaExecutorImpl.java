/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.api.executor;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import io.github.thepieterdc.dodona.DodonaClient;
import io.github.thepieterdc.dodona.plugin.api.DodonaFuture;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Function;

/**
 * Executes calls using the Dodona API.
 */
class DodonaExecutorImpl implements DodonaExecutor {
	private final DodonaClient client;
	
	/**
	 * DodonaExecutorImpl constructor.
	 *
	 * @param client the Dodona client
	 */
	DodonaExecutorImpl(final DodonaClient client) {
		this.client = client;
	}
	
	@Nonnull
	@Override
	public <T> T execute(final Function<? super DodonaClient, T> call,
	                     final ProgressIndicator indicator) {
		indicator.checkCanceled();
		return call.apply(this.client);
	}
	
	@Nonnull
	@Override
	public <T> DodonaFuture<T> execute(@Nullable final Project project,
	                                   final String title,
	                                   final boolean cancellable,
	                                   final ProgressIndicator indicator,
	                                   final Function<? super DodonaClient, ? extends T> call) {
		final DodonaFuture<T> ret = new DodonaFuture<>();
		ProgressManager.getInstance().runProcessWithProgressAsynchronously(
			new Task.Backgroundable(project, title, cancellable) {
				@Override
				public void onCancel() {
					ret.cancel(true);
				}
				
				@Override
				public void onThrowable(@NotNull final Throwable error) {
					ret.completeExceptionally(error);
				}
				
				@Override
				public void run(@NotNull final ProgressIndicator progressIndicator) {
					try {
						final T response = call.apply(DodonaExecutorImpl.this.client);
						ret.complete(response);
					} catch (final RuntimeException ex) {
						ret.completeExceptionally(ex);
					}
				}
			}, indicator
		);
		return ret;
	}
	
	@Nonnull
	@Override
	public <T> DodonaFuture<T> execute(final Function<? super DodonaClient, ? extends T> call) {
		final DodonaFuture<T> ret = new DodonaFuture<>();
		ApplicationManager.getApplication().executeOnPooledThread(() -> {
			final T response;
			
			try {
				response = call.apply(this.client);
			} catch (final RuntimeException ex) {
				ret.completeExceptionally(ex);
				return;
			}
			
			// Successful.
			ret.complete(response);
		});
		return ret;
	}
}
