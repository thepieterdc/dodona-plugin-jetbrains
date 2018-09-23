/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
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
