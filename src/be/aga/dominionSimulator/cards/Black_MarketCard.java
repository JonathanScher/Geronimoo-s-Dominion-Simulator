package be.aga.dominionSimulator.cards;

import java.util.ArrayList;
import java.util.Collections;

import org.apache.log4j.Logger;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.LogHandler;
import be.aga.dominionSimulator.enums.DomCardName;

public class Black_MarketCard extends DomCard {
	protected static final Logger LOGGER = Logger
			.getLogger(Black_MarketCard.class);

	public Black_MarketCard() {
		super(DomCardName.Black_Market);
	}

	public void play(LogHandler logHandler) {
		owner.addAvailableCoins(2);
		ArrayList<DomCard> theRevealedCards = owner.getCurrentGame()
				.revealFromBlackMarketDeck();
		if (logHandler.getHaveToLog())
			logHandler.addToLog(owner + " reveals " + theRevealedCards);
		if (theRevealedCards.isEmpty())
			return;
		owner.playTreasures();
		Collections.sort(theRevealedCards, SORT_FOR_TRASHING);
		DomCost theAvailableCurrency = owner.getTotalAvailableCurrency();
		DomCardName theDesiredCardName = owner.getDesiredCard(
				theAvailableCurrency, false);
		for (int i = theRevealedCards.size() - 1; i >= 0; i--) {
			DomCard theCard = theRevealedCards.get(i);
			DomCost theCardCost = theCard.getName().getCost(
					owner.getCurrentGame());
			if (theAvailableCurrency.compareTo(theCardCost) >= 0
					&& theDesiredCardName == null) {
				owner.buy(theRevealedCards.remove(i));
				break;
			}
		}
		if (logHandler.getHaveToLog())
			logHandler.addToLog(owner + " returns " + theRevealedCards
					+ " to the Black Market Deck");
		for (int i = theRevealedCards.size() - 1; i >= 0; i--) {
			DomCard theCard = theRevealedCards.get(i);
			owner.getCurrentGame().returnToBlackMarketDeck(theCard);
		}
	}
}