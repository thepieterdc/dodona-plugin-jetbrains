/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.settings

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.components.StoragePathMacros
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import io.github.thepieterdc.dodona.plugin.settings.listeners.ProjectCourseListener
import java.util.Optional

/**
 * Project-specific plugin settings.
 */
@State(name = DodonaProjectSettings.STATE_NAME, storages = [Storage(StoragePathMacros.WORKSPACE_FILE)])
class DodonaProjectSettings(project: Project) : PersistentStateComponent<DodonaProjectSettings.State> {
    private var state = State()

    private val bus = project.messageBus

    /** Gets the course id if set. */
    fun getCourseId(): Optional<Long> = Optional.of(state.course_id).filter { it > 0L }

    /** Gets the series id if set. */
    fun getSeriesId(): Optional<Long> = Optional.of(state.series_id).filter { it > 0L }

    override fun getState(): State = state

    override fun loadState(state: State) {
        this.state = state
    }

    /** Sets the course id. */
    fun setCourseId(id: Long) {
        state.course_id = id
        bus.syncPublisher(ProjectCourseListener.CHANGED_TOPIC).onCourseChanged(id)
    }

    /** Sets the series id. */
    fun setSeriesId(id: Long) {
        state.series_id = id
    }

    /** Setting values. */
    class State {
        // The field names are part of the persisted XML format.
        @JvmField
        var course_id = 0L

        @JvmField
        var series_id = 0L
    }

    companion object {
        internal const val STATE_NAME = "DodonaProjectSettings"

        /** Gets an instance of the project settings. */
        @JvmStatic
        fun getInstance(project: Project): DodonaProjectSettings = project.service()
    }
}
