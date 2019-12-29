/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.ui.cards;

import com.intellij.openapi.project.Project;
import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import io.github.thepieterdc.dodona.plugin.authentication.DodonaAuthenticator;
import io.github.thepieterdc.dodona.plugin.ui.Icons;
import io.github.thepieterdc.dodona.plugin.ui.TextColors;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import java.awt.*;

/**
 * Card to display when the user has entered incorrect authentication
 * credentials.
 */
public final class UnauthenticatedCard extends IconTextCard {
	private static final JComponent ICON = Icons.toComponent(
		Icons.USER_INVALID.color(TextColors.SECONDARY)
	);
	
	/**
	 * UnauthenticatedCard constructor.
	 *
	 * @param project current active project
	 * @param parent  parent component
	 */
	private UnauthenticatedCard(@Nullable final Project project,
	                            @Nullable final Component parent) {
		super(ICON, DodonaBundle.message("card.unauthenticated.message"), () ->
			DodonaAuthenticator.getInstance().requestAuthentication(project, parent)
		);
	}
	
	/**
	 * Creates a new instance of the UnauthenticatedCard.
	 *
	 * @param project current active project
	 * @param parent  parent component
	 * @return the instance
	 */
	@Nonnull
	public static UnauthenticatedCard create(@Nullable final Project project,
	                                         @Nullable final Component parent) {
		return new UnauthenticatedCard(project, parent);
	}
}
