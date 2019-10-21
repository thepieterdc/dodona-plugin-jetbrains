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
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Default implementation of a CodeIdentificationService.
 */
public class CodeIdentificationServiceImpl implements CodeIdentificationService {
	private static final Map<String, CodeIdentifier> identifiers = Arrays
		.stream(CodeIdentifier.values())
		.collect(Collectors.toMap(CodeIdentifier::getFileExtension, Function.identity()));
	
	@Nonnull
	@Override
	public CodeIdentifier getIdentifier(final String fileName) {
		final String extension = FileUtilRt.getExtension(fileName)
			.toLowerCase(Locale.getDefault());
		
		return Optional.of(extension)
			.map(identifiers::get)
			.orElse(CodeIdentifier.JAVA);
	}
}
