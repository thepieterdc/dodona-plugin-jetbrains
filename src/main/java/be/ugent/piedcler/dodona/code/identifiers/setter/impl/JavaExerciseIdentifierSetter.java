package be.ugent.piedcler.dodona.code.identifiers.setter.impl;

import be.ugent.piedcler.dodona.code.identifiers.setter.ExerciseIdentifierSetter;
import com.intellij.lang.Language;
import com.intellij.lang.java.JavaLanguage;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.psi.*;

import static java.lang.String.format;

public class JavaExerciseIdentifierSetter implements ExerciseIdentifierSetter {

	@Override
	public void setIdentifier(Language language, Document file, String id) {
		file.insertString(0, format("// %s%n", id));
	}

	@Override
	public Language getLanguage() {
		return JavaLanguage.INSTANCE;
	}

}
