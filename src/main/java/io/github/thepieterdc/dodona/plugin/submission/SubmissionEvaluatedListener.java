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
 * Called when a new submission has been evaluated.
 */
@FunctionalInterface
public interface SubmissionEvaluatedListener {
	Topic<SubmissionEvaluatedListener> SUBMISSION_EVALUATED = Topic.create(
		"Submission evaluated",
		SubmissionEvaluatedListener.class
	);
	
	/**
	 * A new submission has been evaluated.
	 *
	 * @param submission the evaluated submission
	 */
	void onSubmissionEvaluated(Submission submission);
}
