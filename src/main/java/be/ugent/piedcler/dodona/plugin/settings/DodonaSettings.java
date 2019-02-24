/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.settings;

import be.ugent.piedcler.dodona.plugin.authentication.AuthenticationData;
import com.intellij.credentialStore.CredentialAttributes;
import com.intellij.credentialStore.CredentialAttributesKt;
import com.intellij.ide.passwordSafe.PasswordSafe;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Optional;

/**
 * Holds settings for the plugin.
 */
@State(name = "DodonaSettings")
public class DodonaSettings implements PersistentStateComponent<DodonaSettings.State> {
	private static final String CREDENTIALS_SUBSYSTEM = "dodona";
	
	private State state = new State();
	
	/**
	 * Contains the settings.
	 */
	public static class State {
		@Nonnull
		public String HOST = "https://dodona.ugent.be/";
	}
	
	/**
	 * Creates the Credential attributes.
	 *
	 * @param key the key
	 * @return credential attributes
	 */
	private static CredentialAttributes createCredentialAttributes(final String key) {
		return new CredentialAttributes(CredentialAttributesKt.generateServiceName(CREDENTIALS_SUBSYSTEM, key));
	}
	
	/**
	 * Gets the authentication data saved in the settings.
	 *
	 * @return the authentication data
	 */
	@Nonnull
	public Optional<AuthenticationData> getAuthenticationData() {
		return this.getToken().map(token -> AuthenticationData.create(this.getHost(), token));
	}
	
	/**
	 * Gets an instance of the settings.
	 *
	 * @return the instance
	 */
	public static DodonaSettings getInstance() {
		return ServiceManager.getService(DodonaSettings.class);
	}
	
	/**
	 * Gets the host.
	 *
	 * @return the host
	 */
	@Nonnull
	public String getHost() {
		return this.state.HOST;
	}
	
	@Nonnull
	@Override
	public State getState() {
		return this.state;
	}
	
	/**
	 * Gets the api token.
	 *
	 * @return the api token, if set
	 */
	@Nonnull
	private Optional<String> getToken() {
		final CredentialAttributes credentials = createCredentialAttributes(this.getHost());
		return Optional.ofNullable(PasswordSafe.getInstance().getPassword(credentials));
	}
	
	@Override
	public void loadState(@NotNull final State state) {
		this.state = state;
	}
	
	/**
	 * Sets the authentication data.
	 *
	 * @param authData the authentication data
	 */
	public void setAuthenticationData(@Nonnull final AuthenticationData authData) {
		this.setHost(authData.getHost());
		this.setToken(authData.getToken());
	}
	
	/**
	 * Sets the host.
	 *
	 * @param host the host
	 */
	public void setHost(@Nonnull final String host) {
		this.state.HOST = host;
	}
	
	/**
	 * Sets the api token.
	 *
	 * @param token the api token to set
	 */
	public void setToken(@Nonnull final String token) {
		final CredentialAttributes credentials = createCredentialAttributes(this.state.HOST);
		PasswordSafe.getInstance().setPassword(credentials, token);
	}
}
