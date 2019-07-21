/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.code.preprocess;

import com.intellij.lang.Language;
import com.intellij.psi.PsiFile;

public interface FileSubmissionPreprocessor {

	/**
	 * Preprocess the file for submission (remove imports etc)
	 * @param file PsiFile to process
	 * @return The PsiFile
	 */
	PsiFile preprocess(PsiFile file);

	Language getLanguage();
}
