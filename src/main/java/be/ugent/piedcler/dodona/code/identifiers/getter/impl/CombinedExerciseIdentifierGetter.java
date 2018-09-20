package be.ugent.piedcler.dodona.code.identifiers.getter.impl;

import be.ugent.piedcler.dodona.code.identifiers.getter.ExerciseIdentifierGetter;
import be.ugent.piedcler.dodona.dto.Solution;
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
