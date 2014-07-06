package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.LogHandler;
import be.aga.dominionSimulator.enums.DomCardName;

public class Philosopher$s_StoneCard extends DomCard {
  public Philosopher$s_StoneCard() {
	super(DomCardName.Philosopher$s_Stone);
  }
  @Override
  public int getCoinValue() {
	return owner.getDeckSize()/5;
  }
  @Override
  public void play(LogHandler logHandler) {
	owner.addAvailableCoins(getCoinValue());
  }
}
