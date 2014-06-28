package be.aga.dominionSimulator.gai.factory;

import java.util.List;
import java.util.Random;

import be.aga.dominionSimulator.Operand;
import be.aga.dominionSimulator.enums.DomBotFunction;
import be.aga.dominionSimulator.enums.DomCardName;

public class OperandFactory {
	// TODO tweak the max constant to change depending on the left hand
	// operand's type
	private static final int MAX_CONSTANT_NUMBER = 12;
	protected BotFunctionFactory functionFactory = BotFunctionFactory.INSTANCE;
	protected CardNameFactory cardNameFactory;
	protected CardTypeFactory cardTypeFactory = CardTypeFactory.INSTANCE;

	public OperandFactory(List<DomCardName> deck) {
		cardNameFactory = new CardNameFactory(deck);
	}

	// TODO generate RightOperand: Higher chance to get a constant, and no
	// isActionPhase
	public Operand generateRandomOperand(Random rand, boolean isRightHand) {
		Operand operand = new Operand();
		DomBotFunction function;

		if (isRightHand) {
			function = functionFactory.generateRightBotFunction(rand);
		} else {
			function = functionFactory.generateRandomBotFunction(rand);
		}
		operand.setFunction(function);

		if (DomBotFunction.countCardTypeInDeck.equals(function)) {
			operand.setCardType(cardTypeFactory.generateRandomCardType(rand));
		} else if (DomBotFunction.countCardsInDeck.equals(function)
				|| DomBotFunction.countCardsInSupply.equals(function)
				|| DomBotFunction.countCardsInHand.equals(function)
				|| DomBotFunction.countCardsInPlay.equals(function)
				|| DomBotFunction.countCardsInOpponentsDecks.equals(function)) {
			operand.setCardName(cardNameFactory.generateRandomCardName(rand));
		} else if (DomBotFunction.constant.equals(function)) {
			operand.setValue(rand.nextInt(MAX_CONSTANT_NUMBER));
		}

		return operand;
	}
}
