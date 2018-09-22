/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.api;

import be.ugent.piedcler.dodona.dto.Solution;
import be.ugent.piedcler.dodona.exceptions.apitoken.ApiTokenInvalidException;
import be.ugent.piedcler.dodona.exceptions.apitoken.ApiTokenNotSetException;
import be.ugent.piedcler.dodona.exceptions.notfound.ResourceNotFoundException;
import be.ugent.piedcler.dodona.settings.SettingsHelper;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.http.HttpStatus;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.function.Supplier;

/**
 * Provides HTTP methods.
 * <p>
 * TODO: rework this using a HTTP library, issue #5.
 * TODO: better error handling, issue #7.
 */
public enum Http {
	;
	
	private static final String HEADER_ACCEPT_KEY = "Accept";
	private static final String HEADER_ACCEPT_VALUE = "application/json";
	private static final String HEADER_AUTHORIZATION = "Authorization";
	private static final String HEADER_CONTENT_TYPE = "Content-Type";
	
	private static final ObjectMapper mapper = new ObjectMapper()
		.setSerializationInclusion(JsonInclude.Include.NON_NULL)
		.enable(SerializationFeature.WRAP_ROOT_VALUE);
	
	/**
	 * Sends an authenticated HTTP GET request.
	 *
	 * @param endpoint        the endpoint to call
	 * @param resultCls       the result class
	 * @param notFoundHandler handler to execute for 404 errors
	 * @return the response wrapped in the given result class
	 */
	public static <T> T get(final String endpoint,
	                        final Class<T> resultCls,
	                        final Supplier<ResourceNotFoundException> notFoundHandler) {
		final String apiKey = SettingsHelper.getApiKey();
		if (apiKey.isEmpty()) {
			throw new ApiTokenNotSetException();
		}
		
		try {
			final URL url = new URL(endpoint);
			
			final HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			connection.setRequestProperty(Http.HEADER_ACCEPT_KEY, Http.HEADER_ACCEPT_VALUE);
			connection.setRequestProperty(Http.HEADER_AUTHORIZATION, apiKey);
			
			if (connection.getResponseCode() == HttpStatus.SC_UNAUTHORIZED) {
				throw new ApiTokenInvalidException();
			}
			
			if (connection.getResponseCode() == HttpStatus.SC_NOT_FOUND) {
				throw notFoundHandler.get();
			}
			
			return Http.mapper.readValue(connection.getInputStream(), resultCls);
		} catch (final IOException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	/**
	 * Sends an authenticated HTTP POST request.
	 *
	 * @param endpoint  the endpoint to call
	 * @param solution  the solution to send
	 * @param resultCls the result class
	 */
	public static <T> T post(final String endpoint, final Solution solution, final Class<T> resultCls) {
		final String apiKey = SettingsHelper.getApiKey();
		if (apiKey.isEmpty()) {
			throw new ApiTokenNotSetException();
		}
		
		try {
			final URL url = new URL(endpoint);
			
			final HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty(HEADER_ACCEPT_KEY, HEADER_ACCEPT_VALUE);
			connection.setRequestProperty(HEADER_AUTHORIZATION, apiKey);
			connection.setRequestProperty(HEADER_CONTENT_TYPE, HEADER_ACCEPT_VALUE);
			
			try (final OutputStream out = connection.getOutputStream()) {
				mapper.writeValue(out, solution.toSolutionBody());
			}
			
			if (connection.getResponseCode() == HttpStatus.SC_UNAUTHORIZED) {
				throw new ApiTokenInvalidException();
			}
			
			return mapper.readValue(connection.getInputStream(), resultCls);
		} catch (final IOException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	
}
