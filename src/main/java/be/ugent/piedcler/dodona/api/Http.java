/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.api;

import be.ugent.piedcler.dodona.exceptions.warnings.MissingApiKeyException;
import be.ugent.piedcler.dodona.settings.SettingsHelper;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.stream.Collectors;

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
	
	private static final ObjectMapper mapper = new ObjectMapper();
	
	/**
	 * Sends an authenticated HTTP GET request.
	 *
	 * @param endpoint  the endpoint to call
	 * @param resultCls the result class
	 */
	public static <T> T get(final String endpoint, final Class<T> resultCls) {
		final String apiKey = SettingsHelper.getApiKey();
		if (apiKey.isEmpty()) {
			throw new MissingApiKeyException();
		}
		
		try {
			final URL url = new URL(endpoint);
			
			final HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			connection.setRequestProperty(Http.HEADER_ACCEPT_KEY, Http.HEADER_ACCEPT_VALUE);
			connection.setRequestProperty(Http.HEADER_AUTHORIZATION, apiKey);
			
			return Http.mapper.readValue(connection.getInputStream(), resultCls);
		} catch (final IOException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	/**
	 * Sends an authenticated HTTP POST request.
	 *
	 * @param endpoint  the endpoint to call
	 * @param body      the request body to send
	 * @param resultCls the result class
	 */
	public static <T> T post(final String endpoint, final Map<String, ?> body, final Class<T> resultCls) {
		final String apiKey = SettingsHelper.getApiKey();
		if (apiKey.isEmpty()) {
			throw new MissingApiKeyException();
		}
		
		try {
			final URL url = new URL(endpoint);
			
			final HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty(Http.HEADER_ACCEPT_KEY, Http.HEADER_ACCEPT_VALUE);
			connection.setRequestProperty(Http.HEADER_AUTHORIZATION, apiKey);
			connection.setRequestProperty("Content-Type", "multipart/form-data");
			
			try (final OutputStream out = connection.getOutputStream()) {
				out.write(Http.toQueryString(body).getBytes(Charset.defaultCharset()));
			}
			
			return Http.mapper.readValue(connection.getInputStream(), resultCls);
		} catch (final IOException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	/**
	 * Converts a Map to a query string.
	 *
	 * @param items the items to include
	 * @return the query string
	 */
	private static String toQueryString(final Map<String, ?> items) {
		return items
				.entrySet()
				.stream()
				.map(e -> String.format("%s=%s", e.getKey(), e.getValue()))
				.collect(Collectors.joining("&"));
	}
}
