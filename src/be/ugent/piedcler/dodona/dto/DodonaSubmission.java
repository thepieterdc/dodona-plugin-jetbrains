/*
 * Copyright (c) 2018 - Singular-IT. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Julie De Meyer
 * @author Robbe Vanhaesebroeck
 *
 * https://www.limpio.online/
 */
package be.ugent.piedcler.dodona.dto;

/**
 * DTO representing a submission for Dodona.
 */
public class DodonaSubmission {
	private final String code;
	private final long course;
	private final long exercise;
	
	/**
	 * DodonaSubmission constructor.
	 *
	 * @param course   the course id
	 * @param exercise the exercise id
	 * @param code     the code to submit
	 */
	public DodonaSubmission(final long course, final long exercise, final String code) {
		this.code = code;
		this.course = course;
		this.exercise = exercise;
	}
}
