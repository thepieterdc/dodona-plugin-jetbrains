/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.code.identification.parsers;

import java.util.regex.Pattern;

/**
 * Parses identifications from urls.
 */
public class UrlIdentificationParser extends RegexIdentificationParser {
	private static final Pattern courseRegex =
		Pattern.compile("https?://.*/courses/(\\d+)", Pattern.CASE_INSENSITIVE);
	
	private static final Pattern exerciseRegex =
		Pattern.compile("https?://.*/exercises/(\\d+)", Pattern.CASE_INSENSITIVE);
	
	private static final Pattern seriesRegex =
		Pattern.compile("https?://.*/series/(\\d+)", Pattern.CASE_INSENSITIVE);
	
	/**
	 * UrlIdentificationParser constructor.
	 */
	public UrlIdentificationParser() {
		super(courseRegex, seriesRegex, exerciseRegex);
	}
}
