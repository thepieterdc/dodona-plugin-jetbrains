/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.services;

import be.ugent.piedcler.dodona.dto.Resource;

/**
 * Service for resources.
 */
public interface ResourceService<T extends Resource> {
	/**
	 * Gets the resource with id.
	 *
	 * @param id the id of the resource to fetch
	 * @return the resource, if it does not exist, an UnknownResource will be returned
	 */
	T get(long id);
}
