package be.aga.dominionSimulator.gai.mutation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.uncommons.watchmaker.framework.EvolutionaryOperator;
/**
 * Mutation that removes one random element of the list
 * @author Jonathan Scher
 *
 * @param <T>
 */
public class RemoveGene<T> implements EvolutionaryOperator<List<T>> {

	@Override
	public List<List<T>> apply(List<List<T>> selectedCandidates, Random rng) {
		List<List<T>> newGuys = new ArrayList<List<T>>(selectedCandidates);
		newGuys.parallelStream().forEach(x->mutate(x, rng));
		return newGuys;
	}

	public void mutate(List<T> element, Random rng) {
		Integer size = element.size();
		element.remove(rng.nextInt(size-1));
	}
}
