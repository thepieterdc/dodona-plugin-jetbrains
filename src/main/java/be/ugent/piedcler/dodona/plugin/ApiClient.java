package be.ugent.piedcler.dodona.plugin;

import be.ugent.piedcler.dodona.apiclient.Http;
import be.ugent.piedcler.dodona.apiclient.responses.Solution;
import be.ugent.piedcler.dodona.apiclient.responses.Submission;
import be.ugent.piedcler.dodona.plugin.settings.SettingsHelper;
import com.fasterxml.jackson.databind.JavaType;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class ApiClient {


	private static final ApiClient INSTANCE = new ApiClient(new Http(), new HashMap<>(20));

	public static final String SUBMISSION_ENDPOINT = "/submissions";

	public static final String SUBMISSION_PROTOTYPE_URL = SUBMISSION_ENDPOINT + "/%d";

	private final Http http;

	private final Map<String, Object> cache;


	public static ApiClient getInstance() {
		return INSTANCE;
	}

	public ApiClient(final Http http, final Map<String, Object> cache) {
		this.http = http;
		this.cache = cache;
	}

	/**
	 * Sends an authenticated HTTP GET request.
	 *
	 * @param endpoint  the endpoint to call
	 * @param resultCls the result class
	 * @return the response wrapped in the given result class
	 */
	public <T> Optional<T> get(final String endpoint, final Class<T> resultCls) {
		// should cache here

		// TODO extract the base url correctly.
		final String url = SettingsHelper.getDodonaURL() + endpoint.replace(SettingsHelper.getDodonaURL(), "");
		return http.get(url, SettingsHelper.getApiKey(), resultCls);
	}

	/**
	 * Sends an authenticated HTTP POST request.
	 *
	 * @param solution the solution to send
	 */
	public Optional<Submission> post(final Solution solution) {
		return http.post(SettingsHelper.getDodonaURL("/submissions"), SettingsHelper.getApiKey(), solution, Submission.class);
	}

}
