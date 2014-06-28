package be.aga.dominionSimulator.gai.factory;

import java.util.List;
import java.util.Random;

import be.aga.dominionSimulator.DomBuyCondition;
import be.aga.dominionSimulator.Operand;
import be.aga.dominionSimulator.enums.DomBotComparator;
import be.aga.dominionSimulator.enums.DomBotFunction;
import be.aga.dominionSimulator.enums.DomCardName;

public class BuyConditionFactory {

	BotComparatorFactory botComparatorFactory = BotComparatorFactory.INSTANCE;
	OperandFactory operandFactory;

	public BuyConditionFactory(List<DomCardName> deck) {
		operandFactory = new OperandFactory(deck);
	}

	public DomBuyCondition generateRandomBuyCondition(Random rand) {
		DomBuyCondition condition = new DomBuyCondition();
		condition.setLeft(operandFactory.generateRandomOperand(rand, false));

		// TODO handle more special cases and more special values
		if (!condition.getLeftFunction().equals(DomBotFunction.isActionPhase)) {
			condition.setComparator(botComparatorFactory
					.generateRandomDomBotComparator(rand));
			condition.setRight(operandFactory.generateRandomOperand(rand, true));
		} else {
			Operand right = new Operand();
			right.setFunction(DomBotFunction.constant);
			right.setValue(rand.nextInt(2));
			condition.setRight(right);
			condition.setComparator(DomBotComparator.equalTo);
		}

		return condition;
	}
}
