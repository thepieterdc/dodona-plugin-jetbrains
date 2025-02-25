/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */
package io.github.thepieterdc.dodona.plugin.exercise;

import io.github.thepieterdc.random.numerical.RandomLongGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests Identification.
 */
public class IdentificationTest {
	private static final RandomLongGenerator random = RandomLongGenerator.positive();
	
	/**
	 * Tests Identification#getCourse().
	 */
	@Test
	public void testGetCourse() {
		final long courseId = random.generate();
		
		final Identification courseSet =
			new Identification(courseId, random.generate(), random.generate());
		assertNotNull(courseSet);
		assertTrue(courseSet.getCourseId().isPresent());
		courseSet.getCourseId().ifPresent(id ->
			assertEquals(courseId, (long) id)
		);
		
		final Identification courseNotSet =
			new Identification(null, random.generate(), random.generate());
		assertNotNull(courseNotSet);
		assertFalse(courseNotSet.getCourseId().isPresent());
	}
	
	/**
	 * Tests Identification#getExercise().
	 */
	@Test
	public void testGetExercise() {
		final long exerciseId = random.generate();
		
		final Identification identification =
			new Identification(random.generate(), random.generate(), exerciseId);
		assertNotNull(identification);
		assertEquals(exerciseId, (long) identification.getExerciseId());
	}
	
	/**
	 * Tests Identification#getSeries().
	 */
	@Test
	public void testGetSeries() {
		final long seriesId = random.generate();
		
		final Identification seriesSet =
			new Identification(random.generate(), seriesId, random.generate());
		assertNotNull(seriesSet);
		assertTrue(seriesSet.getSeriesId().isPresent());
		seriesSet.getSeriesId().ifPresent(id ->
			assertEquals(seriesId, (long) id)
		);
		
		final Identification seriesNotSet =
			new Identification(random.generate(), null, random.generate());
		assertNotNull(seriesNotSet);
		assertFalse(seriesNotSet.getSeriesId().isPresent());
	}
}