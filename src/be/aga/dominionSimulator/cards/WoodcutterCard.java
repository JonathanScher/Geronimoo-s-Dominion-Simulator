package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.LogHandler;
import be.aga.dominionSimulator.enums.DomCardName;

public class WoodcutterCard extends DomCard {
    public WoodcutterCard () {
      super( DomCardName.Woodcutter);
    }

    public void play(LogHandler logHandler) {
        owner.addAvailableCoins(2);
        owner.addAvailableBuys(1);
    }
}