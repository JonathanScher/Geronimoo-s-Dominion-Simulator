package be.aga.dominionSimulator.gai.factory;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import be.aga.dominionSimulator.Operand;
import be.aga.dominionSimulator.enums.DomBotFunction;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class OperandFactoryTest {

	List<DomCardName> deck = new ArrayList<DomCardName>();
	OperandFactory factory = new OperandFactory(deck);
	Random rand = mock(Random.class);

	@Before
	public void init() {
		factory.cardNameFactory = mock(CardNameFactory.class);
		factory.cardTypeFactory = mock(CardTypeFactory.class);
		factory.functionFactory = mock(BotFunctionFactory.class);
	}

	@Test
	public void randomisedFunction() {
		// G
		when(factory.functionFactory.generateRandomBotFunction(rand))
				.thenReturn(DomBotFunction.constant);
		// W
		Operand generated = factory.generateRandomOperand(rand, false);

		// T
		assertEquals(DomBotFunction.constant, generated.getFunction());
	}

	@Test
	public void ifCardTypeNeededAddOneToOperand() {
		// G
		when(factory.functionFactory.generateRandomBotFunction(rand))
				.thenReturn(DomBotFunction.countCardTypeInDeck);
		when(factory.cardTypeFactory.generateRandomCardType(rand)).thenReturn(
				DomCardType.Action);
		// W
		Operand generated = factory.generateRandomOperand(rand, false);

		// T
		assertEquals(DomCardType.Action, generated.getCardType());
	}

	@Test
	public void aConstantHasAValue() {
		// G
		when(factory.functionFactory.generateRandomBotFunction(rand))
				.thenReturn(DomBotFunction.constant);
		when(rand.nextInt(12)).thenReturn(3);
		// W
		Operand generated = factory.generateRandomOperand(rand, false);

		// T
		assertEquals(Double.valueOf(3), Double.valueOf(generated.getValue()));
	}

	@Test
	public void ifCardNameNeededAddOneToOperand() {
		// G
		DomBotFunction function = DomBotFunction.countCardsInDeck;
		testCardPresentForFunction(function);
	}

	@Test
	public void countCardsInSupply() {
		// G
		DomBotFunction function = DomBotFunction.countCardsInSupply;
		testCardPresentForFunction(function);
	}

	@Test
	public void countCardsInPlay() {
		// G
		DomBotFunction function = DomBotFunction.countCardsInPlay;
		testCardPresentForFunction(function);
	}

	@Test
	public void countCardsInHand() {
		// G
		DomBotFunction function = DomBotFunction.countCardsInHand;
		testCardPresentForFunction(function);
	}

	@Test
	public void countCardsInOpponentsDecks() {
		// G
		DomBotFunction function = DomBotFunction.countCardsInOpponentsDecks;
		testCardPresentForFunction(function);
	}

	private void testCardPresentForFunction(DomBotFunction function) {
		when(factory.functionFactory.generateRandomBotFunction(rand))
				.thenReturn(function);
		when(factory.cardNameFactory.generateRandomCardName(rand)).thenReturn(
				DomCardName.Adventurer);
		// W
		Operand generated = factory.generateRandomOperand(rand, false);

		// T
		assertEquals(DomCardName.Adventurer, generated.getCardName());
	}

}
