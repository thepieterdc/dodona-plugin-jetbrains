package be.ugent.piedcler.dodona.code.preprocess;

import com.intellij.psi.PsiFile;

public interface FileSubmissionPreprocessor {

	/**
	 * Preprocess the file for submission (remove imports etc)
	 * @param file PsiFile to process
	 * @return The PsiFile
	 */
	PsiFile preprocess(PsiFile file);

}
