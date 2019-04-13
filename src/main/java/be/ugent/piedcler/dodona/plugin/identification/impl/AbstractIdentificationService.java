/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.identification.impl;

import be.ugent.piedcler.dodona.plugin.identification.Identification;
import be.ugent.piedcler.dodona.plugin.identification.IdentificationService;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;

import javax.annotation.Nonnull;
import java.util.Optional;

/**
 * An abstract identification service implementation.
 */
abstract class AbstractIdentificationService implements IdentificationService {
	@Override
	@Nonnull
	public Optional<Identification> identify(@Nonnull final PsiFile file) {
		return Optional.of(file)
			.map(PsiElement::getText)
			.flatMap(this::identify);
	}
	
	@Override
	@Nonnull
	public Optional<Identification> identify(@Nonnull final Project project,
	                                         @Nonnull final VirtualFile file) {
		return Optional.ofNullable(PsiManager.getInstance(project).findFile(file)).flatMap(this::identify);
	}
	
	@Override
	@Nonnull
	public Optional<Identification> identifyOpened(@Nonnull final Project project) {
		return Optional.ofNullable(FileEditorManager.getInstance(project))
			.map(FileEditorManager::getSelectedTextEditor)
			.map(Editor::getDocument)
			.map(doc -> PsiDocumentManager.getInstance(project).getPsiFile(doc))
			.flatMap(this::identify);
	}
}
