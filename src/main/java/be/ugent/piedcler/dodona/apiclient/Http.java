/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.apiclient;

import be.ugent.piedcler.dodona.apiclient.exceptions.apitoken.ApiTokenInvalidException;
import be.ugent.piedcler.dodona.apiclient.exceptions.apitoken.ApiTokenNotSetException;
import be.ugent.piedcler.dodona.apiclient.responses.Solution;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.http.HttpStatus;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;
import java.util.Optional;

/**
 * Provides HTTP methods.
 * <p>
 * TODO: rework this using a HTTP library, issue #5.
 * TODO: better error handling, issue #7.
 */
public class Http {

	private static final String HEADER_ACCEPT_KEY = "Accept";
	private static final String HEADER_ACCEPT_VALUE = "application/json";
	private static final String HEADER_AUTHORIZATION = "Authorization";
	private static final String HEADER_CONTENT_TYPE = "Content-Type";

	private static final ObjectMapper mapper = new ObjectMapper()
		.setSerializationInclusion(JsonInclude.Include.NON_NULL)
		.enable(SerializationFeature.WRAP_ROOT_VALUE)
		;//.enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE);

	/**
	 * Sends an authenticated HTTP GET request.
	 *
	 * @param endpoint  the endpoint to call
	 * @param apiKey    the api key
	 * @param resultCls the result class
	 * @return the response wrapped in the given result class
	 */
	public <T> Optional<T> get(final String endpoint,
							   final String apiKey,
							   final Class<T> resultCls) {
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
				return Optional.empty();
			}

			return Optional.of(Http.mapper.readValue(connection.getInputStream(), resultCls));
		} catch (final IOException ex) {
			return Optional.empty();
		}
	}

	/**
	 * Sends an authenticated HTTP POST request.
	 *
	 * @param endpoint  the endpoint to call
	 * @param apiKey    the api key to use
	 * @param solution  the solution to send
	 * @param resultCls the result class
	 */
	public <T> Optional<T> post(final String endpoint, final String apiKey, final Solution solution, final Class<T> resultCls) {
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
				mapper.writeValue(out, solution);
			}

			if (connection.getResponseCode() == HttpStatus.SC_UNAUTHORIZED) {
				throw new ApiTokenInvalidException();
			}

			return Optional.of(mapper.readValue(connection.getInputStream(), resultCls));
		} catch (final IOException ex) {
			return Optional.empty();
		}
	}


	public JavaType getListTypeOf(Class<?> clz) {
		return mapper.getTypeFactory().constructCollectionType(List.class, clz);
	}


}
