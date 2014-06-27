package be.aga.dominionSimulator.gai.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.uncommons.watchmaker.framework.factories.AbstractCandidateFactory;

import be.aga.dominionSimulator.DomBuyRule;
import be.aga.dominionSimulator.enums.DomCardName;

public class BuyRuleListFactory extends
		AbstractCandidateFactory<List<DomBuyRule>> {

	private static final int MAX_GENOME_SIZE = 10;
	BuyConditionFactory buyConditionFactory;
	CardNameFactory cardNameFactory;

	public BuyRuleListFactory(List<DomCardName> deck) {
		buyConditionFactory = new BuyConditionFactory(deck);
		cardNameFactory = new CardNameFactory(deck);
	}

	@Override
	public List<DomBuyRule> generateRandomCandidate(Random rng) {
		List<DomBuyRule> buyRules = new ArrayList<DomBuyRule>();
		Integer maxSize = rng.nextInt(MAX_GENOME_SIZE);
		for (int i = 0; i < maxSize; i++) {
			DomBuyRule rule = new DomBuyRule(
					cardNameFactory.generateRandomCardName(new Random(System
							.currentTimeMillis())));
			rule.addCondition(buyConditionFactory
					.generateRandomBuyCondition(rng));
			buyRules.add(rule);
		}
		return buyRules;
	}
}
