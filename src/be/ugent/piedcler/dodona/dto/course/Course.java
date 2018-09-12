/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.dto.course;

import be.ugent.piedcler.dodona.Configuration;
import be.ugent.piedcler.dodona.dto.Resource;
import org.jetbrains.annotations.NonNls;

/**
 * A course on Dodona.
 */
public interface Course extends Resource {
	@NonNls
	String ENDPOINT_ID = Configuration.DODONA_URL + "/courses/%d";
	
	@Override
	String toString();
	
	@Override
	default String getUrl() {
		return String.format(Course.ENDPOINT_ID, this.getId());
	}
}
