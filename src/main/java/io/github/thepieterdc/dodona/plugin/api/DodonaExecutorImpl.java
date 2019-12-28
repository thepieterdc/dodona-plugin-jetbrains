/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package io.github.thepieterdc.dodona.plugin.api;

import io.github.thepieterdc.dodona.plugin.authentication.ui.DodonaLoginDialog;
import be.ugent.piedcler.dodona.plugin.exceptions.UserAbortedException;
import be.ugent.piedcler.dodona.plugin.settings.DodonaSettings;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import io.github.thepieterdc.dodona.DodonaBuilder;
import io.github.thepieterdc.dodona.DodonaClient;
import io.github.thepieterdc.dodona.exceptions.AuthenticationException;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.function.Function;

/**
 * Interface to the API.
 */
public enum DodonaExecutorImpl {
	;
	
	private static DodonaClient client;
	
	/**
	 * Executes the given task in a modal. Uses the configured credentials.
	 *
	 * @param project the project to call the task on
	 * @param task    the task to execute
	 * @param <T>     type class of the result
	 * @return the result
	 * @throws IOException network connectivity issues
	 */
	@Nonnull
	public static <T> T call(@Nonnull final Project project,
	                         @Nonnull final Function<DodonaClient, T> task) throws IOException {
		try {
			return task.apply(getClient());
		} catch (final AuthenticationException ex) {
			ApplicationManager.getApplication().invokeAndWait(() -> {
				final DodonaSettings settings = DodonaSettings.getInstance();
				
				final DodonaLoginDialog dialog = new DodonaLoginDialog(project, settings.getToken());
				dialog.show();
				
				if (dialog.isOK()) {
					settings.setToken(dialog.getToken());
				} else {
					throw new UserAbortedException();
				}
			});
			
			return call(project, task);
		}
	}
	
	/**
	 * Executes the given task in a modal. Uses the configured credentials.
	 *
	 * @param project the project to call the task on
	 * @param title   the title for the progress indicator
	 * @param task    the task to execute
	 * @param <T>     type class of the result
	 * @return the result
	 * @throws IOException network connectivity issues
	 */
	@Nonnull
	public static <T> T callModal(@Nonnull final Project project,
	                              @Nonnull final String title,
	                              @Nonnull final Function<DodonaClient, T> task) throws IOException {
		return ProgressManager.getInstance().run(new Task.WithResult<T, IOException>(project, title, true) {
			@Override
			protected T compute(@NotNull final ProgressIndicator indicator) throws IOException {
				return call(project, task);
			}
		});
	}
	
	/**
	 * Executes the given task in a modal.
	 *
	 * @param project the project to call the task on
	 * @param title   the title for the progress indicator
	 * @param host    the host to call
	 * @param token   the authentication token
	 * @param task    the task to execute
	 * @param <T>     type class of the result
	 * @return the result
	 * @throws IOException network connectivity issues
	 */
	@Nonnull
	public static <T> T callModal(@Nonnull final Project project,
	                              @Nonnull final String title,
	                              @Nonnull final String host,
	                              @Nonnull final String token,
	                              @Nonnull final Function<DodonaClient, T> task) throws IOException {
		final DodonaClient client = DodonaBuilder.builder()
			.setApiToken(token)
			.setHost(host)
			.setUserAgent(getUserAgent())
			.build();
		
		return ProgressManager.getInstance().run(new Task.WithResult<T, IOException>(project, title, true) {
			@Override
			protected T compute(@NotNull final ProgressIndicator indicator) throws IOException {
				return task.apply(client);
			}
		});
	}
	
	/**
	 * Clears the instance of the client.
	 */
	public static void clearClient() {
		client = null;
	}
	
	/**
	 * Gets a Dodona client from the configured credentials.
	 *
	 * @return client
	 */
	@Nonnull
	private static DodonaClient getClient() {
		if (client == null) {
			final DodonaSettings settings = DodonaSettings.getInstance();
			
			client = DodonaBuilder.builder()
				.setApiToken(settings.getToken())
				.setHost(settings.getHost())
				.setUserAgent(getUserAgent())
				.build();
		}
		return client;
	}
	
	/**
	 * Gets the user agent to send in API calls.
	 *
	 * @return the user agent
	 */
	@Nonnull
	private static String getUserAgent() {
		final String version = BuildConfig.VERSION;
		return String.format("DodonaPlugin/JetBrains-%s", version);
	}
}
