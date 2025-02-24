/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.code.analysis.impl;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiErrorElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiRecursiveElementVisitor;
import io.github.thepieterdc.dodona.plugin.code.analysis.CodeAnalysisService;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Implementation of CodeAnalysisService.
 */
public class CodeAnalysisServiceImpl implements CodeAnalysisService {
	private final Project project;
	
	/**
	 * CodeAnalysisServiceImpl constructor.
	 *
	 * @param project the current project
	 */
	public CodeAnalysisServiceImpl(final Project project) {
		this.project = project;
	}
	
	@Nonnull
	@Override
	public Collection<Integer> errors(final VirtualFile file) {
		final PsiFile psiFile = PsiManager.getInstance(this.project).findFile(file);
		if (psiFile == null) {
			return Collections.emptySet();
		}

		final Document document = FileDocumentManager.getInstance().getDocument(file);

		// Handle the errors.
		final Collection<Integer> errors = new ArrayList<>();
		psiFile.accept(new PsiRecursiveElementVisitor() {
			@Override
			public void visitErrorElement(@NotNull PsiErrorElement element) {
				final Integer line = document != null ? document.getLineNumber(element.getTextOffset()) + 1 : 0;
				errors.add(line);
				super.visitErrorElement(element);
			}
		});
		return errors;
	}
}
