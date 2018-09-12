/*
 * Copyright (c) 2018 - Singular-IT. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Julie De Meyer
 * @author Robbe Vanhaesebroeck
 *
 * https://www.limpio.online/
 */
package be.ugent.piedcler.dodona.resources;

import com.intellij.CommonBundle;
import com.intellij.reference.SoftReference;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.PropertyKey;

import java.lang.ref.Reference;
import java.util.ResourceBundle;

/**
 * Provides access to the properties file.
 */
public class DodonaBundle {
	@NonNls
	private static final String BUNDLE = "resources.dodona";
	private static Reference<ResourceBundle> bundle;
	
	/**
	 * DodonaBundle constructor.
	 */
	private DodonaBundle() {
		//unused.
	}
	
	/**
	 * Gets a string from the properties file.
	 *
	 * @param key    the key to lookup
	 * @param params additional formatting parameters
	 * @return the formatted string
	 */
	public static String message(@NotNull @NonNls @PropertyKey(resourceBundle = BUNDLE) final String key,
	                             @NotNull final Object... params) {
		return CommonBundle.message(getBundle(), key, params);
	}
	
	/**
	 * Gets the resource bundle and stores it in this class.
	 *
	 * @return the properties file
	 */
	private static ResourceBundle getBundle() {
		ResourceBundle ret = SoftReference.dereference(bundle);
		if (ret == null) {
			ret = ResourceBundle.getBundle(BUNDLE);
			bundle = new java.lang.ref.SoftReference<>(ret);
		}
		
		return ret;
	}
}
