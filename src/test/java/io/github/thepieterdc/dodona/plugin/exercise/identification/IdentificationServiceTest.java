/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */
package io.github.thepieterdc.dodona.plugin.exercise.identification;

import io.github.thepieterdc.dodona.plugin.exercise.Identification;
import io.github.thepieterdc.dodona.plugin.exercise.identification.impl.IdentificationServiceImpl;
import io.github.thepieterdc.random.RandomGenerator;
import io.github.thepieterdc.random.numerical.RandomLongGenerator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

/**
 * Tests IdentificationService.
 */
@SuppressWarnings({"HardcodedLineSeparator", "HardCodedStringLiteral"})
public class IdentificationServiceTest {
	private static final RandomGenerator<Long> random =
		RandomLongGenerator.between(1L, 100000L);
	
	private IdentificationService instance;
	
	/**
	 * Set-up the test environment.
	 */
	@Before
	public void setUp() {
		this.instance = new IdentificationServiceImpl();
	}
	
	/**
	 * Tests IdentificationServiceImpl#identify() within a course.
	 */
	@Test
	public void testCourseScoped() {
		// Generate a random course, series and exercise id.
		final long courseId = IdentificationServiceTest.random.generate();
		final long exerciseId = IdentificationServiceTest.random.generate();
		final long seriesId = IdentificationServiceTest.random.generate();
		
		// Generate the url to the exercise.
		final String urlPattern = "https://dodona.ugent.be/nl/courses/%d/series/%d/exercises/%d/";
		final String url = String.format(urlPattern, courseId, seriesId, exerciseId);
		
		// Generate some code.
		final String codePattern = "// %s\npublic class Test {}";
		final String code = String.format(codePattern, url);
		
		// Identify the url.
		final Optional<Identification> result = this.instance.identify(code);
		
		// Validate the result.
		Assert.assertTrue(result.isPresent());
		result.ifPresent(identification -> {
			Assert.assertEquals(courseId, (long) identification.getCourseId().orElse(-1L));
			Assert.assertEquals(seriesId, (long) identification.getSeriesId().orElse(-1L));
			Assert.assertEquals(exerciseId, (long) identification.getExerciseId());
		});
	}
	
	/**
	 * Tests IdentificationServiceImpl#identify() with an out-of-course
	 * exercise.
	 */
	@Test
	public void testOutsideOfCourse() {
		// Generate a random exercise id.
		final long exerciseId = IdentificationServiceTest.random.generate();
		
		// Generate the url to the exercise.
		final String urlPattern = "https://dodona.ugent.be/nl/exercises/%d/";
		final String url = String.format(urlPattern, exerciseId);
		
		// Generate some code.
		final String codePattern = "// %s\npublic class Test {}";
		final String code = String.format(codePattern, url);
		
		// Identify the url.
		final Optional<Identification> result = this.instance.identify(code);
		
		// Validate the result.
		Assert.assertTrue(result.isPresent());
		result.ifPresent(identification -> {
			Assert.assertFalse(identification.getCourseId().isPresent());
			Assert.assertFalse(identification.getSeriesId().isPresent());
			Assert.assertEquals(exerciseId, (long) identification.getExerciseId());
		});
	}
	
	/**
	 * Tests IdentificationServiceImpl#identify() with an empty code string.
	 */
	@Test
	public void testEmptyCode() {
		Assert.assertFalse(this.instance.identify("").isPresent());
	}
}