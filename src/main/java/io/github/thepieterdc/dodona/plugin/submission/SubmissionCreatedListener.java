/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.submission;

import com.intellij.util.messages.Topic;
import io.github.thepieterdc.dodona.resources.submissions.Submission;

/**
 * Called when a new submission has been created.
 */
@FunctionalInterface
public interface SubmissionCreatedListener {
	Topic<SubmissionCreatedListener> SUBMISSION_CREATED = Topic.create(
		"Submission created",
		SubmissionCreatedListener.class
	);
	
	/**
	 * A new submission has been created.
	 *
	 * @param submission the created submission
	 */
	void onSubmissionCreated(Submission submission);
}
