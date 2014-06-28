package be.aga.dominionSimulator.gai.mutation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.uncommons.watchmaker.framework.EvolutionaryOperator;

import be.aga.dominionSimulator.DomBuyRule;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.gai.factory.BuyRuleListFactory;

public class AddGene implements EvolutionaryOperator<List<DomBuyRule>> {

	private BuyRuleListFactory ruleListFactory;

	public AddGene(List<DomCardName> realm) {
		ruleListFactory = new BuyRuleListFactory(realm);
	}

	@Override
	public List<List<DomBuyRule>> apply(
			List<List<DomBuyRule>> selectedCandidates, Random rng) {
		List<List<DomBuyRule>> mutants = new ArrayList<List<DomBuyRule>>(
				selectedCandidates);
		mutants.parallelStream().forEach(x -> mutate(x, rng));
		return mutants;
	}

	public void mutate(List<DomBuyRule> element, Random rng) {
		DomBuyRule gene = ruleListFactory.generateGene(rng);
		element.add(rng.nextInt(element.size()), gene);
	}

}
