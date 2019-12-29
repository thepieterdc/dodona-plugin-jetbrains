/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.ui.identification;

import io.github.thepieterdc.dodona.resources.Course;
import io.github.thepieterdc.dodona.resources.Exercise;
import io.github.thepieterdc.dodona.resources.Series;

import javax.annotation.Nullable;

/**
 * Listener for identification events.
 */
interface IdentificationListener {
	/**
	 * Called when a course has been selected.
	 *
	 * @param selected the selected course
	 */
	void onCourseSelected(@Nullable final Course selected);
	
	/**
	 * Called when an exercise has been selected.
	 *
	 * @param selected the selected exercise
	 */
	void onExerciseSelected(@Nullable final Exercise selected);
	
	/**
	 * Called when a series has been selected.
	 *
	 * @param selected the selected series
	 */
	void onSeriesSelected(@Nullable final Series selected);
}
