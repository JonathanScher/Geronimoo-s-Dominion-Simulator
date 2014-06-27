package be.aga.dominionSimulator;

import static org.junit.Assert.*;

import org.junit.Test;

import be.aga.dominionSimulator.cards.CopperCard;
import be.aga.dominionSimulator.cards.Silk_RoadCard;
import be.aga.dominionSimulator.cards.WharfCard;

public class DomCardTest {

	@Test
	public void twoCoppersAreEquals() {
		//G
		CopperCard card1 = new CopperCard();
		CopperCard card2 = new CopperCard();
		//W
		//T
		assertEquals(card1, card2);
		assertEquals(card1.hashCode(), card2.hashCode());
	}
	@Test
	public void coppersAndSliverAreNotEquals() {
		//G
		DomCard card1 = new CopperCard();
		DomCard card2 = new WharfCard();
		//W
		//T
		assertNotEquals(card1.hashCode(), card2.hashCode());
	}
	

}
