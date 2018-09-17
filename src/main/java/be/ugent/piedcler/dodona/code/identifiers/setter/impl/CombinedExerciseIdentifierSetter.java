package be.ugent.piedcler.dodona.code.identifiers.setter.impl;

import be.ugent.piedcler.dodona.code.identifiers.setter.ExerciseIdentifierSetter;
import com.intellij.lang.Language;
import com.intellij.psi.PsiFile;

import java.util.HashMap;
import java.util.Map;

public class CombinedExerciseIdentifierSetter implements ExerciseIdentifierSetter {


	private final HashMap<Language, ExerciseIdentifierSetter> setterMap;

	public CombinedExerciseIdentifierSetter() {
		setterMap = new HashMap<>();
	}

	public CombinedExerciseIdentifierSetter(Map<Language, ExerciseIdentifierSetter> preprocessorMap) {
		this.setterMap = new HashMap<>(preprocessorMap);
	}

	public CombinedExerciseIdentifierSetter registerEntry(Language lang, ExerciseIdentifierSetter setter) {
		this.setterMap.put(lang, setter);
		return this;
	}

	public CombinedExerciseIdentifierSetter unregisterEntry(Language lang) {
		setterMap.remove(lang);
		return this;
	}

	@Override
	public void setIdentifier(PsiFile file, String id) {
		if (setterMap.containsKey(file.getLanguage()))
			setterMap.get(file.getLanguage()).setIdentifier(file, id);
	}
}
