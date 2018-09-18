package be.ugent.piedcler.dodona.code.identifiers.setter.impl;

import be.ugent.piedcler.dodona.code.identifiers.setter.ExerciseIdentifierSetter;
import com.intellij.lang.Language;
import com.intellij.lang.java.JavaLanguage;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.util.ThrowableComputable;
import com.intellij.psi.*;

import static java.lang.String.format;

public class JavaExerciseIdentifierSetter implements ExerciseIdentifierSetter {

	public static final Language LANGUAGE = JavaLanguage.INSTANCE;
	@Override
	public void setIdentifier(PsiFile file, String id) {
		final PsiElementFactory factory = JavaPsiFacade.getInstance(file.getProject()).getElementFactory();
		final PsiComment comment = factory.createCommentFromText(format(id), null);
		final PsiElement firstChild = file.getFirstChild();

		WriteCommandAction.runWriteCommandAction(file.getProject(),
			(ThrowableComputable<PsiElement, RuntimeException>) () -> file.addBefore(comment, firstChild)
		);
	}
}
