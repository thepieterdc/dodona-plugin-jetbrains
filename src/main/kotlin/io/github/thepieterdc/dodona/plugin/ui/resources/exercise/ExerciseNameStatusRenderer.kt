/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.ui.resources.exercise

import com.intellij.ui.ColoredListCellRenderer
import com.intellij.util.ui.JBUI
import io.github.thepieterdc.dodona.data.ExerciseStatus
import io.github.thepieterdc.dodona.plugin.DodonaBundle
import io.github.thepieterdc.dodona.plugin.ui.Icons
import io.github.thepieterdc.dodona.resources.activities.Exercise
import javax.swing.Icon
import javax.swing.JList

/**
 * Renders the exercise name and completion status.
 */
internal class ExerciseNameStatusRenderer : ColoredListCellRenderer<Exercise>() {
    private val icons: Map<ExerciseStatus, Icon> =
        mapOf(
            ExerciseStatus.CORRECT to Icons.EXERCISE_CORRECT,
            ExerciseStatus.HAS_BEEN_CORRECT to Icons.EXERCISE_CORRECT,
            ExerciseStatus.INCORRECT to Icons.EXERCISE_WRONG.color(JBUI.CurrentTheme.Focus.errorColor(true)),
        )

    override fun customizeCellRenderer(
        list: JList<out Exercise>,
        value: Exercise?,
        index: Int,
        selected: Boolean,
        hasFocus: Boolean,
    ) {
        if (value == null) {
            append(DodonaBundle.message("dialog.select_exercise.exercise.placeholder"))
        } else {
            append(value.name)
            icon = icons[value.status]
        }
    }
}
