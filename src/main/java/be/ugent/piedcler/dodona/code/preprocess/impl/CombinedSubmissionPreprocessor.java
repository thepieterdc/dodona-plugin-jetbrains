package be.ugent.piedcler.dodona.code.preprocess.impl;

import be.ugent.piedcler.dodona.code.preprocess.FileSubmissionPreprocessor;
import com.intellij.lang.Language;
import com.intellij.psi.PsiFile;

import java.util.HashMap;
import java.util.Map;

public class CombinedSubmissionPreprocessor implements FileSubmissionPreprocessor {

	private final HashMap<Language, FileSubmissionPreprocessor> preprocessorMap;

	public CombinedSubmissionPreprocessor() {
		preprocessorMap = new HashMap<>();
	}

	public CombinedSubmissionPreprocessor(Map<Language, FileSubmissionPreprocessor> preprocessorMap) {
		this.preprocessorMap = new HashMap<>(preprocessorMap);
	}

	public CombinedSubmissionPreprocessor registerEntry(Language lang, FileSubmissionPreprocessor preprocessor) {
		preprocessorMap.put(lang, preprocessor);
		return this;
	}

	public CombinedSubmissionPreprocessor unregisterEntry(Language lang) {
		preprocessorMap.remove(lang);
		return this;
	}

	@Override
	public PsiFile preprocess(PsiFile file) {
		if (preprocessorMap.containsKey(file.getLanguage()))
			return preprocessorMap.get(file.getLanguage()).preprocess(file);
		return file;
	}
}
