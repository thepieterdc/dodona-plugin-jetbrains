/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.ui.resources;

import io.github.thepieterdc.dodona.resources.Resource;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * A selection element for resources.
 *
 * @param <T> type of the resource
 */
public interface ResourceSelector<T extends Resource> {
	/**
	 * Gets the selected resource, empty if no resource is selected.
	 *
	 * @return the selected resource
	 */
	@Nonnull
	Optional<T> getSelectedResource();
	
	/**
	 * Sets the resources the user can choose from.
	 *
	 * @param resources the resources
	 */
	void setResources(Collection<? extends T> resources);
	
	/**
	 * Sets the selected resource to the resource with the given id.
	 *
	 * @param id the id
	 */
	void setSelectedResource(long id);
	
	/**
	 * Sets the selected resource to the first one that matches the predicate.
	 *
	 * @param predicate the predicate to match
	 */
	void setSelectedResource(Predicate<? super T> predicate);
	
	/**
	 * Sets the selected resource.
	 *
	 * @param resource the selected resource
	 */
	void setSelectedResource(@Nullable T resource);
}
