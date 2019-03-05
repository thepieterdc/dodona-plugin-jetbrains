/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.plugin.code.identifiers.setter.impl;

import be.ugent.piedcler.dodona.plugin.code.identifiers.setter.ExerciseIdentifierSetter;
import com.intellij.lang.Language;
import com.intellij.lang.java.JavaLanguage;
import com.intellij.openapi.editor.Document;

import static java.lang.String.format;

public class JavaExerciseIdentifierSetter implements ExerciseIdentifierSetter {

	@Override
	public void setIdentifier(Language language, Document file, String id) {
		file.insertString(0, format("// %s\n", id));
	}

	@Override
	public Language getLanguage() {
		return JavaLanguage.INSTANCE;
	}

}
