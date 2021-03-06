package be.aga.dominionSimulator.cards;

import java.util.ArrayList;
import java.util.Collections;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.LogHandler;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class CourtyardCard extends DomCard {
    public CourtyardCard () {
      super( DomCardName.Courtyard);
    }

    public void play(LogHandler logHandler) {
    	owner.drawCards(3);
    	if (owner.getCardsInHand().isEmpty()) {
  	      if (logHandler.getHaveToLog()) 
            logHandler.addToLog( owner + "'s hand is empty, so returns nothing");
          return;
    	}
    	Collections.sort(owner.getCardsInHand(), SORT_FOR_DISCARD_FROM_HAND);
    	DomCard theCardToReturn = null;
    	ArrayList<DomCard> theCardsInHand = owner.getCardsInHand();
     	for (int i=theCardsInHand.size()-1;i>=0;i--){
      	  theCardToReturn = theCardsInHand.get(i);
      	  if (!owner.removingReducesBuyingPower(theCardToReturn)) {
      		  break;
      	  }
        }
     	if (theCardsInHand.get(0).hasCardType(DomCardType.Action))
     		theCardToReturn=theCardsInHand.get(0);
     	owner.putOnTopOfDeck(owner.removeCardFromHand(theCardToReturn));
    }
    
    @Override
    public boolean wantsToBePlayed() {
      return !owner.isDeckEmpty();
    }
}