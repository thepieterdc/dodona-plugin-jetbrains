package be.ugent.piedcler.dodona.code.identifiers.getter.impl;

import java.util.regex.Pattern;

public class URLExerciseIdentifierGetter extends RegexExerciseIdentifierGetter {

	private static final int GROUP_COURSE = 1;
	private static final int GROUP_EXERCISE = 2;

	private static final Pattern regex =
		Pattern.compile("https?://[^/]*/[^/]*/courses/\\D*(\\d+)/exercises/\\D*(\\d+)/?", Pattern.CASE_INSENSITIVE);

	public URLExerciseIdentifierGetter() {
		super(regex, GROUP_COURSE, GROUP_EXERCISE);
	}
}
