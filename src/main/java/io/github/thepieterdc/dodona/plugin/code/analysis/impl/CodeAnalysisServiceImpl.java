/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.code.analysis.impl;

import com.intellij.codeInsight.CodeSmellInfo;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.CodeSmellDetector;
import com.intellij.openapi.vfs.VirtualFile;
import io.github.thepieterdc.dodona.plugin.code.analysis.CodeAnalysisService;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * Implementation of CodeAnalysisService.
 */
public class CodeAnalysisServiceImpl implements CodeAnalysisService {
	private final Project project;
	
	/**
	 * CodeAnalysisServiceImpl constructor.
	 *
	 * @param project the current project
	 */
	public CodeAnalysisServiceImpl(final Project project) {
		this.project = project;
	}
	
	@Nonnull
	@Override
	public Collection<CodeSmellInfo> errors(final VirtualFile file) {
		return CodeSmellDetector.getInstance(this.project)
			.findCodeSmells(Collections.singletonList(file))
			.stream()
			.filter(info -> info.getSeverity().equals(HighlightSeverity.ERROR))
			.collect(Collectors.toSet());
	}
}
