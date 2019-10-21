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
import org.junit.Assert;
import org.junit.Test;

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
		Assert.assertNotNull(courseSet);
		Assert.assertTrue(courseSet.getCourse().isPresent());
		courseSet.getCourse().ifPresent(id ->
			Assert.assertEquals(courseId, (long) id)
		);
		
		final Identification courseNotSet =
			new Identification(null, random.generate(), random.generate());
		Assert.assertNotNull(courseNotSet);
		Assert.assertFalse(courseNotSet.getCourse().isPresent());
	}
	
	/**
	 * Tests Identification#getExercise().
	 */
	@Test
	public void testGetExercise() {
		final long exerciseId = random.generate();
		
		final Identification identification =
			new Identification(random.generate(), random.generate(), exerciseId);
		Assert.assertNotNull(identification);
		Assert.assertEquals(exerciseId, (long) identification.getExercise());
	}
	
	/**
	 * Tests Identification#getSeries().
	 */
	@Test
	public void testGetSeries() {
		final long seriesId = random.generate();
		
		final Identification seriesSet =
			new Identification(random.generate(), seriesId, random.generate());
		Assert.assertNotNull(seriesSet);
		Assert.assertTrue(seriesSet.getSeries().isPresent());
		seriesSet.getSeries().ifPresent(id ->
			Assert.assertEquals(seriesId, (long) id)
		);
		
		final Identification seriesNotSet =
			new Identification(random.generate(), null, random.generate());
		Assert.assertNotNull(seriesNotSet);
		Assert.assertFalse(seriesNotSet.getSeries().isPresent());
	}
}