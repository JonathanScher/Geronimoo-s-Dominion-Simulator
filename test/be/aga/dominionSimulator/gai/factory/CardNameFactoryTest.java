package be.aga.dominionSimulator.gai.factory;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import be.aga.dominionSimulator.enums.DomCardName;

public class CardNameFactoryTest {

	List<DomCardName> cards = new ArrayList<DomCardName>();
	CardNameFactory factory = new CardNameFactory(new ArrayList<>());
	Random rand = mock(Random.class);

	@Before
	public void init(){
		factory.setDeck(cards);
	}
	
	@Test
	public void justACopper() {
		// Given
		cards.add(DomCardName.Copper);
		// When
		DomCardName card = factory.generateRandomCardName(rand);
		//Then
		DomCardName expected = DomCardName.Copper;
		assertEquals(expected, card);
	}

	@Test
	public void justASilver() {
		// Given
		cards.add(DomCardName.Silver);
		// When
		DomCardName card = factory.generateRandomCardName(rand);
		//Then
		DomCardName expected = DomCardName.Silver;
		assertEquals(expected, card);
	}
	
	@Test
	public void silverAndCopper() {
		// Given
		cards.add(DomCardName.Copper);
		cards.add(DomCardName.Silver);
		when(rand.nextInt(1)).thenReturn(1);
		// When
		DomCardName card = factory.generateRandomCardName(rand);
		//Then
		DomCardName expected = DomCardName.Silver;
		assertEquals(expected, card);
	}
	@Test
	public void emptyDeck() {
		// Given
		when(rand.nextInt(-1)).thenReturn(0);
		// When
		DomCardName card = factory.generateRandomCardName(rand);
		//Then
		DomCardName expected = null;
		assertEquals(expected, card);
	}
}
