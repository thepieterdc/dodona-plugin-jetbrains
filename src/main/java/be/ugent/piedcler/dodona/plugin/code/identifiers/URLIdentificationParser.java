/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.plugin.code.identifiers;

import java.util.regex.Pattern;

public class URLIdentificationParser extends RegexExerciseIdentifierGetter {

	private static final Pattern courseRegex =
		Pattern.compile("https?://.*/courses/(\\d+)", Pattern.CASE_INSENSITIVE);

	private static final Pattern seriesRegex =
		Pattern.compile("https?://.*/series/(\\d+)", Pattern.CASE_INSENSITIVE);

	private static final Pattern exerciseRegex =
		Pattern.compile("https?://.*/exercises/(\\d+)", Pattern.CASE_INSENSITIVE);

	public URLIdentificationParser() {
		super(courseRegex, seriesRegex, exerciseRegex);
	}
}
