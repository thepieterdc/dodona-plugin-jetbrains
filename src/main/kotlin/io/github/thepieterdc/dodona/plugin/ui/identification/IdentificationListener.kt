/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.ui.identification

import io.github.thepieterdc.dodona.resources.Course
import io.github.thepieterdc.dodona.resources.Series
import io.github.thepieterdc.dodona.resources.activities.Exercise

/** Listener for identification events. */
internal interface IdentificationListener {
    /** Called when a course has been selected. */
    fun onCourseSelected(selected: Course?)

    /** Called when the selected exercises have changed. Empty if none are selected. */
    fun onExercisesSelected(selected: List<Exercise>)

    /** Called when a series has been selected. */
    fun onSeriesSelected(selected: Series?)
}
