package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.LogHandler;
import be.aga.dominionSimulator.enums.DomCardName;

public class WatchtowerCard extends DrawUntilXCardsCard {
    private DomCard lastWatchtoweredCard = null;

    public WatchtowerCard () {
      super( DomCardName.Watchtower);
    }

    public void play(LogHandler logHandler) {
      owner.drawCards( 6 - owner.getCardsInHand().size() );
    }

    public boolean react(DomCard aCard, LogHandler logHandler) {
       if (logHandler.getHaveToLog()) logHandler.addToLog( owner + " reveals " + this );
       lastWatchtoweredCard = aCard;
       if (aCard.getName().getTrashPriority(owner)<16) {
         owner.trash( aCard );
       } else {
         owner.gainOnTopOfDeck( aCard );
       }
       return true;
    }

    public boolean wantsToBePlayed() {
      if (owner.getCardsInHand().size()<=6 && owner.getDeckSize()>0)
    	  return super.wantsToBePlayed();
      return false;
    }

    public boolean wantsToReact(DomCard aCard) {
      //TODO this way of handling Watchtower looks a bit dirty
      if (lastWatchtoweredCard == aCard)
        return false;
      if (aCard.getName().getTrashPriority(owner)<35) {
     	return true;
      } else {
    	return false;
      }
    }
    @Override
    public int getPlayPriority() {
      return owner.getActionsLeft()>1 ? 6 : super.getPlayPriority();
    }
}