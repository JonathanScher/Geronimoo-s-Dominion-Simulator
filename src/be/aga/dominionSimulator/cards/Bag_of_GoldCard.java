package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.LogHandler;
import be.aga.dominionSimulator.enums.DomCardName;

public class Bag_of_GoldCard extends DomCard {
    public Bag_of_GoldCard () {
      super( DomCardName.Bag_of_Gold);
    }

    public void play(LogHandler logHandler) {
      owner.addActions(1);
      DomCard theGold = owner.getCurrentGame().takeFromSupply( DomCardName.Gold );
      if (theGold!=null) {
        owner.gainOnTopOfDeck( theGold );
      }  
    }
}