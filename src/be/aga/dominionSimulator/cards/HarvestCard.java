package be.aga.dominionSimulator.cards;

import java.util.ArrayList;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.LogHandler;
import be.aga.dominionSimulator.enums.DomCardName;

public class HarvestCard extends DomCard {
    public HarvestCard () {
      super( DomCardName.Harvest);
    }

    public void play(LogHandler logHandler) {
      ArrayList< DomCard > theCards = owner.revealTopCards(4);
  	  ArrayList<DomCardName> theSingleCards = new ArrayList<DomCardName>();
      for (DomCard card : theCards) {
		if (!theSingleCards.contains(card.getName())){
    		theSingleCards.add(card.getName());
    	}
      }
      owner.addAvailableCoins(theSingleCards.size());
      owner.discard(theCards);
    }
}