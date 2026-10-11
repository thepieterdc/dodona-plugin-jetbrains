/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.settings

import com.intellij.openapi.options.Configurable
import com.intellij.openapi.options.ConfigurableBase
import io.github.thepieterdc.dodona.plugin.DodonaBundle
import io.github.thepieterdc.dodona.plugin.authentication.accounts.DodonaAccountManager
import io.github.thepieterdc.dodona.plugin.settings.ui.DodonaSettingsPanel

/**
 * Controller that handles changing plugin settings.
 */
class DodonaSettingsConfigurable :
    ConfigurableBase<DodonaSettingsPanel, DodonaSettingsHolder>(SETTINGS_ID, DodonaBundle.NAME, SETTINGS_ID),
    Configurable.NoMargin {
    private val accountManager = DodonaAccountManager.getInstance()

    override fun createUi() = DodonaSettingsPanel()

    override fun getSettings() = DodonaSettingsHolder(accountManager)

    private companion object {
        const val SETTINGS_ID = "settings.dodona"
    }
}
