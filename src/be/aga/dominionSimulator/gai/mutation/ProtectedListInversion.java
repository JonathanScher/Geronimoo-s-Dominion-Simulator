package be.aga.dominionSimulator.gai.mutation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.uncommons.maths.number.ConstantGenerator;
import org.uncommons.maths.number.NumberGenerator;
import org.uncommons.maths.random.Probability;
import org.uncommons.watchmaker.framework.EvolutionaryOperator;

public class ProtectedListInversion<T> implements EvolutionaryOperator<List<T>> {
	private final NumberGenerator<Probability> inversionProbability;

	/**
	 * @param inversionProbability
	 *            The probability that an individual list will have some
	 *            subsection inverted.
	 */
	public ProtectedListInversion(Probability inversionProbability) {
		this(new ConstantGenerator<Probability>(inversionProbability));
	}

	/**
	 * @param inversionProbability
	 *            A variable that controls the probability that an individual
	 *            list will have some subsection inverted.
	 */
	public ProtectedListInversion(
			NumberGenerator<Probability> inversionProbability) {
		this.inversionProbability = inversionProbability;
	}

	public List<List<T>> apply(List<List<T>> selectedCandidates, Random rng) {
		List<List<T>> result = new ArrayList<List<T>>(selectedCandidates.size());
		for (List<T> candidate : selectedCandidates) {
			if (candidate.size() > 2
					&& inversionProbability.nextValue().nextEvent(rng)) {
				List<T> newCandidate = new ArrayList<T>(candidate);
				int length = newCandidate.size();
				int start = rng.nextInt(length);
				int offset = 2 + rng.nextInt(length - 2);
				int end = (start + offset) % length;
				int segmentLength = end - start;
				if (segmentLength < 0) {
					segmentLength += length;
				}
				for (int i = 0; i < segmentLength / 2; i++) {
					Collections.swap(newCandidate, (start + i) % length, (end
							- i + length)
							% length);
				}
				result.add(newCandidate);
			} else {
				result.add(candidate);
			}
		}
		return result;
	}
}
