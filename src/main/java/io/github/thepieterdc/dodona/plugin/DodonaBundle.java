/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */
package io.github.thepieterdc.dodona.plugin;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.PropertyKey;

import javax.annotation.Nonnull;
import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * Translation bundle.
 */
public final class DodonaBundle {
	@NonNls
	public static final String BUNDLE_NAME = "messages.Dodona";
	
	@Nonnull
	private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
	
	@NonNls
	public static final String NAME = "Dodona";
	
	/**
	 * DodonaBundle constructor.
	 */
	private DodonaBundle() {
	
	}
	
	/**
	 * Gets a translation string.
	 *
	 * @param key    the key of the string
	 * @param params optional formatting parameters
	 * @return the string or "null"
	 */
	@Nonnull
	public static String message(
		@PropertyKey(resourceBundle = BUNDLE_NAME) final String key,
		final Object... params) {
		if (!BUNDLE.containsKey(key)) {
			return "null";
		}
		
		if (params.length == 0) {
			return BUNDLE.getString(key);
		}
		
		// Fill in the parameters.
		return MessageFormat.format(BUNDLE.getString(key), params);
	}
}
