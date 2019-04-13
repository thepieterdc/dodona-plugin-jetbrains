/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.identification.impl;

import java.util.regex.Pattern;

/**
 * Performs identification by matching the url contained in the exercise.
 */
public class UrlIdentificationService extends RegexIdentificationService {
	private static final Pattern courseRegex =
		Pattern.compile("https?://.*/courses/(\\d+)", Pattern.CASE_INSENSITIVE);
	
	private static final Pattern exerciseRegex =
		Pattern.compile("https?://.*/exercises/(\\d+)", Pattern.CASE_INSENSITIVE);
	
	private static final Pattern seriesRegex =
		Pattern.compile("https?://.*/series/(\\d+)", Pattern.CASE_INSENSITIVE);
	
	/**
	 * UrlIdentificationParser constructor.
	 */
	public UrlIdentificationService() {
		super(courseRegex, seriesRegex, exerciseRegex);
	}
}
