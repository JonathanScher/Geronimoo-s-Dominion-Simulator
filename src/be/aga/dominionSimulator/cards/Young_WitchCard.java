package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.LogHandler;
import be.aga.dominionSimulator.enums.DomCardName;

public class Young_WitchCard extends DomCard {
    public Young_WitchCard () {
      super( DomCardName.Young_Witch);
    }

    public void play(LogHandler logHandler) {
	  owner.drawCards(2);
	  owner.doForcedDiscard(2, false);
	  for (DomPlayer thePlayer : owner.getOpponents()) {
		if (thePlayer.checkDefense() ) 
	      return;
		for (DomCard card : thePlayer.getCardsInHand()){
	      if (card.isBane()) {
	        if (logHandler.getHaveToLog()) logHandler.addToLog( thePlayer + " reveals the Bane card "+card );
            return;
	      }
	    }  
		thePlayer.gain(DomCardName.Curse);
	  }
    }
}