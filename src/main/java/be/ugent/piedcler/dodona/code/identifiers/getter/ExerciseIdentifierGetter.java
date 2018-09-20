package be.ugent.piedcler.dodona.code.identifiers.getter;

import be.ugent.piedcler.dodona.dto.Solution;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public interface ExerciseIdentifierGetter {

	/**
	 * Identifies the current exercise given some code.
	 *
	 * @param code the code to process
	 * @return the exercise found
	 */
	Optional<Solution> identify(@NotNull final CharSequence code);
}
