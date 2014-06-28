package be.aga.dominionSimulator.gai.factory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import be.aga.dominionSimulator.enums.DomBotFunction;

public class BotFunctionFactory {

	public static final BotFunctionFactory INSTANCE = new BotFunctionFactory();
	
	private static final DomBotFunction[] VALUES = DomBotFunction.values();
	private static final List<DomBotFunction> RIGHT_VALUES;
	private static final Integer MAX_INDEX = VALUES.length - 1;
	private static final Integer MAX_RIGHT_INDEX;
	private BotFunctionFactory(){}
	
	static{
		//Right values are 50% chance to be a constant, and 0% chance to be a isActionPhase
		RIGHT_VALUES = new ArrayList<DomBotFunction>();
		RIGHT_VALUES.addAll(Arrays.asList( DomBotFunction.values()));
		RIGHT_VALUES.remove(DomBotFunction.isActionPhase);
		int size = RIGHT_VALUES.size();
		for (int i=0;i<size/2;i++){
			RIGHT_VALUES.add(DomBotFunction.constant);
		}
		MAX_RIGHT_INDEX = RIGHT_VALUES.size() - 1;
	}
	
	public DomBotFunction generateRandomBotFunction(Random rand) {
		return VALUES[rand.nextInt(MAX_INDEX)];
	}

	public DomBotFunction generateRightBotFunction(Random rand) {
		return RIGHT_VALUES.get(rand.nextInt(MAX_RIGHT_INDEX));
	}
	
}
