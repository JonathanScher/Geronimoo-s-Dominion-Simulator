package be.aga.dominionSimulator.gai.factory;

import java.util.Random;

import be.aga.dominionSimulator.enums.DomCardType;

public class CardTypeFactory {
	public static final CardTypeFactory INSTANCE = new CardTypeFactory();
	private static final DomCardType[] VALUES = DomCardType.values();

	private CardTypeFactory() {
	}

	public DomCardType generateRandomCardType(Random rand) {
		return VALUES[rand.nextInt(VALUES.length - 1)];
	}

}
