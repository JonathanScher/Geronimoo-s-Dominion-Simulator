package be.aga.dominionSimulator.cards;

import java.util.ArrayList;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.enums.DomCardName;

public class CopperCard extends DomCard {

    public CopperCard () {
      super( DomCardName.Copper);
    }
    @Override
    public int getCoinValue() {
      return owner.getCoppersmithsPlayed()+1;
    }
    @Override
    public boolean wantsToBePlayed() {
    	if (handlePossibleGrandMarketBuy())
    	  return false;
    	
    	return true;
    }
    
	private boolean handlePossibleGrandMarketBuy() {
		//if we've already played a copper or there is no Grand Market in the supply, we can play the Copper
		if (owner.getCurrentGame().countInSupply(DomCardName.Grand_Market)==0
    	  || !owner.getCardsFromPlay(DomCardName.Copper).isEmpty())
    		return false;
		//if we are going to buy a better card then Grand Market this turn (Colony perhaps) just play the Copper
    	DomCardName theDesiredCard = owner.getDesiredCard(owner.getTotalPotentialCurrency(), false);
    	if (theDesiredCard!=null && theDesiredCard.getTrashPriority(owner)>
    	  DomCardName.Grand_Market.getTrashPriority(owner))
    		return false;
    	//finally if we can buy a Grand Market with the current available money, don't play the Copper 
    	ArrayList<DomCard> theCoppersInHand = owner.getCardsFromHand(DomCardName.Copper);
    	DomCost theCurrencyWithoutCoppers = owner.getTotalPotentialCurrency().add(new DomCost(-theCoppersInHand.size()*getCoinValue(),0));
    	if (owner.getDesiredCard(theCurrencyWithoutCoppers, false) == DomCardName.Grand_Market)
    		return true;
    	//just play that Copper
    	return false;
	}
}