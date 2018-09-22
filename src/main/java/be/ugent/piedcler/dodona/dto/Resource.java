/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.dto;

/**
 * A resource on Dodona.
 */
public interface Resource {
	/**
	 * Gets the id of the resource.
	 *
	 * @return the id
	 */
	long getId();
	
	/**
	 * Gets the url to reach te resource.
	 *
	 * @return the url
	 */
	String getUrl();
}
