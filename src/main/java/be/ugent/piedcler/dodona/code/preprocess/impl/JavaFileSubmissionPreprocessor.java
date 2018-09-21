/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.code.preprocess.impl;

import be.ugent.piedcler.dodona.code.preprocess.FileSubmissionPreprocessor;
import com.intellij.lang.Language;
import com.intellij.lang.java.JavaLanguage;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.psi.JavaRecursiveElementVisitor;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiPackageStatement;

import java.util.ArrayList;


public class JavaFileSubmissionPreprocessor implements FileSubmissionPreprocessor {

	@Override
	public PsiFile preprocess(PsiFile file) {
		ArrayList<PsiElement> toDelete = new ArrayList<>();
		file.accept(new JavaRecursiveElementVisitor() {
			@Override
			public void visitPackageStatement(PsiPackageStatement statement) {
				super.visitPackageStatement(statement);
				toDelete.add(statement);
			}
		});

		WriteCommandAction.runWriteCommandAction(file.getProject(),
			() -> toDelete.forEach(PsiElement::delete)
		);
		return file;
	}

	@Override
	public Language getLanguage() {
		return JavaLanguage.INSTANCE;
	}


}
