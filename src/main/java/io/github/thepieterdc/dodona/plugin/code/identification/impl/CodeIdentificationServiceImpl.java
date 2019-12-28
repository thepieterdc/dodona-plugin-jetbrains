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
import io.github.thepieterdc.dodona.plugin.exercise.Identification;
import io.github.thepieterdc.dodona.plugin.tasks.IdentifyTask;
import io.github.thepieterdc.dodona.resources.Exercise;

import javax.annotation.Nonnull;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Default implementation of a CodeIdentificationService.
 */
public class CodeIdentificationServiceImpl implements CodeIdentificationService {
	private final Project project;
	
	/**
	 * CodeIdentificationServiceImpl constructor.
	 *
	 * @param project the current project
	 */
	public CodeIdentificationServiceImpl(final Project project) {
		this.project = project;
	}
	
	@Nonnull
	@Override
	public CodeIdentifier getIdentifier(final String fileName) {
		final String extension = FileUtilRt.getExtension(fileName)
			.toLowerCase(Locale.getDefault());
		
		return CodeIdentifier.getForExtension(extension)
			.orElse(CodeIdentifier.JAVA);
	}
	
	@Override
	public CompletableFuture<Identification> identify(final Document document) {
		// Prepare the result.
		final CompletableFuture<Identification> result = new CompletableFuture<>();
		
		// Prompt the user to identify the exercise.
		final FullIdentification identification = IdentifyTask
			.create(this.project, DodonaBundle.message("tasks.identify.title.identify"))
			.execute()
			.orElse(null);
		
		// Cancelled.
		if (identification == null) {
			result.cancel(true);
			return result;
		}
		
		// Find the identifier to use.
		final CodeIdentifier identifier = Optional.of(identification)
			.map(FullIdentification::getExercise)
			.flatMap(Exercise::getProgrammingLanguage)
			.flatMap(lang -> CodeIdentifier.getForExtension(lang.getExtension()))
			.orElse(CodeIdentifier.JAVA);
		
		// Perform the identification in the file.
		WriteCommandAction.runWriteCommandAction(this.project, () -> {
			identifier.process(identification.getExercise(), document);
			result.complete(identification);
		});
		
		return result;
	}
	
	@Override
	public void identifyCurrent() {
		// Get the current editor.
		final Optional<FileEditorManager> optEditor = Optional
			.ofNullable(FileEditorManager.getInstance(this.project));
		
		// Get the current document.
		final Document document = optEditor
			.map(FileEditorManager::getSelectedTextEditor)
			.map(Editor::getDocument)
			.orElseThrow(CurrentFileReadException::new);
		
		// Get the current file.
		final VirtualFile file = optEditor
			.map(FileEditorManager::getSelectedEditor)
			.map(FileEditor::getFile)
			.orElseThrow(CurrentFileReadException::new);
		
		// Create a bus to broadcast the event when the exercise is identified.
		final MessageBus bus = this.project.getMessageBus();
		
		// Perform the identification.
		this.identify(document).whenComplete((identification, throwable) -> {
			if (identification != null) {
				bus.syncPublisher(CurrentExerciseListener.CHANGED_TOPIC)
					.onCurrentExercise(file, identification);
			}
		});
	}
}
