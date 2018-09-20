package be.ugent.piedcler.dodona.code.identifiers.getter.impl;

import java.util.regex.Pattern;

public class URLExerciseIdentifierGetter extends RegexExerciseIdentifierGetter {

	//private static final Pattern regex =
	//	Pattern.compile("https?://[^/]*/[^/]*(/courses/\\D*(\\d+))?(/series/\\D*(\\d+))?/exercises/\\D*(\\d+)/?", Pattern.CASE_INSENSITIVE);

	private static final Pattern courseRegex =
		Pattern.compile("https?://.*/courses/(\\d+)", Pattern.CASE_INSENSITIVE);

	private static final Pattern seriesRegex =
		Pattern.compile("https?://.*/series/(\\d+)", Pattern.CASE_INSENSITIVE);

	private static final Pattern exerciseRegex =
		Pattern.compile("https?://.*/exercises/(\\d+)", Pattern.CASE_INSENSITIVE);

	public URLExerciseIdentifierGetter() {
		super(courseRegex, seriesRegex, exerciseRegex);
	}
}
