/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.exercise.creation.impl;

import com.intellij.openapi.application.ActionsKt;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.FileTypeRegistry;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.intellij.ui.UIBundle;
import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import io.github.thepieterdc.dodona.plugin.code.identification.CodeIdentificationService;
import io.github.thepieterdc.dodona.plugin.exceptions.CancelledException;
import io.github.thepieterdc.dodona.plugin.exercise.FullIdentification;
import io.github.thepieterdc.dodona.plugin.exercise.creation.ExerciseCreationService;
import io.github.thepieterdc.dodona.plugin.exercise.naming.ExerciseNamingService;
import io.github.thepieterdc.dodona.resources.Exercise;
import org.jetbrains.annotations.Nls;

import javax.annotation.Nonnull;

/**
 * Default implementation of an ExerciseCreationService.
 */
public class ExerciseCreationServiceImpl implements ExerciseCreationService {
	private final CodeIdentificationService codeIdentificationService;
	private final PsiFileFactory fileFactory;
	private final ExerciseNamingService naming;
	
	/**
	 * ExerciseCreationServiceImpl constructor.
	 *
	 * @param project the current project
	 */
	public ExerciseCreationServiceImpl(final Project project) {
		this.codeIdentificationService = CodeIdentificationService.getInstance();
		this.fileFactory = PsiFileFactory.getInstance(project);
		this.naming = ExerciseNamingService.getInstance();
	}
	
	@Override
	public VirtualFile create(final PsiDirectory directory,
	                          final FullIdentification identification) {
		// Generate a filename.
		final String filename = this
			.getFileName(directory, identification.getExercise());
		
		// Determine the file type.
		final FileType fileType = FileTypeRegistry.getInstance()
			.getFileTypeByFileName(filename);
		
		// Generate the file contents.
		final String rawContents = identification.getExercise()
			.getBoilerplate()
			.orElse("");
		
		// Insert the identification string.
		final String contents = this.codeIdentificationService
			.getIdentifier(filename)
			.process(identification.getExercise(), rawContents);
		
		// Create the file.
		final PsiFile file = this.fileFactory
			.createFileFromText(filename, fileType, contents);
		
		// Return the created file
		return ActionsKt
			.runWriteAction(() -> (PsiFile) directory.add(file))
			.getVirtualFile();
	}
	
	/**
	 * Gets a filename for the given exercise.
	 *
	 * @param directory the directory to save the file in, file may not yet exist
	 * @param exercise  the exercise
	 * @return the filename and extension
	 */
	@Nonnull
	private String getFileName(final PsiDirectory directory, final Exercise exercise) {
		// Propose an initial name.
		String name = this.naming.generateFileName(exercise).orElse(null);
		
		while (true) {
			// Prompt the user for a filename.
			name = Messages.showInputDialog(
				UIBundle.message("create.new.file.enter.new.file.name.prompt.text"),
				UIBundle.message("new.file.dialog.title"),
				Messages.getQuestionIcon(), name, null
			);
			
			// User pressed cancel.
			if (name == null) {
				throw new CancelledException();
			}
			
			// Validate the filename.
			if (name.trim().isEmpty()) {
				showError(UIBundle.message("create.new.file.file.name.cannot.be.empty.error.message"));
			} else {
				if (directory.findFile(name) == null) {
					return name;
				} else {
					showError(DodonaBundle.message("creation.already.exists"));
				}
			}
		}
	}
	
	/**
	 * Shows an error message to the user.
	 *
	 * @param message the message to display
	 */
	private static void showError(@Nls final String message) {
		Messages.showMessageDialog(
			message,
			UIBundle.message("error.dialog.title"),
			Messages.getErrorIcon()
		);
	}
}
