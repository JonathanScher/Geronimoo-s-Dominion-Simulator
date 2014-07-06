package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.LogHandler;
import be.aga.dominionSimulator.enums.DomCardName;

public class PrincessCard extends DomCard {

	public PrincessCard () {
      super( DomCardName.Princess);
    }

    public void play(LogHandler logHandler) {        
      owner.addAvailableBuys(1);
      owner.getCurrentGame().setPrincessPlayed();
   }
}