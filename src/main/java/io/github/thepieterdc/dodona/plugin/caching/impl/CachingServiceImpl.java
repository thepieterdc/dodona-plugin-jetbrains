/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.caching.impl;

import io.github.thepieterdc.dodona.plugin.api.DodonaExecutor;
import io.github.thepieterdc.dodona.plugin.authentication.DodonaAuthenticator;
import io.github.thepieterdc.dodona.plugin.caching.CachingService;

import java.util.HashMap;
import java.util.Map;

/**
 * Default implementation of a caching service.
 */
public class CachingServiceImpl implements CachingService {
	private final DodonaExecutor executor;
	
	private final Map<Long, String> courseNames;
	private final Map<Long, String> exerciseNames;
	
	/**
	 * CachingServiceImpl constructor.
	 */
	public CachingServiceImpl() {
		this.executor = DodonaAuthenticator.getInstance().getExecutor();
		
		this.courseNames = new HashMap<>(10);
		this.exerciseNames = new HashMap<>(10);
	}
}
