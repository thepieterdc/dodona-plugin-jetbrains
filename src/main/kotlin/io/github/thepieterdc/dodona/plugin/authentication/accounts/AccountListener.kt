/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.authentication.accounts

import com.intellij.util.messages.Topic

/**
 * Listens for changes to the account.
 */
fun interface AccountListener {
    /**
     * Called when the default account has been changed.
     */
    fun updated()

    companion object {
        @JvmField
        val UPDATED_TOPIC: Topic<AccountListener> =
            Topic.create("The default account has been changed", AccountListener::class.java)
    }
}
