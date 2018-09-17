package be.ugent.piedcler.dodona.code.preprocess;

import be.ugent.piedcler.dodona.code.FileSubmissionPreprocessor;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.psi.JavaRecursiveElementVisitor;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiPackageStatement;

import java.util.ArrayList;


// hello
public class JavaFileSubmissionPreprocessor implements FileSubmissionPreprocessor {

	@Override
	public PsiFile preprocess(PsiFile file) {
		ArrayList<PsiElement> toDelete = new ArrayList<>();
		file.accept(new JavaRecursiveElementVisitor() {
			@Override
			public void visitPackageStatement(PsiPackageStatement statement) {
				super.visitPackageStatement(statement);
				toDelete.add(statement);
			}
		});

		WriteCommandAction.runWriteCommandAction(file.getProject(),
			() -> toDelete.forEach(PsiElement::delete)
		);
		return file;
	}


}
