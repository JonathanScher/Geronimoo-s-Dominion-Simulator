package be.aga.dominionSimulator.cards;

import java.util.ArrayList;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.LogHandler;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class MenagerieCard extends DomCard {
    public MenagerieCard () {
      super( DomCardName.Menagerie);
    }

    public void play(LogHandler logHandler) {
      owner.addActions(1);
      if (getMultiplesInHand().isEmpty()) 
        owner.drawCards(3);
      else
    	owner.drawCards(1);
    }

	@Override
	public int getPlayPriority() {
		//play it as soon as possible if we have no multiples
		if (getMultiplesInHand().isEmpty())
		  return 0;
		//if we have multiples, but more than 1 action left wait to play it as long as possible
		if (owner.getActionsLeft()>1)
		  return 1000;
		//if we have multiples, but only 1 action left, we're going to wait until the other card gets played
	    for (DomCard theCard : owner.getCardsInHand()) {
		   if (theCard==this || theCard.getName()==DomCardName.Menagerie)
			  continue;
		   if (theCard.hasCardType(DomCardType.Action)&&!theCard.hasCardType(DomCardType.Terminal)) 
//				   && theCard.wantsToBePlayed())
			  return 1000;
	    }
	    //we still haven't found a way to let us draw 3 so we just play the Menagerie as a Great Hall...
	    return super.getPlayPriority();
	}

	public ArrayList<DomCard> getMultiplesInHand() {
    	ArrayList<DomCardName> theSingleCards = new ArrayList<DomCardName>();
    	ArrayList<DomCard> theMultipleCards = new ArrayList<DomCard>();
        for (DomCard theCard : owner.getCardsInHand()) {
          if (theCard==this)
            continue;
		  if (!theSingleCards.contains(theCard.getName())){
        	theSingleCards.add(theCard.getName());
          } else {
            theMultipleCards.add(theCard);
          }
        }
		return theMultipleCards;
	}
}