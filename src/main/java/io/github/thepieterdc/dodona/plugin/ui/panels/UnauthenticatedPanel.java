/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.ui.panels;

import com.intellij.util.ui.AnimatedIcon;
import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import io.github.thepieterdc.dodona.plugin.ui.Icons;
import org.jetbrains.annotations.NonNls;

import javax.swing.*;

/**
 * Panel to display when the user has entered incorrect authentication
 * credentials.
 */
public final class UnauthenticatedPanel extends IconTextPanel {
	@NonNls
	private static final JComponent ICON = new AnimatedIcon(
		"ICON_UNAUTHENTICATED", new Icon[0], Icons.USER_INVALID, 0
	);
	
	/**
	 * UnauthenticatedPanel constructor.
	 */
	public UnauthenticatedPanel() {
		super(ICON, DodonaBundle.message("panel.unauth.failed.message"));
	}
}
