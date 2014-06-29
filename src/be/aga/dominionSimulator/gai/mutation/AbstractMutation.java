package be.aga.dominionSimulator.gai.mutation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.uncommons.maths.random.Probability;
import org.uncommons.watchmaker.framework.EvolutionaryOperator;

import be.aga.dominionSimulator.DomBuyRule;
import be.aga.dominionSimulator.gai.BuyStrategy;

public abstract class AbstractMutation implements
		EvolutionaryOperator<List<DomBuyRule>> {

	private Probability probability;

	protected AbstractMutation(Probability probability) {
		this.probability = probability;
	}

	@Override
	public List<List<DomBuyRule>> apply(
			List<List<DomBuyRule>> selectedCandidates, Random rng) {
		List<List<DomBuyRule>> mutants = new ArrayList<List<DomBuyRule>>();
		for(List<DomBuyRule> candidate: selectedCandidates) {
			BuyStrategy mutant = new BuyStrategy(candidate);
			mutants.add(mutant);
		}
			
			
		mutants.parallelStream().forEach(x -> {
			if (probability.nextEvent(rng)) {
				mutate(x, rng);
			}
		});
		return mutants;
	}

	public abstract void mutate(List<DomBuyRule> element, Random rng);
}
