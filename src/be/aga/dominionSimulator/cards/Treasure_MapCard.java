package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

public class Treasure_MapCard extends DomCard {
    public Treasure_MapCard () {
      super( DomCardName.Treasure_Map );
    }

    public void play() {
      DomPlayer theOwner = owner;
      theOwner.trash( theOwner.removeCardFromPlay(this ));
      if (theOwner.getCardsFromHand( DomCardName.Treasure_Map ).size()>0) {
        theOwner.trash(theOwner.removeCardFromHand( theOwner.getCardsFromHand( DomCardName.Treasure_Map ).get( 0 )  ));
        for (int i=0;i<4;i++) {
          DomCard theGold = theOwner.getCurrentGame().takeFromSupply( DomCardName.Gold );
          if (theGold!=null) {
            theOwner.gainOnTopOfDeck( theGold );
          }  
        }
      }
    }
    
    public boolean wantsToBePlayed() {
      return owner.getCardsFromHand( DomCardName.Treasure_Map ).size()>1;
    }
      
    @Override
    public int getDiscardPriority( int aActionsLeft ) {
    	return wantsToBePlayed() ? 50 : 0;
    }
}