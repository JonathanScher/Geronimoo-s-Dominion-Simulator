package be.aga.dominionSimulator.cards;

import java.util.ArrayList;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.LogHandler;
import be.aga.dominionSimulator.enums.DomCardName;

public class MoneylenderCard extends DomCard {
    public MoneylenderCard () {
      super( DomCardName.Moneylender);
    }

    public void play(LogHandler logHandler) {
      ArrayList<DomCard> theCoppers = owner.getCardsFromHand(DomCardName.Copper); 
      if (!theCoppers.isEmpty()) {
    	owner.trash(owner.removeCardFromHand(theCoppers.get(0)));
    	owner.addAvailableCoins(3);
      }
    }
    
    @Override
    public int getPlayPriority() {
        if (owner.getCardsFromHand(DomCardName.Copper).isEmpty()) 
        	return 100;
    	return super.getPlayPriority();
    }
    
    public int getDiscardPriority(int i) {
    	if (owner!=null)
          if (owner.getCardsFromHand(DomCardName.Copper).isEmpty()) 
        	return 0;
    	return super.getDiscardPriority(i);
    }
}