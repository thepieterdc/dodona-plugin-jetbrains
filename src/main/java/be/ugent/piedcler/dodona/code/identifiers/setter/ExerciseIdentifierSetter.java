package be.ugent.piedcler.dodona.code.identifiers.setter;

import be.ugent.piedcler.dodona.code.identifiers.setter.impl.JavaExerciseIdentifierSetter;
import com.intellij.lang.Language;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.psi.PsiFile;

public interface ExerciseIdentifierSetter {

	void setIdentifier(PsiFile file, String id);

	Language getLanguage();
}
