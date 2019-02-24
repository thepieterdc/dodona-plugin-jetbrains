/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.authentication;

import be.ugent.piedcler.dodona.plugin.exceptions.errors.AuthenticationCancelledException;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.project.Project;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * An authenticator for Dodona.
 */
public enum Authenticator {
	;
	
	/**
	 * Shows a login dialog and returns the entered login credentials.
	 *
	 * @param project   the current project
	 * @param oldData   the old authentication data, null to omit
	 * @param indicator the progress indicator
	 * @return the entered login credentials
	 */
	@Nonnull
	public static AuthenticationData askCredentials(@Nonnull final Project project,
	                                                @Nullable final AuthenticationData oldData,
	                                                @Nonnull final ProgressIndicator indicator) {
		indicator.setIndeterminate(true);
		indicator.setText("Waiting for user credentials");
		
		// Little hack to allow modifying this variable from the UI thread.
		final AuthenticationData[] data = new AuthenticationData[1];
		
		ApplicationManager.getApplication().invokeAndWait(() -> {
			final LoginDialog dialog = new LoginDialog(project, oldData);
			dialog.show();
			
			if (dialog.isOK()) {
				data[0] = dialog.getAuthenticationData();
			}
		});
		
		if (data[0] == null) {
			indicator.cancel();
			throw new AuthenticationCancelledException();
		}
		
		return data[0];
	}
}
