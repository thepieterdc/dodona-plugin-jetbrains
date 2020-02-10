/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.ui.cards;

import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import io.github.thepieterdc.dodona.plugin.ui.Icons;
import io.github.thepieterdc.dodona.plugin.ui.TextColors;
import io.github.thepieterdc.dodona.plugin.ui.Updatable;

import javax.annotation.Nonnull;
import javax.swing.*;

/**
 * Card to display when the user is not subscribed to any courses.
 */
public final class NoCourseSubscribedCard extends IconTextCard {
	private static final JComponent ICON = Icons.toComponent(
		Icons.COURSE.color(TextColors.SECONDARY)
	);
	
	/**
	 * NoCourseSubscribedCard constructor.
	 */
	private NoCourseSubscribedCard() {
		super(ICON, DodonaBundle.message("card.no_course_subscribed.message"));
	}
	
	/**
	 * NoCourseSubscribedCard constructor.
	 *
	 * @param parent the parent panel
	 */
	private NoCourseSubscribedCard(final Updatable parent) {
		super(ICON, DodonaBundle.message("card.no_course_subscribed.message"), parent::requestUpdate);
	}
	
	/**
	 * Creates a new instance of the NoCourseSubscribedCard.
	 *
	 * @return the instance
	 */
	@Nonnull
	public static NoCourseSubscribedCard create() {
		return new NoCourseSubscribedCard();
	}
	
	/**
	 * Creates a new instance of the NoCourseSubscribedCard.
	 *
	 * @param parent the parent panel
	 * @return the instance
	 */
	@Nonnull
	public static NoCourseSubscribedCard create(final Updatable parent) {
		return new NoCourseSubscribedCard(parent);
	}
}
