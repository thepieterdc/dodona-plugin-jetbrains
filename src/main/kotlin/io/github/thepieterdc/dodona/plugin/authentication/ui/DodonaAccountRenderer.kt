/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.authentication.ui

import com.intellij.util.ui.GridBag
import com.intellij.util.ui.JBUI
import io.github.thepieterdc.dodona.plugin.api.DodonaServer
import io.github.thepieterdc.dodona.plugin.authentication.accounts.DodonaAccount
import io.github.thepieterdc.dodona.plugin.ui.renderers.list.IconListCellRenderer
import java.awt.Color
import java.awt.Font
import javax.swing.JLabel
import javax.swing.JPanel

/**
 * Renders a Dodona account in a list.
 */
internal class DodonaAccountRenderer : IconListCellRenderer<DodonaAccount>(JBUI.Borders.empty(5, 8)) {
    // The base constructor calls initialize() before adding these labels to the layout.
    private lateinit var email: JLabel
    private lateinit var fullName: JLabel
    private lateinit var server: JLabel

    override fun addDetails(container: JPanel, insets: GridBag) {
        container.add(fullName, insets.nextLine().next())
        container.add(email, insets.next())
        container.add(server, insets.nextLine().coverLine())
    }

    override fun initialize() {
        email = JLabel()
        fullName = JLabel()
        server = JLabel()
    }

    override fun renderValue(account: DodonaAccount, primary: Color, secondary: Color) {
        icon.icon = DodonaServer.toIcon(account.server)

        email.foreground = secondary
        email.text = account.email

        fullName.foreground = primary
        fullName.text = account.fullName
        fullName.font = fullName.font.deriveFont(Font.BOLD)

        server.foreground = secondary
        server.text = DodonaServer.toDisplayName(account.server)
    }
}
