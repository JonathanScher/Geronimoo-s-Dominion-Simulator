package be.aga.dominionSimulator.gai.factory;

import java.util.List;
import java.util.Random;

import be.aga.dominionSimulator.enums.DomCardName;

public class CardNameFactory {
	private List<DomCardName> deck;

	public CardNameFactory(List<DomCardName> deck) {
		this.deck = deck;
	}

	public DomCardName generateRandomCardName(Random rand) {
		DomCardName generated = null;
		if (!deck.isEmpty()) {
			generated = deck.get(rand.nextInt(deck.size() - 1));
		}
		return generated;
	}

	public void setDeck(List<DomCardName> deck) {
		this.deck = deck;
	}
}
