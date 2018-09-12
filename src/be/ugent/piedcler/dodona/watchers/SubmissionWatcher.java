/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.watchers;

import be.ugent.piedcler.dodona.dto.submission.Submission;

import java.util.Collection;
import java.util.HashSet;

/**
 * Watches submissions for approval/rejection.
 */
public final class SubmissionWatcher {
	private final Collection<Submission> submissions;
	
	/**
	 * SubmissionWatcher constructor. Singleton.
	 */
	private SubmissionWatcher() {
		this.submissions = new HashSet<>();
	}
}
