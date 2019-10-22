/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.tasks;

import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.project.Project;
import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import io.github.thepieterdc.dodona.plugin.api.DodonaExecutor;
import io.github.thepieterdc.dodona.plugin.authentication.DodonaAuthenticator;
import io.github.thepieterdc.dodona.plugin.exceptions.CancelledException;
import io.github.thepieterdc.dodona.plugin.exercise.FullIdentification;
import io.github.thepieterdc.dodona.plugin.tasks.ui.IdentifyExerciseDialog;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

/**
 * Prompts the user to identify the current exercise.
 */
public class IdentifyTask extends AbstractDodonaResultTask<FullIdentification> {
	private final DodonaExecutor executor;
	
	@Nullable
	private FullIdentification identification;
	
	/**
	 * SubmitSolutionTask constructor.
	 *
	 * @param project the current project
	 */
	private IdentifyTask(final Project project) {
		super(project, DodonaBundle.message("tasks.select_exercise.title"));
		this.executor = DodonaAuthenticator.getInstance().getExecutor();
	}
	
	@Nonnull
	@Override
	protected FullIdentification compute(final ProgressIndicator progress) {
		try {
			// Set the progressbar.
			progress.setIndeterminate(true);
			progress.setText(DodonaBundle.message("tasks.select_exercise.progress.identify"));
			
			// Perform the identification.
			SwingUtilities.invokeAndWait(this::showDialog);
			
			// Return the result if available.
			return Optional.ofNullable(this.identification)
				.orElseThrow(CancelledException::new);
		} catch (final InterruptedException | InvocationTargetException ex) {
			throw new CancelledException();
		}
	}
	
	/**
	 * Creates an exercise selection task.
	 *
	 * @param project the current project
	 * @return the task
	 */
	@Nonnull
	public static DodonaResultTask<FullIdentification> create(final Project project) {
		return new IdentifyTask(project);
	}
	
	/**
	 * Shows the selection dialog.
	 */
	private void showDialog() {
		final IdentifyExerciseDialog dialog = new IdentifyExerciseDialog(
			this.myProject,
			this.executor
		);
		
		// Show the dialog.
		if (dialog.showAndGet()) {
			this.identification = dialog.getSelectedExercise().orElse(null);
		}
	}
}
