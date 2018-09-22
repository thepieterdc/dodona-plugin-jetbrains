/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.services;

import be.ugent.piedcler.dodona.dto.Resource;

/**
 * Service for resources.
 */
@FunctionalInterface
public interface ResourceService<T extends Resource> {
	/**
	 * Gets the resource with id.
	 *
	 * @param id the id of the resource to fetch
	 * @return the resource
	 */
	T get(long id);
}
