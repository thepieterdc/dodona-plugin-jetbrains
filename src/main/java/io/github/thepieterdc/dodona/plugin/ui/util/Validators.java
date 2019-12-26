/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.ui.util;

import com.intellij.openapi.ui.ValidationInfo;

import javax.annotation.Nullable;
import javax.swing.*;

/**
 * Custom validators.
 */
public enum Validators {
	;
	
	/**
	 * Validates that an input field is not empty.
	 *
	 * @param field   the input field
	 * @param message error message
	 * @return validationinfo if the field is empty, null if valid
	 */
	@Nullable
	public static ValidationInfo notEmpty(final JTextField field, final String message) {
		return field.getText().isEmpty() ? new ValidationInfo(message, field) : null;
	}
}
