/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.ui.resources.series

import com.intellij.ui.ColoredListCellRenderer
import io.github.thepieterdc.dodona.plugin.DodonaBundle
import io.github.thepieterdc.dodona.resources.Series
import javax.swing.JList

/**
 * Renders the series name.
 */
internal class SeriesNameRenderer : ColoredListCellRenderer<Series>() {
    override fun customizeCellRenderer(
        list: JList<out Series>,
        value: Series?,
        index: Int,
        selected: Boolean,
        hasFocus: Boolean,
    ) {
        append(value?.name ?: DodonaBundle.message("dialog.select_exercise.series.placeholder"))
    }
}
