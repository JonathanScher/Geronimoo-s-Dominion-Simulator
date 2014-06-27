package be.aga.dominionSimulator.gai.factory;

import java.util.Random;

import be.aga.dominionSimulator.enums.DomBotFunction;

public class BotFunctionFactory {

	public static final BotFunctionFactory INSTANCE = new BotFunctionFactory();
	
	private static final DomBotFunction[] VALUES = DomBotFunction.values();
	private static final Integer MAX_INDEX = VALUES.length - 1;

	private BotFunctionFactory(){}
	
	public DomBotFunction generateRandomBotFunction(Random rand) {
		return VALUES[rand.nextInt(MAX_INDEX)];
	}

}
