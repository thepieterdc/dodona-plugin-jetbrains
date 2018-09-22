/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.services.impl;

import be.ugent.piedcler.dodona.api.Http;
import be.ugent.piedcler.dodona.api.responses.SubmissionPostResponse;
import be.ugent.piedcler.dodona.api.responses.SubmissionResponse;
import be.ugent.piedcler.dodona.dto.Solution;
import be.ugent.piedcler.dodona.dto.Submission;
import be.ugent.piedcler.dodona.dto.submission.PendingSubmission;
import be.ugent.piedcler.dodona.dto.submission.SubmissionStatus;
import be.ugent.piedcler.dodona.exceptions.errors.SubmissionException;
import be.ugent.piedcler.dodona.services.SubmissionService;

import java.util.HashMap;
import java.util.Map;

import static be.ugent.piedcler.dodona.api.Http.post;
import static be.ugent.piedcler.dodona.dto.Submission.getUrl;
import static be.ugent.piedcler.dodona.settings.SettingsHelper.getDodonaURL;
import static java.util.Optional.ofNullable;

/**
 * Implementation class for SubmissionService.
 * <p>
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
		return ofNullable(this.cache.get(id))
			.filter(submission -> submission.getStatus() != SubmissionStatus.PENDING)
			.orElseGet(() -> {
				final Submission ret = SubmissionServiceImpl.getFromApi(id);
				this.cache.put(id, ret);
				return ret;
			});
	}

	/**
	 * Gets a series from the api.
	 *
	 * @param id the id of the series to fetch
	 * @return the series
	 */
	private static Submission getFromApi(final long id) {
		final String url = getUrl(id);
		return Http.get(url, SubmissionResponse.class).toSubmission();
	}

	@Override
	public Submission submit(final Solution solution) {

		final String url = getDodonaURL(Submission.ENDPOINT);

		final SubmissionPostResponse response = post(url, solution, SubmissionPostResponse.class);

		if (response.getStatus().equals(SubmissionPostResponse.STATUS_OK)) {
			final Submission submission = new PendingSubmission(response.getId(), solution.getExercise());
			this.cache.put(response.getId(), submission);
			return submission;
		} else {
			throw new SubmissionException();
		}
	}
}
