/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin;

import be.ugent.piedcler.dodona.DodonaBuilder;
import be.ugent.piedcler.dodona.DodonaClient;
import be.ugent.piedcler.dodona.plugin.settings.DodonaSettings;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.function.Function;

/**
 * Interface to the API.
 */
public enum Api {
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
		return task.apply(getClient());
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
				return task.apply(getClient());
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
