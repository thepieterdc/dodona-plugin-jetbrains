package be.ugent.piedcler.dodona.code.ExerciseIdenifierImpl;

import be.ugent.piedcler.dodona.code.ExerciseIdentifier;
import be.ugent.piedcler.dodona.dto.Solution;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class CombinedExerciseIdentifier implements ExerciseIdentifier {

	private List<ExerciseIdentifier> identifierList;

	public CombinedExerciseIdentifier(ExerciseIdentifier... identifiers) {
		this.identifierList = Arrays.asList(identifiers);
	}

	public CombinedExerciseIdentifier registerIdentifier(ExerciseIdentifier identifier) {
		identifierList.add(identifier);
		return this;
	}

	public CombinedExerciseIdentifier unregisterIdentifier(ExerciseIdentifier identifier) {
		identifierList.remove(identifier);
		return this;
	}

	@Override
	public Optional<Solution> identify(@NotNull CharSequence code) {
		for (ExerciseIdentifier exerciseIdentifier : identifierList) {
			Optional<Solution> identify = exerciseIdentifier.identify(code);
			if (identify.isPresent()) {
				return identify;
			}
		}
		return Optional.empty();
	}
}
