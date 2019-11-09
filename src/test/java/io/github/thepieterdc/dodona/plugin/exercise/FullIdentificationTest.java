/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */
package io.github.thepieterdc.dodona.plugin.exercise;

import io.github.thepieterdc.dodona.plugin.testutils.Mocking;
import io.github.thepieterdc.dodona.resources.Course;
import io.github.thepieterdc.dodona.resources.Exercise;
import io.github.thepieterdc.dodona.resources.Series;
import io.github.thepieterdc.random.numerical.RandomLongGenerator;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests FullIdentification.
 */
public class FullIdentificationTest {
	private static final RandomLongGenerator ids = RandomLongGenerator.positive();
	
	/**
	 * Tests FullIdentification#getCourse().
	 */
	@Test
	public void testGetCourse() {
		final long id = ids.generate();
		final Course course = Mocking.resource(Course.class, id);
		final Exercise exercise = Mocking.resource(Exercise.class, ids.generate());
		final Series series = Mocking.resource(Series.class, ids.generate());
		
		final FullIdentification identification = new FullIdentification(
			course,
			series,
			exercise
		);
		Assert.assertNotNull(identification);
		
		Assert.assertTrue(identification.getCourse().isPresent());
		Assert.assertEquals(id, identification.getCourse().get().getId());
	}
	
	/**
	 * Tests FullIdentification#getExercise().
	 */
	@Test
	public void testGetExercise() {
		final long id = ids.generate();
		final Course course = Mocking.resource(Course.class, ids.generate());
		final Exercise exercise = Mocking.resource(Exercise.class, id);
		final Series series = Mocking.resource(Series.class, ids.generate());
		
		final FullIdentification identification = new FullIdentification(
			course,
			series,
			exercise
		);
		Assert.assertNotNull(identification);
		
		Assert.assertNotNull(identification.getExercise());
		Assert.assertEquals(id, identification.getExercise().getId());
	}
	
	/**
	 * Tests FullIdentification#getSeries().
	 */
	@Test
	public void testGetSeries() {
		final long id = ids.generate();
		final Course course = Mocking.resource(Course.class, ids.generate());
		final Exercise exercise = Mocking.resource(Exercise.class, ids.generate());
		final Series series = Mocking.resource(Series.class, id);
		
		final FullIdentification identification = new FullIdentification(
			course,
			series,
			exercise
		);
		Assert.assertNotNull(identification);
		
		Assert.assertTrue(identification.getSeries().isPresent());
		Assert.assertEquals(id, identification.getSeries().get().getId());
	}
}