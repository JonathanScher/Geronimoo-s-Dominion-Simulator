package be.aga.dominionSimulator.gai.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.uncommons.watchmaker.framework.factories.AbstractCandidateFactory;

import be.aga.dominionSimulator.DomBuyRule;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.gai.BuyStrategy;

public class BuyRuleListFactory extends
		AbstractCandidateFactory<List<DomBuyRule>> {

	private static final int MAX_GENOME_SIZE = 10;
	BuyConditionFactory buyConditionFactory;
	CardNameFactory cardNameFactory;

	public BuyRuleListFactory(List<DomCardName> realm) {
		buyConditionFactory = new BuyConditionFactory(realm);
		cardNameFactory = new CardNameFactory(realm);
	}

	@Override
	public List<DomBuyRule> generateRandomCandidate(Random rng) {
		BuyStrategy buyRules = new BuyStrategy();
		Integer maxSize = rng.nextInt(MAX_GENOME_SIZE);
		for (int i = 0; i < maxSize; i++) {
			buyRules.add(generateGene(rng));
		}
		return buyRules;
	}

	public DomBuyRule generateGene(Random rng) {
		DomBuyRule rule = new DomBuyRule(
				cardNameFactory.generateRandomCardName(new Random(System
						.currentTimeMillis())));
		rule.addCondition(buyConditionFactory
				.generateRandomBuyCondition(rng));
		return rule;
	}
}
