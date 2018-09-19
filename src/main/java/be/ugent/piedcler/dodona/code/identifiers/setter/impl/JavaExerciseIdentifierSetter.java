package be.ugent.piedcler.dodona.code.identifiers.setter.impl;

import be.ugent.piedcler.dodona.code.identifiers.setter.ExerciseIdentifierSetter;
import com.intellij.lang.Language;
import com.intellij.lang.java.JavaLanguage;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.psi.*;

import static java.lang.String.format;

public class JavaExerciseIdentifierSetter implements ExerciseIdentifierSetter {


	@Override
	public void setIdentifier(PsiFile file, String id) {
		WriteCommandAction.runWriteCommandAction(file.getProject(),
			() -> {
				final PsiElementFactory factory = JavaPsiFacade.getInstance(file.getProject()).getElementFactory();
				final PsiComment comment = factory.createCommentFromText(format("// %s", id), null);
				final PsiElement firstChild = file.getFirstChild();
				file.addBefore(comment, firstChild);
			}
		);
	}

	@Override
	public Language getLanguage() {
		return JavaLanguage.INSTANCE;
	}

}
