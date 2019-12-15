/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.code;

import io.github.thepieterdc.dodona.resources.ProgrammingLanguage;
import io.github.thepieterdc.dodona.resources.submissions.Submission;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

/**
 * DTO that combines a submission with a programming language. This might
 * eventually be added to the API when language-agnostic exercises are added.
 */
public class SolutionWithLanguage {
	@Nullable
	private final ProgrammingLanguage language;
	
	private final Submission submission;
	
	/**
	 * SolutionWithLanguage constructor.
	 *
	 * @param submission the submission
	 * @param language   the programming language
	 */
	public SolutionWithLanguage(final Submission submission,
	                             @Nullable final ProgrammingLanguage language) {
		this.language = language;
		this.submission = submission;
	}
	
	/**
	 * Gets the code of the submission.
	 *
	 * @return the code
	 */
	@Nonnull
	public String getCode() {
		return this.submission.getCode();
	}
	
	/**
	 * Gets the programming language of this exercise. This is optional since
	 * not every exercise contains this property.
	 *
	 * @return the programming language
	 */
	@Nonnull
	public Optional<ProgrammingLanguage> getProgrammingLanguage() {
		return Optional.ofNullable(this.language);
	}
}
