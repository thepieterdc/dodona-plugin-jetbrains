/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.services.impl;

import be.ugent.piedcler.dodona.api.Http;
import be.ugent.piedcler.dodona.api.responses.SubmissionResponse;
import be.ugent.piedcler.dodona.dto.Series;
import be.ugent.piedcler.dodona.dto.Submission;
import be.ugent.piedcler.dodona.dto.submission.SubmissionStatus;
import be.ugent.piedcler.dodona.services.SubmissionService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Implementation class for SubmissionService.
 *
 * In contrast to other Services, the cache is only used when the submission is
 * either accepted or rejected, since the result can be rapidly changing.
 */
public class SubmissionServiceImpl implements SubmissionService {
	private final Map<Long, Submission> cache;
	
	/**
	 * SubmissionServiceImpl constructor.
	 */
	public SubmissionServiceImpl() {
		this.cache = new HashMap<>(5);
	}
	
	@Override
	public Submission get(final long id) {
		return Optional.ofNullable(this.cache.get(id))
				.filter(submission -> submission.getStatus() != SubmissionStatus.PENDING)
				.orElseGet(() -> this.cache.put(id, SubmissionServiceImpl.getFromApi(id)));
	}
	
	/**
	 * Gets a series from the api.
	 *
	 * @param id the id of the series to fetch
	 * @return the series
	 */
	private static Submission getFromApi(final long id) {
		final String url = String.format(Series.ENDPOINT_ID, id);
		return Http.get(url, SubmissionResponse.class).toSubmission();
	}
}
