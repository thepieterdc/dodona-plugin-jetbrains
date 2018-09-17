package be.ugent.piedcler.dodona.code;

import com.intellij.psi.PsiFile;

public interface FileSubmissionPreprocessor {

	/**
	 * Preprocess the file for submission (remove imports etc)
	 * @param file PsiFile to process
	 * @return TODO
	 */
	PsiFile preprocess(PsiFile file);


}
