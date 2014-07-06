package be.aga.dominionSimulator.cards;

import java.util.ArrayList;
import java.util.Collections;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.LogHandler;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class HavenCard extends DomCard {
    private DomCard myHavenedCard;

    public HavenCard () {
      super( DomCardName.Haven);
    }

    public void play(LogHandler logHandler) {
      owner.addActions(1);
      owner.drawCards(1);
      if (owner.getCardsInHand().isEmpty())
    	  return;
      //first look for excess terminals
      ArrayList< DomCard > theTerminalsInHand = owner.getCardsFromHand( DomCardType.Terminal );
      Collections.sort(theTerminalsInHand, DomCard.SORT_FOR_DISCARD_FROM_HAND);
      if (owner.getProbableActionsLeft()<0) {
        havenAway(theTerminalsInHand.get(0), logHandler);
		return;
      }
      //TODO now Haven will try to stow away the best treasure card, but more handling is probably needed
      Collections.sort(owner.getCardsInHand(),SORT_FOR_DISCARD_FROM_HAND);
      for (int i = owner.getCardsInHand().size()-1;i>0;i--) {
          DomCard theCardToHavenAway = owner.getCardsInHand().get( i );
          if (theCardToHavenAway.hasCardType(DomCardType.Treasure)
        		  && !owner.removingReducesBuyingPower( theCardToHavenAway )) {
            havenAway(theCardToHavenAway, logHandler );
            return;
          }
      }
      //nothing found to put away so put away the worst card
      havenAway(owner.getCardsInHand().get( 0 ), logHandler);
    }

	private void havenAway(DomCard aCard, LogHandler logHandler) {
		myHavenedCard=owner.removeCardFromHand( aCard);
        if (logHandler.getHaveToLog() ) logHandler.addToLog( owner + " puts " + myHavenedCard + " aside");
	}

    public void resolveDuration(LogHandler logHandler) {
      if (myHavenedCard!=null) {
    	owner.putInHand(myHavenedCard);
    	owner.showHand();
      } else {
        if (logHandler.getHaveToLog() ) logHandler.addToLog( owner + " adds nothing to his hand");
      }
      myHavenedCard=null;
    }
}