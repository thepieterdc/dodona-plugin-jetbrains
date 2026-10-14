/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.ui.resources.course

import io.github.thepieterdc.dodona.plugin.ui.listeners.ItemSelectedListener
import io.github.thepieterdc.dodona.plugin.ui.resources.AbstractResourceComboBox
import io.github.thepieterdc.dodona.resources.Course

/**
 * A combo box containing courses.
 */
class CourseComboBox(listener: ItemSelectedListener<in Course>) :
    AbstractResourceComboBox<Course>(CourseNameRenderer(), listener) {
    override fun setResources(resources: Collection<Course>) {
        super.setResources(resources.sortedDescending())
    }
}
