package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.LogHandler;
import be.aga.dominionSimulator.enums.DomCardName;

public class FeastCard extends DomCard {
    public FeastCard () {
      super( DomCardName.Feast);
    }

    public void play(LogHandler logHandler) {
      DomCardName theDesiredCard = owner.getDesiredCard(new DomCost( 5, 0), false);
      if (theDesiredCard==null) {
        //possible we get here if card was throne-roomed
    	owner.gain(owner.getCurrentGame().getBestCardInSupplyFor(owner, null, new DomCost( 5, 0)));
      } else {
        owner.gain(theDesiredCard);
      }
      owner.removeCardFromPlay(this);
      owner.trash(this);
    }
    
    @Override
    public boolean wantsToBePlayed() {
    	return owner.getDesiredCard(new DomCost( 5, 0), false)!=null;
    }
}