package be.ugent.piedcler.dodona.code.identifiers.setter;

import com.intellij.lang.Language;
import com.intellij.openapi.editor.Document;


public interface ExerciseIdentifierSetter {

	void setIdentifier(Language language, Document file, String id);

	Language getLanguage();
}
