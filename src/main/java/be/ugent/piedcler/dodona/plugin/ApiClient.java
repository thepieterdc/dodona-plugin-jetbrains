/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.plugin;

import be.ugent.piedcler.dodona.apiclient.Http;
import be.ugent.piedcler.dodona.apiclient.responses.*;
import be.ugent.piedcler.dodona.plugin.settings.SettingsHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ApiClient {


	private static final ApiClient INSTANCE = new ApiClient(new Http(), new HashMap<>(20));

	private final Http http;

	private final Map<String, Object> cache;


	public static ApiClient getInstance() {
		return INSTANCE;
	}

	public ApiClient(final Http http, final Map<String, Object> cache) {
		this.http = http;
		this.cache = cache;
	}

	private String getUrl(final String url) {
		return SettingsHelper.getDodonaURL() + url.replace(SettingsHelper.getDodonaURL(), "");
	}


	public Root getRoot() {
		return this.http.getRoot(this.getUrl(""), SettingsHelper.getApiKey());
	}

	public Course getCourse(final String url) {
		return this.http.getCourse(this.getUrl(url), SettingsHelper.getApiKey());
	}

	public Series getSeries(final String url) {
		return this.http.getSeries(this.getUrl(url), SettingsHelper.getApiKey());
	}

	public Exercise getExercise(final String url) {
		return this.http.getExercise(this.getUrl(url), SettingsHelper.getApiKey());
	}

	public Submission getSubmission(final String url) {
		return this.http.getSubmission(this.getUrl(url), SettingsHelper.getApiKey());
	}

	public List<Series> getSeriesList(final String url) {
		return this.http.getSeriesList(this.getUrl(url), SettingsHelper.getApiKey());
	}

	public List<Exercise> getExercisesList(final String url) {
		return this.http.getExercisesList(this.getUrl(url), SettingsHelper.getApiKey());
	}

	/**
	 * Sends an authenticated HTTP POST request.
	 *
	 * @param solution the solution to send
	 */
	public SubmissionPost postSolution(final Solution solution) {
		return http.postSolution(SettingsHelper.getDodonaURL("/submissions"), SettingsHelper.getApiKey(), solution);
	}

}
