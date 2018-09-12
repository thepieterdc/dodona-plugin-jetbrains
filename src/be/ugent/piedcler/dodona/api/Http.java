/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.api;

import be.ugent.piedcler.dodona.exceptions.warnings.MissingApiKeyException;
import be.ugent.piedcler.dodona.reporting.NotificationReporter;
import be.ugent.piedcler.dodona.settings.SettingsHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NonNls;

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
enum Http {
	;
	
	@NonNls
	private static final String BASE_URL = "https://dodona.ugent.be";
	
	private static final ObjectMapper mapper = new ObjectMapper();
	
	/**
	 * Sends an authenticated HTTP POST request.
	 *
	 * @param endpoint  the endpoint to call
	 * @param body      the request body to send
	 * @param resultCls the result class
	 */
	//TODO check if an api token exists.
	static <T> T post(final String endpoint, final Map<String, ?> body, Class<T> resultCls) {
		final String apiKey = SettingsHelper.getApiKey();
		if (apiKey.isEmpty()) {
			throw new MissingApiKeyException();
		}
		
		try {
			final URL url = new URL(Http.BASE_URL + endpoint);
			
			final HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Authorization", apiKey);
			connection.setRequestProperty("Content-Type", "multipart/form-data");
			
			try (final OutputStream out = connection.getOutputStream()) {
				out.write(Http.toQueryString(body).getBytes(Charset.defaultCharset()));
			}
			
			return Http.mapper.readValue(connection.getInputStream(), resultCls);
		} catch (final IOException ex) {
			//TODO improve this, lots
			NotificationReporter.error("Something went wrong submitting your code. Please ensure your api token is valid.");
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
