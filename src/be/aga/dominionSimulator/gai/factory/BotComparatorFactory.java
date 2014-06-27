package be.aga.dominionSimulator.gai.factory;

import java.util.Random;

import be.aga.dominionSimulator.enums.DomBotComparator;

public class BotComparatorFactory {
	public static final BotComparatorFactory INSTANCE = new BotComparatorFactory();
	private static final DomBotComparator[] VALUES = DomBotComparator.values();

	private BotComparatorFactory() {
	}

	public DomBotComparator generateRandomDomBotComparator(Random rand) {
		return VALUES[rand.nextInt(VALUES.length - 1)];
	}

}
