package be.ugent.piedcler.dodona.code.identifiers.setter.impl;


import be.ugent.piedcler.dodona.code.identifiers.setter.ExerciseIdentifierSetter;
import com.intellij.lang.Language;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.jetbrains.python.PythonLanguage;
import com.jetbrains.python.psi.PyExpression;
import com.jetbrains.python.psi.PyUtil;


public class PythonExerciseIdentifierSetter implements ExerciseIdentifierSetter {


	@Override
	public void setIdentifier(PsiFile file, String id) {
		WriteCommandAction.runWriteCommandAction(file.getProject(),
			() -> {
				final PyExpression comment = PyUtil.createExpressionFromFragment("\"\"\" " + id + " \"\"\"", file);
				final PsiElement firstChild = file.getFirstChild();
				if (comment != null) file.addBefore(comment, firstChild);
			}
		);

	}

	@Override
	public Language getLanguage() {
		return PythonLanguage.INSTANCE;
	}

	public static PythonExerciseIdentifierSetter getInstance() {
		return ServiceManager.getService(PythonExerciseIdentifierSetter.class);
	}
}
