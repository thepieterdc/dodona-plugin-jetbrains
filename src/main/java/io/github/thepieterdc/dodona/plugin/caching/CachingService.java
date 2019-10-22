/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */
package io.github.thepieterdc.dodona.plugin.caching;

import com.intellij.openapi.components.ServiceManager;

import javax.annotation.Nonnull;

/**
 * Renders feedback about submissions.
 */
public interface CachingService {
	/**
	 * Gets an instance of the CachingService.
	 *
	 * @return the instance
	 */
	@Nonnull
	static CachingService getInstance() {
		return ServiceManager.getService(CachingService.class);
	}
}
