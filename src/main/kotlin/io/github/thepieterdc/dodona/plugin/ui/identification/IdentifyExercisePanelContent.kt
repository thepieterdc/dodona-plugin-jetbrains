/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.ui.identification

import com.intellij.ui.ScrollPaneFactory
import com.intellij.ui.dsl.builder.Align
import com.intellij.ui.dsl.builder.AlignX
import com.intellij.ui.dsl.builder.AlignY
import com.intellij.ui.dsl.builder.RowLayout
import com.intellij.ui.dsl.builder.panel
import com.intellij.util.ui.AsyncProcessIcon
import io.github.thepieterdc.dodona.plugin.DodonaBundle
import io.github.thepieterdc.dodona.plugin.ui.resources.course.CourseComboBox
import io.github.thepieterdc.dodona.plugin.ui.resources.exercise.ExercisesList
import io.github.thepieterdc.dodona.plugin.ui.resources.series.SeriesComboBox
import io.github.thepieterdc.dodona.plugin.ui.util.PanelUtils
import java.awt.BorderLayout
import java.awt.CardLayout
import java.awt.Dimension
import java.awt.Rectangle
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.Scrollable
import javax.swing.SwingConstants

/**
 * Panel that contains comboboxes to identify an exercise.
 */
internal class IdentifyExercisePanelContent(listener: IdentificationListener, confirmation: Runnable) :
    JPanel(BorderLayout()),
    Scrollable {
    val courseComboBox = CourseComboBox(listener::onCourseSelected)
    val exercisesList = ExercisesList(confirmation, listener::onExerciseSelected)
    val seriesComboBox = SeriesComboBox(listener::onSeriesSelected)

    private val courseSelectionPanel = JPanel(CardLayout())
    private val exerciseSelectionPanel = JPanel(CardLayout())
    private val seriesSelectionPanel = JPanel(CardLayout())

    init {
        courseSelectionPanel.add(courseComboBox, SELECT_COURSES)
        courseSelectionPanel.add(createLoadingPanel(), SELECT_LOADING)

        exerciseSelectionPanel.add(ScrollPaneFactory.createScrollPane(exercisesList, true), SELECT_EXERCISES)
        exerciseSelectionPanel.add(createLoadingPanel(), SELECT_LOADING)

        seriesSelectionPanel.add(seriesComboBox, SELECT_SERIES)
        seriesSelectionPanel.add(createLoadingPanel(), SELECT_LOADING)

        add(
            panel {
                row {
                    label(DodonaBundle.message("dialog.select_exercise.course"))
                    cell(courseSelectionPanel).align(AlignX.FILL)
                }.layout(RowLayout.PARENT_GRID)
                row {
                    label(DodonaBundle.message("dialog.select_exercise.series"))
                    cell(seriesSelectionPanel).align(AlignX.FILL)
                }.layout(RowLayout.PARENT_GRID)
                row {
                    label(DodonaBundle.message("dialog.select_exercise.exercise")).align(AlignY.TOP)
                    cell(exerciseSelectionPanel).align(Align.FILL)
                }.layout(RowLayout.PARENT_GRID).resizableRow()
            },
            BorderLayout.CENTER,
        )
    }

    // Fill the surrounding scroll pane, so that only the exercises list scrolls.
    override fun getPreferredScrollableViewportSize(): Dimension = preferredSize

    override fun getScrollableUnitIncrement(visibleRect: Rectangle, orientation: Int, direction: Int) = 1

    override fun getScrollableBlockIncrement(visibleRect: Rectangle, orientation: Int, direction: Int) =
        if (orientation == SwingConstants.VERTICAL) visibleRect.height else visibleRect.width

    override fun getScrollableTracksViewportWidth() = true

    override fun getScrollableTracksViewportHeight() = true

    /** Creates a small loading panel. */
    private fun createLoadingPanel(): JComponent {
        val loadingPanel = JPanel(BorderLayout())
        loadingPanel.add(AsyncProcessIcon("${javaClass}.loading"), BorderLayout.CENTER)
        return ScrollPaneFactory.createScrollPane(loadingPanel, true)
    }

    /** Sets the loading status of the courses. */
    fun setCoursesLoading(loading: Boolean) =
        PanelUtils.showCard(courseSelectionPanel, if (loading) SELECT_LOADING else SELECT_COURSES)

    /** Sets the loading status of the exercises. */
    fun setExercisesLoading(loading: Boolean) =
        PanelUtils.showCard(exerciseSelectionPanel, if (loading) SELECT_LOADING else SELECT_EXERCISES)

    /** Sets the loading status of the series. */
    fun setSeriesLoading(loading: Boolean) =
        PanelUtils.showCard(seriesSelectionPanel, if (loading) SELECT_LOADING else SELECT_SERIES)

    private companion object {
        const val SELECT_COURSES = "SELECT_COURSES"
        const val SELECT_EXERCISES = "SELECT_EXERCISES"
        const val SELECT_SERIES = "SELECT_SERIES"
        const val SELECT_LOADING = "SELECT_LOADING"
    }
}
