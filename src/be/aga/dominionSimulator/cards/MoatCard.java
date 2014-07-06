package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.LogHandler;
import be.aga.dominionSimulator.enums.DomCardName;

public class MoatCard extends DomCard {
    public MoatCard () {
      super( DomCardName.Moat);
    }

    public void play(LogHandler logHandler) {
      owner.drawCards( 2 );
    }
}