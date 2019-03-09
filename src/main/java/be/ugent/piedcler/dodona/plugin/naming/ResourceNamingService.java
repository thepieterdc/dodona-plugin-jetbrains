/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.naming;

import be.ugent.piedcler.dodona.resources.Resource;

import javax.annotation.Nonnull;

/**
 * A naming service for resources.
 *
 * @param <T> type class of the resource
 */
public interface ResourceNamingService<T extends Resource> {
	/**
	 * Gets a name for the given resource.
	 *
	 * @param resource the resource to name
	 * @return name
	 */
	@Nonnull
	String getName(@Nonnull T resource);
}
