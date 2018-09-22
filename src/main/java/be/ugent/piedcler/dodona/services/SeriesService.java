/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.services;

import be.ugent.piedcler.dodona.dto.Series;
import com.intellij.openapi.components.ServiceManager;

/**
 * Service for Series entities.
 */
public interface SeriesService extends ResourceService<Series> {
	/**
	 * Gets an instance of a SeriesService.
	 *
	 * @return singleton instance
	 */
	static SeriesService getInstance() {
		return ServiceManager.getService(SeriesService.class);
	}
}
