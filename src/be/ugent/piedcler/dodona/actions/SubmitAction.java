/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;

/**
 * Action that submits the current file to Dodona.
 */
public class SubmitAction extends AnAction {
	
	@Override
	public void actionPerformed(AnActionEvent e) {
		final String code = e.getData(DataKeys.FILE_TEXT);
		
		if (code != null) {
			//extract course/exercise from code
			//submit!
			System.out.println(code);
		}
	}
}
