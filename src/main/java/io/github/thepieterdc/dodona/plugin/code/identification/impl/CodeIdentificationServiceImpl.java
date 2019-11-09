/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.code.identification.impl;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.io.FileUtilRt;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.messages.MessageBus;
import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import io.github.thepieterdc.dodona.plugin.code.identification.CodeIdentificationService;
import io.github.thepieterdc.dodona.plugin.code.identification.CodeIdentifier;
import io.github.thepieterdc.dodona.plugin.exceptions.error.CurrentFileReadException;
import io.github.thepieterdc.dodona.plugin.exercise.CurrentExerciseListener;
import io.github.thepieterdc.dodona.plugin.exercise.FullIdentification;
import io.github.thepieterdc.dodona.plugin.tasks.IdentifyTask;

import javax.annotation.Nonnull;
import java.util.Locale;
import java.util.Optional;

/**
 * Default implementation of a CodeIdentificationService.
 */
public class CodeIdentificationServiceImpl implements CodeIdentificationService {
	@Nonnull
	@Override
	public CodeIdentifier getIdentifier(final String fileName) {
		final String extension = FileUtilRt.getExtension(fileName)
			.toLowerCase(Locale.getDefault());
		
		return CodeIdentifier.getForExtension(extension)
			.orElse(CodeIdentifier.JAVA);
	}
	
	@Override
	public void identifyCurrent(final Project project) {
		final Optional<FileEditorManager> editor = Optional
			.ofNullable(FileEditorManager.getInstance(project));
		
		// Get the current document.
		final Document document = editor
			.map(FileEditorManager::getSelectedTextEditor)
			.map(Editor::getDocument)
			.orElseThrow(CurrentFileReadException::new);
		
		// Get the current file.
		final VirtualFile file = editor
			.map(FileEditorManager::getSelectedEditor)
			.map(FileEditor::getFile)
			.orElseThrow(CurrentFileReadException::new);
		
		// Prompt the user to identify the exercise.
		final FullIdentification identification = IdentifyTask
			.create(project, DodonaBundle.message("tasks.identify.title.identify"))
			.execute();
		
		// Create a bus to broadcast the event when the exercise is identified.
		final MessageBus bus = project.getMessageBus();
		
		// Find the identifier to use.
		final CodeIdentifier identifier = identification
			.getExercise()
			.getProgrammingLanguage()
			.flatMap(lang -> CodeIdentifier.getForExtension(lang.getExtension()))
			.orElse(CodeIdentifier.JAVA);
		
		// Perform the identification in the file.
		WriteCommandAction.runWriteCommandAction(project, () -> {
			identifier.process(identification.getExercise(), document);
			bus.syncPublisher(CurrentExerciseListener.CHANGED_TOPIC)
				.onCurrentExercise(file, identification);
		});
	}
}
