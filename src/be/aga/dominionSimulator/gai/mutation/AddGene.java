package be.aga.dominionSimulator.gai.mutation;

import java.util.List;
import java.util.Random;

import org.uncommons.maths.random.Probability;

import be.aga.dominionSimulator.DomBuyRule;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.gai.factory.BuyRuleListFactory;

public class AddGene extends AbstractMutation {

	private BuyRuleListFactory ruleListFactory;

	public AddGene(List<DomCardName> realm, Probability probability) {
		super(probability);
		ruleListFactory = new BuyRuleListFactory(realm);
	}

	public void mutate(List<DomBuyRule> element, Random rng) {
		DomBuyRule gene = ruleListFactory.generateGene(rng);
		Integer position = 0;
		if (element.size() > 1) {
			position = rng.nextInt(element.size());
		}
		element.add(position, gene);
	}

}
