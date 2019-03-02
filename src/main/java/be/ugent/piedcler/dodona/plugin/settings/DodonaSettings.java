/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.settings;

import be.ugent.piedcler.dodona.DodonaClient;
import be.ugent.piedcler.dodona.plugin.Api;
import com.intellij.credentialStore.CredentialAttributes;
import com.intellij.ide.passwordSafe.PasswordSafe;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;

import javax.annotation.Nonnull;
import java.util.Optional;

/**
 * Manages the configured settings.
 */
@State(name = "DodonaSettings")
public class DodonaSettings {
	private static final String CREDENTIALS_SUBSYSTEM = "dodona";
	private static final String CREDENTIALS_KEY_TOKEN = "token";
	
	/**
	 * Creates the Credential attributes.
	 *
	 * @return credential attributes
	 */
	@Nonnull
	private static CredentialAttributes createCredentialAttributes() {
		return new CredentialAttributes(String.format("%s-%s", CREDENTIALS_SUBSYSTEM, CREDENTIALS_KEY_TOKEN));
	}
	
	/**
	 * Gets the configured host.
	 *
	 * @return the host
	 */
	@Nonnull
	public String getHost() {
		return DodonaClient.DEFAULT_HOST;
	}
	
	/**
	 * Gets an instance of the settings.
	 *
	 * @return instance
	 */
	@Nonnull
	public static DodonaSettings getInstance() {
		return ServiceManager.getService(DodonaSettings.class);
	}
	
	/**
	 * Gets the authentication token.
	 *
	 * @return the authentication token
	 */
	@Nonnull
	public String getToken() {
		final CredentialAttributes credentials = createCredentialAttributes();
		return Optional.ofNullable(PasswordSafe.getInstance().getPassword(credentials)).orElse("");
	}
	
	/**
	 * Sets the authentication token.
	 *
	 * @param token the authentication token to set
	 */
	public void setToken(@Nonnull final String token) {
		final CredentialAttributes credentials = createCredentialAttributes();
		PasswordSafe.getInstance().setPassword(credentials, token);
		
		Api.clearClient();
	}
}
