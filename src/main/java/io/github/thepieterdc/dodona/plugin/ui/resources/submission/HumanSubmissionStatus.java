/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.ui.resources.submission;

import io.github.thepieterdc.dodona.data.SubmissionStatus;
import io.github.thepieterdc.dodona.exceptions.SubmissionStatusNotFoundException;
import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import org.jetbrains.annotations.PropertyKey;

import javax.annotation.Nonnull;
import java.util.Arrays;


/**
 * Human versions of the submission statuses.
 */
public enum HumanSubmissionStatus {
	COMPILATION_ERROR(SubmissionStatus.COMPILATION_ERROR, "submission_status.compilation_error"),
	CORRECT(SubmissionStatus.CORRECT, "submission_status.correct"),
	INTERNAL_ERROR(SubmissionStatus.INTERNAL_ERROR, "submission_status.internal_error"),
	MEMORY_LIMIT_EXCEEDED(SubmissionStatus.MEMORY_LIMIT_EXCEEDED, "submission_status.memory_limit_exceeded"),
	RUNTIME_ERROR(SubmissionStatus.RUNTIME_ERROR, "submission_status.runtime_error"),
	TIME_LIMIT_EXCEEDED(SubmissionStatus.TIME_LIMIT_EXCEEDED, "submission_status.time_limit_exceeded"),
	WRONG(SubmissionStatus.WRONG, "submission_status.wrong");
	
	private final String human;
	private final SubmissionStatus status;
	
	/**
	 * SubmissionStatusNames constructor.
	 *
	 * @param status the status
	 * @param human  the human name
	 */
	HumanSubmissionStatus(final SubmissionStatus status,
	                      @PropertyKey(resourceBundle = DodonaBundle.BUNDLE_NAME) final String human) {
		this.human = DodonaBundle.message(human);
		this.status = status;
	}
	
	/**
	 * Gets the icon for the given status.
	 *
	 * @param status the status
	 * @return the icon
	 */
	@Nonnull
	public static String forStatus(final SubmissionStatus status) {
		return Arrays.stream(HumanSubmissionStatus.values())
			.filter(name -> name.status == status)
			.map(name -> name.human)
			.findAny()
			.orElseThrow(() -> new SubmissionStatusNotFoundException(status.getName()));
	}
}