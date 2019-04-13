/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.toolwindow.submissions;

import com.intellij.openapi.ui.SimpleToolWindowPanel;

/**
 * Panel for the tab showing exercise submissions.
 */
class SubmissionsTabPanel extends SimpleToolWindowPanel {
	
	/**
	 * DodonaToolWindowView constructor.
	 */
	public SubmissionsTabPanel() {
		super(false, true);
	}
}
