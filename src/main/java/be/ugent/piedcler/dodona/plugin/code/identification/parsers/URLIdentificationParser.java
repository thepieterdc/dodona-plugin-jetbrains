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
public class URLIdentificationParser extends RegexIdentificationParser {
	private static final Pattern courseRegex =
		Pattern.compile("https?://.*/courses/(\\d+)", Pattern.CASE_INSENSITIVE);
	
	private static final Pattern seriesRegex =
		Pattern.compile("https?://.*/series/(\\d+)", Pattern.CASE_INSENSITIVE);
	
	private static final Pattern exerciseRegex =
		Pattern.compile("https?://.*/exercises/(\\d+)", Pattern.CASE_INSENSITIVE);
	
	/**
	 * URLIdentificationParser constructor.
	 */
	public URLIdentificationParser() {
		super(courseRegex, seriesRegex, exerciseRegex);
	}
}
