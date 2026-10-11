/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.ui.resources.series

import io.github.thepieterdc.dodona.plugin.ui.listeners.ItemSelectedListener
import io.github.thepieterdc.dodona.plugin.ui.resources.AbstractResourceComboBox
import io.github.thepieterdc.dodona.resources.Series

/**
 * A combo box containing series.
 */
class SeriesComboBox(listener: ItemSelectedListener<in Series>) :
    AbstractResourceComboBox<Series>(SeriesNameRenderer(), listener)
