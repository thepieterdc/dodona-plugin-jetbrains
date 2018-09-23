/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.plugin.code.identifiers.getter.impl;

import java.util.regex.Pattern;

/**
 * Identifies the current exercise from the file.
 */
public class StructuredExerciseIdentifierGetter extends RegexExerciseIdentifierGetter {


	private static final Pattern courseRegex =
		Pattern.compile("dodona:?.*course\\s*(\\d+).*", Pattern.CASE_INSENSITIVE);

	private static final Pattern seriesRegex =
		Pattern.compile("dodona:?.*series\\s*(\\d+).*", Pattern.CASE_INSENSITIVE);

	private static final Pattern exerciseRegex =
		Pattern.compile("dodona:?.*exercise\\s*(\\d+).*", Pattern.CASE_INSENSITIVE);

	public StructuredExerciseIdentifierGetter() {
		super(courseRegex, seriesRegex, exerciseRegex);
	}
}
