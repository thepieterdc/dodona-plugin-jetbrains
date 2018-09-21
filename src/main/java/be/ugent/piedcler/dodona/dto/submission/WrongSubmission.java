/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.dto.submission;

import be.ugent.piedcler.dodona.dto.Exercise;
import be.ugent.piedcler.dodona.dto.Submission;
import org.jetbrains.annotations.NotNull;

/**
 * A submission for Dodona which is incorrect.
 */
public class WrongSubmission implements Submission {
	private final Exercise exercise;
	private final long id;
	
	/**
	 * WrongSubmission constructor.
	 *
	 * @param id       the id of the submission
	 * @param exercise the exercise
	 */
	public WrongSubmission(final long id, final Exercise exercise) {
		this.exercise = exercise;
		this.id = id;
	}
	
	@Override
	public Exercise getExercise() {
		return this.exercise;
	}
	
	@Override
	public long getId() {
		return this.id;
	}
	
	@Override
	@NotNull
	public SubmissionStatus getStatus() {
		return SubmissionStatus.INCORRECT;
	}
}
