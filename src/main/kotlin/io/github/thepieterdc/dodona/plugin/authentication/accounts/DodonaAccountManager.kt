/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.authentication.accounts

import com.intellij.credentialStore.CredentialAttributes
import com.intellij.credentialStore.Credentials
import com.intellij.credentialStore.generateServiceName
import com.intellij.ide.passwordSafe.PasswordSafe
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.components.service
import io.github.thepieterdc.dodona.plugin.DodonaBundle
import io.github.thepieterdc.dodona.plugin.api.DodonaServer
import io.github.thepieterdc.dodona.resources.User
import java.util.Optional

/**
 * Manages Dodona accounts.
 */
@Service(Service.Level.APP)
@State(name = DodonaAccountManager.STATE_NAME, storages = [Storage(DodonaAccountManager.STATE_STORAGE)])
class DodonaAccountManager : PersistentStateComponent<DodonaAccount> {
    private var account: DodonaAccount? = null

    private val bus = ApplicationManager.getApplication().messageBus

    /**
     * Clears the account and authentication token.
     */
    fun clearAccount() {
        val previous = account ?: return

        // Clear the token and the account.
        PasswordSafe.instance.set(createCredentialAttributes(previous), null)
        account = null

        // Broadcast the removal of the account.
        bus.syncPublisher(AccountListener.UPDATED_TOPIC).updated()
    }

    /**
     * Gets the account.
     *
     * @return the account
     */
    fun getAccount(): Optional<DodonaAccount> = Optional.ofNullable(account)

    override fun getState(): DodonaAccount? = account

    /**
     * Gets the token for the current account, if available.
     */
    fun getToken(): Optional<String> = Optional.ofNullable(
        account?.let { PasswordSafe.instance.getPassword(createCredentialAttributes(it)) },
    )

    /**
     * Validates whether the given account is already in the account list.
     *
     * @param acc the account to check
     * @return true if the account exists
     */
    fun hasAccount(acc: DodonaAccount): Boolean = acc == account

    override fun loadState(state: DodonaAccount) {
        account = state
    }

    /**
     * Sets the account.
     *
     * @param account the account to set
     * @param token the corresponding authentication token
     */
    fun setAccount(account: DodonaAccount, token: String?) {
        // Remove the previous account if set.
        clearAccount()

        // Set the new account and its token.
        this.account = account
        PasswordSafe.instance.set(createCredentialAttributes(account), Credentials(account.id, token))

        // Broadcast the new account.
        bus.syncPublisher(AccountListener.UPDATED_TOPIC).updated()
    }

    /**
     * Creates credential attributes for the given account, allowing storage in
     * the Password Safe.
     */
    private fun createCredentialAttributes(acc: DodonaAccount) =
        CredentialAttributes(generateServiceName(DodonaBundle.NAME, acc.id))

    companion object {
        const val STATE_NAME = "DodonaAccounts"
        const val STATE_STORAGE = "dodona.xml"

        /**
         * Creates a new DodonaAccount instance.
         *
         * @param server the server
         * @param user the Dodona user
         * @return the account
         */
        @JvmStatic
        fun createAccount(server: DodonaServer, user: User): DodonaAccount =
            DodonaAccount(user.email, "${user.firstName} ${user.lastName}", server.url)

        /**
         * Gets a shared instance of the account manager.
         */
        @JvmStatic
        fun getInstance(): DodonaAccountManager = service()
    }
}
