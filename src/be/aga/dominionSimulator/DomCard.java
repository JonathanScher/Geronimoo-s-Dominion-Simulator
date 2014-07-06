package be.aga.dominionSimulator;

import java.util.Comparator;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;


public class DomCard implements Comparable< DomCard >{
    DomCardName name;
    public DomPlayer owner = null;
	private boolean isFromBlackMarket=false;
	private boolean isTaggedByHerbalist;
	private boolean isBane=false;
	private boolean isTaggedByScheme;
	private boolean discardAtCleanUp=true;

    public static final Comparator<DomCard> SORT_FOR_TRASHING = new Comparator<DomCard>(){
        @Override
        public int compare( DomCard aO1, DomCard aO2 ) {
            if (aO1.getTrashPriority()< aO2.getTrashPriority())
                return -1;
            if (aO1.getTrashPriority() > aO2.getTrashPriority())
                return 1;
            return 0;
        }
      };

      public static final Comparator<DomCard> SORT_FOR_PLAYING = new Comparator<DomCard>(){
          @Override
          public int compare( DomCard aO1, DomCard aO2 ) {
              if (aO1.getPlayPriority()< aO2.getPlayPriority())
                  return -1;
              if (aO1.getPlayPriority() > aO2.getPlayPriority())
                  return 1;
              return 0;
          }
        };

      public static final Comparator<DomCard> SORT_FOR_DISCARDING = new Comparator<DomCard>(){
          @Override
          public int compare( DomCard aO1, DomCard aO2 ) {
              if (aO1.getDiscardPriority(1)< aO2.getDiscardPriority(1))
                  return -1;
              if (aO1.getDiscardPriority(1) > aO2.getDiscardPriority(1))
                  return 1;
              return 0;
          }
        };

        public static final Comparator<DomCard> SORT_FOR_DISCARD_FROM_HAND = new Comparator<DomCard>(){
            @Override
            public int compare( DomCard aO1, DomCard aO2 ) {
                int theActionsLeft = aO1.owner.getActionsLeft();
                if (aO1.getDiscardPriority(theActionsLeft)< aO2.getDiscardPriority(theActionsLeft))
                    return -1;
                if (aO1.getDiscardPriority(theActionsLeft) > aO2.getDiscardPriority(theActionsLeft))
                    return 1;
                return 0;
            }
          };

    /**
     * @param aCardName
     */
    public DomCard ( DomCardName aCardName ) {
      name = aCardName;
    }

    /**
     * @return
     */
    public DomCardName getName() {
      return name;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
      return name.toHTML();
    }

    /**
     * @param aTreasure
     * @return
     */
    public boolean hasCardType( DomCardType aType ) {
      return name.hasCardType( aType );
    }

    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo( DomCard aO ) {
        if (getName().getCoinValue()< aO.getName().getCoinValue())
            return 1;
        if (getName().getCoinValue()> aO.getName().getCoinValue())
            return -1;
        return 0;
    }

    /**
     * @param aActionsLeft
     * @return
     */
    public int getPlayPriority() {
      return name.getPlayPriority();  
    }

    /**
     * @return
     */
    public double getPotentialCoinValue() {
      if (owner.getActionsLeft()==0 && hasCardType( DomCardType.Action ) && owner.getCardsInHand().contains( this ) ) {
        return 0;
      } else {
        return name.getCoinValue();
      }
    }

    /**
     * @param aActionsLeft
     * @return
     */
    public int getDiscardPriority( int aActionsLeft ) {
//    	//TODO problem with Royal Seal whic
//        if (aActionsLeft>0 && getTrashPriority()<16 && owner.getCardsFromHand(DomCardType.Trasher).size()>0)
//          //added this to make sure player keeps Curse in hand when he also has a Bishop for instance 
//          return 36-getTrashPriority();
        return name.getDiscardPriority(aActionsLeft);
    }

    /**
     * @return
     */
    public int getVictoryValue() {
      return name.getVictoryValue(owner);
    }

    /**
     * @param aDomPlayer 
     * @return
     */
    public int getTrashPriority() {
      return name.getTrashPriority();
    }

    /**
     * @return
     */
    public boolean wantsToBePlayed() {
      return true;
    }

    /**
     * @param aPlayer
     */
    public void setOwner( DomPlayer aPlayer ) {
      owner = aPlayer;
    }

    public void play(LogHandler logHandler) {
      owner.availableCoins+=getCoinValue();        
      owner.availablePotions+=getPotionValue();        
    }

    /**
     * @return
     */
    public int getPotionValue() {
      return 0;
    }

    public int getCoinValue() {
        return name.getCoinValue();
    }

    public void resolveDuration() {}

    public int getCoinCost( DomGame aGame) {
        return name.getCoinCost( aGame );
    }

    public void handleCleanUpPhase() {
		if (!discardAtCleanUp()){
		  owner.addToCardsToStayInPlay(this);
		  return;
		}
		if (isTaggedByHerbalist() || isTaggedByScheme()){
		  owner.putOnTopOfDeck(this);
		  //reset this boolean
		  isTaggedByHerbalist=false; 
		  isTaggedByScheme=false;
		  return;
		}
		owner.getDeck().discard( this);
    }

    public int getPotionCost() {
      return name.getPotionCost();
    }

    /**
     * @param aOwner
     * @return
     */
    public DomCost getPotentialCurrencyValue() {
      return new DomCost( (int) getPotentialCoinValue(), getPotionValue());
    }

	public void setFromBlackMarket(boolean b) {
		isFromBlackMarket=b;
	}

	public boolean isFromBlackMarket() {
		return isFromBlackMarket;
	}

	public void addHerbalistTag() {
		isTaggedByHerbalist=true;
	}

	public boolean isTaggedByHerbalist() {
		return isTaggedByHerbalist;
	}

	public  DomCost getCost(DomGame aDomGame) {
		return name.getCost(aDomGame);
	}

	public boolean isBane() {
		return isBane;
	}

	public void setAsBane() {
		isBane=true;
	}
	
	public void doWhenGained() {}

	public void doWhenDiscarded() {}

	public boolean isTaggedByScheme() {
		return isTaggedByScheme;
	}

	public void addSchemeTag() {
		isTaggedByScheme=true;
	}

	public boolean discardAtCleanUp() {
		return discardAtCleanUp; 
	}

	public void setDiscardAtCleanup(boolean b) {
	  discardAtCleanUp=b;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (discardAtCleanUp ? 1231 : 1237);
		result = prime * result + (isBane ? 1231 : 1237);
		result = prime * result + (isFromBlackMarket ? 1231 : 1237);
		result = prime * result + (isTaggedByHerbalist ? 1231 : 1237);
		result = prime * result + (isTaggedByScheme ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DomCard other = (DomCard) obj;
		if (discardAtCleanUp != other.discardAtCleanUp)
			return false;
		if (isBane != other.isBane)
			return false;
		if (isFromBlackMarket != other.isFromBlackMarket)
			return false;
		if (isTaggedByHerbalist != other.isTaggedByHerbalist)
			return false;
		if (isTaggedByScheme != other.isTaggedByScheme)
			return false;
		if (name != other.name)
			return false;
		if (owner == null) {
			if (other.owner != null)
				return false;
		} else if (!owner.equals(other.owner))
			return false;
		return true;
	}
	

}