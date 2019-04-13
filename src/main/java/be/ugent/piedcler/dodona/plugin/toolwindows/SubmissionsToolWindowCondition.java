/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.toolwindows;

import be.ugent.piedcler.dodona.plugin.dto.Identification;
import be.ugent.piedcler.dodona.plugin.exceptions.errors.CodeReadException;
import be.ugent.piedcler.dodona.plugin.exceptions.warnings.ExerciseNotSetException;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Condition;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;

import javax.annotation.Nonnull;
import java.util.Optional;

/**
 * Creates tool windows to list submissions.
 */
public class SubmissionsToolWindowCondition implements Condition<Project> {
	@Override
	public boolean value(@Nonnull final Project project) {
		final Document document = Optional.ofNullable(FileEditorManager.getInstance(project))
			.map(FileEditorManager::getSelectedTextEditor)
			.map(Editor::getDocument)
			.orElseThrow(CodeReadException::new);
		
		final PsiFile file = Optional.ofNullable(PsiDocumentManager.getInstance(project))
			.map(mgr -> mgr.getPsiFile(document))
			.orElseThrow(CodeReadException::new);
		
		final String code = Optional.of(file)
			.map(PsiElement::copy)
			.map(e -> (PsiFile) e)
			.map(preprocessor::preprocess)
			.map(PsiElement::getText)
			.orElseThrow(CodeReadException::new);
		
		final Identification identification = idParser.identify(code).orElseThrow(ExerciseNotSetException::new);
		
	}
}
