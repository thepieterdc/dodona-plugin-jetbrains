/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.plugin;

import be.ugent.piedcler.dodona.DodonaBuilder;
import be.ugent.piedcler.dodona.DodonaClient;
import be.ugent.piedcler.dodona.exceptions.AuthenticationException;
import be.ugent.piedcler.dodona.plugin.authentication.AuthenticationData;
import be.ugent.piedcler.dodona.plugin.authentication.Authenticator;
import be.ugent.piedcler.dodona.plugin.settings.SettingsHelper;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.util.function.Function;

/**
 * Interface to the API.
 */
public final class Api {
	private static final Logger logger = LoggerFactory.getLogger(Api.class);
	
	@Nullable
	private static DodonaClient instance;
	
	/**
	 * Calls the given task using the given authentication credentials. In case
	 * an AuthenticationException is thrown, the LoginDialog is shown.
	 *
	 * @param project   the current project
	 * @param data      the authentication data
	 * @param indicator progress indicator
	 * @param task      the task to call
	 * @param <T>       type class of the result
	 * @return the result of the call
	 */
	@Nonnull
	public static <T> T call(@Nonnull final Project project,
	                         @Nullable final AuthenticationData data,
	                         @Nonnull final ProgressIndicator indicator,
	                         @Nonnull final Function<DodonaClient, T> task) {
		if(data == null) {
			final AuthenticationData newAuthData = Authenticator.askCredentials(project, data, indicator);
			return call(project, newAuthData, indicator, task);
		}
		
		try {
			return task.apply(DodonaBuilder.builder()
				.setApiToken(data.getToken())
				.setHost(data.getHost())
				.build());
		} catch (final AuthenticationException ex) {
			logger.error(ex.getMessage(), ex);
			final AuthenticationData newAuthData = Authenticator.askCredentials(project, data, indicator);
			return call(project, newAuthData, indicator, task);
		}
	}
	
	/**
	 * Removes the instance of the Dodona client.
	 */
	@Deprecated
	public static void clearInstance() {
		instance = null;
	}
	
	/**
	 * Gets an instance of the Dodona client. This instance may not be saved by
	 * the caller since the instance will become invalid upon changing the
	 * settings.
	 *
	 * @return Dodona instance
	 * @deprecated Don't use this directly, use the error wrapper Api#call.
	 */
	@Deprecated
	public static DodonaClient getInstance() {
		if (instance == null) {
			instance = DodonaBuilder.builder()
				.setApiToken(SettingsHelper.getApiKey())
				.setHost(SettingsHelper.getDodonaUrl())
				.setUserAgent(getUserAgent())
				.build();
		}
		return instance;
	}
	
	/**
	 * Gets the user agent to send in API calls.
	 *
	 * @return the user agent
	 */
	private static String getUserAgent() {
		final String version = BuildConfig.VERSION;
		return String.format("DodonaPlugin/JetBrains-%s", version);
	}
}
