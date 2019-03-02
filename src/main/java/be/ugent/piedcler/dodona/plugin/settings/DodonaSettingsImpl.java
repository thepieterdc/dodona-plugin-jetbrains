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
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.components.State;

import javax.annotation.Nonnull;
import java.util.Optional;

/**
 * Manages the configured settings.
 */
@State(name = "DodonaSettings")
public class DodonaSettingsImpl implements DodonaSettings {
	private static final String CREDENTIALS_SUBSYSTEM = "dodona";
	private static final String CREDENTIALS_KEY_TOKEN = "token";
	
	private static final String SETTING_HIDE_CORRECT_EXERCISES = "hideCorrectExercises";
	
	/**
	 * Creates the Credential attributes.
	 *
	 * @return credential attributes
	 */
	@Nonnull
	private static CredentialAttributes createCredentialAttributes() {
		return new CredentialAttributes(String.format("%s-%s", CREDENTIALS_SUBSYSTEM, CREDENTIALS_KEY_TOKEN));
	}
	
	@Nonnull
	@Override
	public String getHost() {
		return DodonaClient.DEFAULT_HOST;
	}
	
	@Nonnull
	@Override
	public String getToken() {
		final CredentialAttributes credentials = createCredentialAttributes();
		return Optional.ofNullable(PasswordSafe.getInstance().getPassword(credentials)).orElse("");
	}
	
	@Override
	public boolean hideCorrectExercises() {
		final PropertiesComponent properties = PropertiesComponent.getInstance();
		return properties.getBoolean(SETTING_HIDE_CORRECT_EXERCISES, false);
	}
	
	@Override
	public void setHideCorrectExercises(final boolean hide) {
		final PropertiesComponent properties = PropertiesComponent.getInstance();
		properties.setValue(SETTING_HIDE_CORRECT_EXERCISES, hide);
	}
	
	@Override
	public void setToken(@Nonnull final String token) {
		final CredentialAttributes credentials = createCredentialAttributes();
		PasswordSafe.getInstance().setPassword(credentials, token);
		
		Api.clearClient();
	}
}
