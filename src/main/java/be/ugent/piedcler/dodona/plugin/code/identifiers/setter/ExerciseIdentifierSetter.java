/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.plugin.code.identifiers.setter;

import com.intellij.lang.Language;
import com.intellij.openapi.editor.Document;


public interface ExerciseIdentifierSetter {

	void setIdentifier(Language language, Document file, String id);

	Language getLanguage();
}