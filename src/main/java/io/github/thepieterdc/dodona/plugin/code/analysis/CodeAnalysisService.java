/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.code.analysis;

import com.intellij.codeInsight.CodeSmellInfo;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

import javax.annotation.Nonnull;
import java.util.Collection;

/**
 * Service that sets the analysis the code.
 */
public interface CodeAnalysisService {
	/**
	 * Gets the errors in the given file.
	 *
	 * @param file input file
	 * @return the errors
	 */
	@Nonnull
	Collection<CodeSmellInfo> errors(VirtualFile file);
	
	/**
	 * Gets an instance of the CodeAnalysisService.
	 *
	 * @param project current project
	 * @return the instance
	 */
	@Nonnull
	static CodeAnalysisService getInstance(final Project project) {
		return project.getService(CodeAnalysisService.class);
	}
}
