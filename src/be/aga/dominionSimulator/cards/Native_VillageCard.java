package be.aga.dominionSimulator.cards;

import java.util.ArrayList;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.LogHandler;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomPlayStrategy;

public class Native_VillageCard extends DomCard {
    public Native_VillageCard () {
      super( DomCardName.Native_Village);
    }

    public void play(LogHandler logHandler) {
      owner.addActions(2);
      switch (owner.getPlayStrategyFor(this)) {
        case standard:
          playDefault(logHandler);
          break;
        case bigTurnBridge:
        case bigTurnGoons:
          playForBigTurn(logHandler);
          break;
        case ApothecaryNativeVillage:
        	//always put card away because it's a useless green
        	playNativeVillageForStorage(logHandler);
      }    
    }

    private void playForBigTurn(LogHandler logHandler) {
      if (owner.getCardsFromHand( DomCardName.Native_Village ).size() > 0 
       || owner.getNativeVillageMat().isEmpty()
       || !isBigTurnReady()) {
        playNativeVillageForStorage(logHandler);
      } else {
        playNativeVillageForCards(logHandler);
      }
    }

    private boolean isBigTurnReady() {
        switch ( owner.getPlayStrategyFor( this ) ) {
        case bigTurnBridge :
            for (DomPlayer thePlayer : owner.getOpponents()) {
              if (thePlayer.countVictoryPoints()-owner.countVictoryPoints()>24) {
                return true;
              }
            }
            break;

        case bigTurnGoons:
            if (owner.countInDeck(DomCardName.Goons)>3) {
              return true;
            }
            break;

        default :
            break;
        }
        return false;
    }

    private void playDefault(LogHandler logHandler) {
      if (owner.addingThisIncreasesBuyingPower( owner.getPotentialCurrencyFromNativeVillageMat() )) {
        playNativeVillageForCards(logHandler);
      } else {
        playNativeVillageForStorage(logHandler);
      } 
    }

      public void playNativeVillageForStorage(LogHandler logHandler) {
        ArrayList< DomCard > theCard = owner.revealTopCards( 1 );
        if (!theCard.isEmpty()) {
          owner.getNativeVillageMat().addAll( theCard );
          if (logHandler.getHaveToLog()) logHandler.addToLog( owner + " adds a " + theCard.get( 0 ) +" to his Village Mat");
          if (logHandler.getHaveToLog()) logHandler.addToLog( owner + "'s Village Mat contains: " + owner.getNativeVillageMat());
        } else {
          if (logHandler.getHaveToLog()) logHandler.addToLog( owner + " adds nothing to his Village Mat");
        }
      }

      public void playNativeVillageForCards(LogHandler logHandler) {
        if (logHandler.getHaveToLog()) logHandler.addToLog( owner + " puts all cards from the Village Mat into his hand");
        owner.getCardsInHand().addAll( owner.getNativeVillageMat());
        owner.getNativeVillageMat().clear();
        owner.showHand();
      }
      @Override
    public boolean wantsToBePlayed() {
    	if (owner.getPlayStrategyFor(this)==DomPlayStrategy.ApothecaryNativeVillage){
    		if (owner.getKnownTopCards()>0
    		  && owner.getDeck().lookAtTopCard().getDiscardPriority(1)<DomCardName.Copper.getDiscardPriority(1))
    			return true;
    		return false;
    	}
    	return true;
    }
}