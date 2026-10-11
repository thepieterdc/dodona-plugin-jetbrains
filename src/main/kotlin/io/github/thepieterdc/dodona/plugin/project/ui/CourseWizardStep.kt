/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.project.ui

import com.intellij.ide.util.projectWizard.ModuleWizardStep
import com.intellij.openapi.options.ConfigurationException
import com.intellij.ui.dsl.builder.Align
import com.intellij.ui.dsl.builder.panel
import io.github.thepieterdc.dodona.plugin.DodonaBundle
import io.github.thepieterdc.dodona.plugin.api.executor.DodonaExecutorHolder
import io.github.thepieterdc.dodona.plugin.project.DodonaModuleBuilder
import javax.swing.JComponent

/**
 * Wizard step: Select course.
 */
class CourseWizardStep(
    private val builder: DodonaModuleBuilder,
    executor: DodonaExecutorHolder,
) : ModuleWizardStep() {
    private val coursesPanel = CourseListPanel(executor)

    private val rootPanel =
        panel {
            group(DodonaBundle.message("module.course"), indent = false) {
                row {
                    cell(coursesPanel).align(Align.FILL)
                }.resizableRow()
            }
        }

    init {
        coursesPanel.requestUpdate()
    }

    override fun getComponent(): JComponent = rootPanel

    override fun updateDataModel() {
        coursesPanel.selectedCourse.ifPresent(builder::setCourse)
    }

    override fun validate(): Boolean {
        if (!coursesPanel.selectedCourse.isPresent) {
            throw ConfigurationException(DodonaBundle.message("module.course.blank"))
        }
        return super.validate()
    }
}
