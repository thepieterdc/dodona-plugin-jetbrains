package be.ugent.piedcler.dodona.code.preprocess;

import be.ugent.piedcler.dodona.code.FileSubmissionPreprocessor;
import com.intellij.lang.Language;
import com.intellij.psi.PsiFile;

import java.util.HashMap;
import java.util.Map;

public class CombinedSubmissionPreprocessor implements FileSubmissionPreprocessor {

	private final HashMap<Language, FileSubmissionPreprocessor> preprocessorMap;

	public CombinedSubmissionPreprocessor(Map<Language, FileSubmissionPreprocessor> preprocessorMap) {
		this.preprocessorMap = new HashMap<>(preprocessorMap);
	}

	public CombinedSubmissionPreprocessor registerEntry(Language lang, FileSubmissionPreprocessor preprocessor) {
		preprocessorMap.put(lang, preprocessor);
		return this;
	}

	public CombinedSubmissionPreprocessor unregisterEntry(Language lang, FileSubmissionPreprocessor preprocessor) {
		preprocessorMap.remove(lang);
		return this;
	}

	@Override
	public PsiFile preprocess(PsiFile file) {
		for (Map.Entry<Language, FileSubmissionPreprocessor> entry : preprocessorMap.entrySet()) {
			Language key = entry.getKey();
			FileSubmissionPreprocessor value = entry.getValue();
			if (file.getLanguage().is(key)) {
				return value.preprocess(file);
			}
		}
		return file;
	}
}
