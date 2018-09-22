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

	public CombinedSubmissionPreprocessor registerEntry(FileSubmissionPreprocessor preprocessor) {
		if (preprocessor != null)
			preprocessorMap.put(preprocessor.getLanguage(), preprocessor);
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

	@Override
	public Language getLanguage() {
		return null;
	}
}
