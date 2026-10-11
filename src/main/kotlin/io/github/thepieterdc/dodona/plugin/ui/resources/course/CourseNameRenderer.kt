/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.ui.resources.course

import com.intellij.ui.ColoredListCellRenderer
import io.github.thepieterdc.dodona.resources.Course
import javax.swing.JList

/**
 * Renders the course name.
 */
internal class CourseNameRenderer : ColoredListCellRenderer<Course>() {
    override fun customizeCellRenderer(
        list: JList<out Course>,
        value: Course?,
        index: Int,
        selected: Boolean,
        hasFocus: Boolean,
    ) {
        if (value != null) {
            append("[${value.year}] ${value.name}")
        }
    }
}
