/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.ui.resources.exercise

import com.intellij.ui.CollectionListModel
import com.intellij.ui.components.JBList
import io.github.thepieterdc.dodona.data.ExerciseStatus
import io.github.thepieterdc.dodona.plugin.DodonaBundle
import io.github.thepieterdc.dodona.plugin.ui.listeners.DoubleClickListener
import io.github.thepieterdc.dodona.plugin.ui.resources.ResourceSelector
import io.github.thepieterdc.dodona.resources.activities.Exercise
import java.util.Optional
import java.util.function.Predicate
import javax.swing.ListSelectionModel

/**
 * A list containing exercises.
 *
 * @param confirmed called upon double click events
 * @param selected called upon selection events
 * @param multiple whether multiple exercises can be selected
 */
class ExercisesList(confirmed: () -> Unit, selected: (List<Exercise>) -> Unit, multiple: Boolean) :
    JBList<Exercise>(),
    ResourceSelector<Exercise> {
    private val listModel = CollectionListModel<Exercise>()

    init {
        // Detect double-clicks.
        addMouseListener(DoubleClickListener { confirmed() })
        addListSelectionListener { e ->
            if (!e.valueIsAdjusting) {
                selected(selectedValuesList)
            }
        }
        selectionMode =
            if (multiple) ListSelectionModel.MULTIPLE_INTERVAL_SELECTION else ListSelectionModel.SINGLE_SELECTION
        setEmptyText(DodonaBundle.message("dialog.select_exercise.exercise.empty"))
        model = listModel
        cellRenderer = ExerciseNameStatusRenderer()
    }

    /** Whether there are exercises and all of them are selected. */
    val isAllSelected: Boolean
        get() = listModel.size > 0 && selectedIndices.size == listModel.size

    override fun getSelectedResource(): Optional<Exercise> = Optional.ofNullable(selectedValue)

    override fun setResources(resources: Collection<out Exercise>) {
        // Change the empty text based on the current state.
        setEmptyText(
            DodonaBundle.message(
                if (isEnabled) "dialog.select_exercise.exercise.empty" else "dialog.select_exercise.exercise.select_series",
            ),
        )

        // Set the new items.
        listModel.replaceAll(resources.toList())

        // Find the first incorrect exercise and select it.
        listModel.items.firstOrNull(::isNotCorrect)?.let { setSelectedResource(it) }
    }

    override fun setSelectedResource(id: Long) = setSelectedResource { it.id == id }

    override fun setSelectedResource(predicate: Predicate<in Exercise>) =
        setSelectedResource(listModel.items.firstOrNull { predicate.test(it) })

    override fun setSelectedResource(resource: Exercise?) = setSelectedValue(resource, true)

    /** Selects all exercises. */
    fun selectAll() {
        if (listModel.size > 0) {
            setSelectionInterval(0, listModel.size - 1)
        }
    }

    private companion object {
        fun isNotCorrect(exercise: Exercise) =
            exercise.status == ExerciseStatus.INCORRECT || exercise.status == ExerciseStatus.NOT_ATTEMPTED
    }
}
