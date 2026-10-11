/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.settings.ui

import com.intellij.openapi.Disposable
import com.intellij.openapi.options.ConfigurableUi
import com.intellij.openapi.util.Disposer
import io.github.thepieterdc.dodona.plugin.authentication.ui.DodonaAccountPanel
import io.github.thepieterdc.dodona.plugin.settings.DodonaSettingsHolder
import javax.swing.JComponent

/**
 * Main panel for plugin settings.
 */
class DodonaSettingsPanel :
    ConfigurableUi<DodonaSettingsHolder>,
    Disposable {
    private val accountPanel = DodonaAccountPanel()

    override fun apply(holder: DodonaSettingsHolder) {
        val pair = accountPanel.account
        val account = pair.first
        if (account == null) {
            // Clear the previous account.
            holder.accounts.clearAccount()
        } else if (!holder.accounts.hasAccount(account)) {
            // Set the new account.
            holder.accounts.setAccount(account, pair.second)
        }

        // Clear the new authentication data and the modification status.
        accountPanel.clearModified()
        accountPanel.clearNewData()
    }

    override fun dispose() = Disposer.dispose(accountPanel)

    override fun getComponent(): JComponent = accountPanel

    override fun isModified(settings: DodonaSettingsHolder) = accountPanel.isModified

    override fun reset(holder: DodonaSettingsHolder) {
        accountPanel.setAccount(holder.accounts.getAccount().orElse(null))

        // Clear the new authentication data and the modification status.
        accountPanel.clearModified()
        accountPanel.clearNewData()
    }
}
