/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.code.identification.impl;

import com.intellij.openapi.util.io.FileUtilRt;
import io.github.thepieterdc.dodona.plugin.code.identification.CodeIdentificationService;
import io.github.thepieterdc.dodona.plugin.code.identification.CodeIdentifier;

import javax.annotation.Nonnull;
import java.util.Locale;

/**
 * Default implementation of a CodeIdentificationService.
 */
public class CodeIdentificationServiceImpl implements CodeIdentificationService {
	@Nonnull
	@Override
	public CodeIdentifier getIdentifier(final String fileName) {
		final String extension = FileUtilRt.getExtension(fileName)
			.toLowerCase(Locale.getDefault());
		
		return CodeIdentifier.getForExtension(extension)
			.orElse(CodeIdentifier.JAVA);
	}
}
