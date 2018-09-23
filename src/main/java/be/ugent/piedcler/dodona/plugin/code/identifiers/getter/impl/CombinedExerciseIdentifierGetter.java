/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.plugin.code.identifiers.getter.impl;

import be.ugent.piedcler.dodona.apiclient.responses.Solution;
import be.ugent.piedcler.dodona.plugin.code.identifiers.getter.ExerciseIdentifierGetter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class CombinedExerciseIdentifierGetter implements ExerciseIdentifierGetter {

	private List<ExerciseIdentifierGetter> identifierList;

	public CombinedExerciseIdentifierGetter(ExerciseIdentifierGetter... identifiers) {
		this.identifierList = new ArrayList<>(Arrays.asList(identifiers));
	}

	public CombinedExerciseIdentifierGetter registerIdentifier(ExerciseIdentifierGetter identifier) {
		identifierList.add(identifier);
		return this;
	}

	public CombinedExerciseIdentifierGetter unregisterIdentifier(ExerciseIdentifierGetter identifier) {
		identifierList.remove(identifier);
		return this;
	}

	@Override
	public Optional<Solution> identify(@NotNull CharSequence code) {
		for (ExerciseIdentifierGetter exerciseIdentifierGetter : identifierList) {
			Optional<Solution> identify = exerciseIdentifierGetter.identify(code);
			if (identify.isPresent()) {
				return identify;
			}
		}
		return Optional.empty();
	}
}
