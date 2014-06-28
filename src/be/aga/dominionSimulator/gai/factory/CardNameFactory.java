package be.aga.dominionSimulator.gai.factory;

import java.util.List;
import java.util.Random;

import be.aga.dominionSimulator.enums.DomCardName;

public class CardNameFactory {
	private List<DomCardName> realm;

	public CardNameFactory(List<DomCardName> realm) {
		this.realm = realm;
	}

	public DomCardName generateRandomCardName(Random rand) {
		DomCardName generated = null;
		if (!realm.isEmpty()) {
			generated = realm.get(rand.nextInt(realm.size() - 1));
		}
		return generated;
	}

	public void setDeck(List<DomCardName> deck) {
		this.realm = deck;
	}
}
