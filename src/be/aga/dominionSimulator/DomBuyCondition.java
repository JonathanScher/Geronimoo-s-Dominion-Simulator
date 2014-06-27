package be.aga.dominionSimulator;

import be.aga.dominionSimulator.enums.DomBotComparator;
import be.aga.dominionSimulator.enums.DomBotFunction;
import be.aga.dominionSimulator.enums.DomBotOperator;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;
import be.aga.dominionSimulator.gui.DomBuyConditionPanel;
import be.aga.dominionSimulator.gui.DomBuyRulePanel;

public class DomBuyCondition {
	private Operand left = new Operand();
	private DomBotComparator comparator;

	private Operand right = new Operand();
	private DomBotOperator extraOperator = DomBotOperator.plus;
	private double extraAttribute;

	public DomBuyCondition(DomBotFunction aLeftFunction,
			DomCardName aLeftCardName, DomCardType aLeftCardType,
			String aLeftValue, DomBotComparator aComparator,
			DomBotFunction aRightFunction, DomCardName aRightCardName,
			DomCardType aRightCardType, String aRightValue,
			DomBotOperator anExtraOperator, String anExtraAttribute) {
		left.setFunction(aLeftFunction);
		left.setCardName(aLeftCardName);
		left.setCardType(aLeftCardType);
		left.setValue(new Double(aLeftValue).doubleValue());
		comparator = aComparator;
		right.setFunction(aRightFunction);
		right.setCardName(aRightCardName);
		right.setCardType(aRightCardType);
		right.setValue(new Double(aRightValue).doubleValue());
		extraOperator = anExtraOperator;
		if (anExtraAttribute == null || anExtraAttribute.equals("")) {
			extraAttribute = 0;
		} else {
			extraAttribute = new Double(anExtraAttribute).doubleValue();
		}
	}

	public DomBuyCondition() {
	}

	public boolean isTrue(DomPlayer owner) {
		switch (left.getFunction()) {
		case countCardsInDeck:
			left.setValue(owner.countInDeck(left.getCardName()));
			break;
		case countCardTypeInDeck:
			left.setValue(owner.count(left.getCardType()));
			break;
		case countCardsInSupply:
			left.setValue(owner.getCurrentGame().countInSupply(
					left.getCardName()));
			break;
		case countCardsInPlay:
			left.setValue(owner.getCardsFromPlay(left.getCardName()).size());
			break;
		case countCardsInHand:
			left.setValue(owner.getCardsFromHand(left.getCardName()).size());
			break;
		case countAllCardsInDeck:
			left.setValue(owner.countAllCards());
			break;
		case countAvailableMoney:
			left.setValue(owner.getAvailableCoins());
			break;
		case countTurns:
			left.setValue(owner.getTurns());
			break;
		case gainsNeededToEndGame:
			left.setValue(owner.getCurrentGame().getGainsNeededToEndGame());
			break;
		case isActionPhase:
			left.setValue(owner.isInBuyPhase() ? 0 : 1);
			break;
		case countBuysLeft:
			left.setValue(owner.buysLeft);
			break;
		case getTotalMoney:
			left.setValue(owner.getTotalMoneyInDeck());
			break;
		case getTotalMoneyExcludingNativeVillage:
			left.setValue(owner.getTotalMoneyExcludingNativeVillage());
			break;
		case countVP:
			left.setValue(owner.countVictoryPoints());
			break;
		case countMAXOpponentVP:
			left.setValue(owner.countMaxOpponentsVictoryPoints());
			break;
		case countCardsLeftInDrawDeck:
			left.setValue(owner.getDeck().getDrawDeckSize());
			break;
		case countEmptyPiles:
			left.setValue(owner.getCurrentGame().countEmptyPiles());
			break;
		case countCardsLeftInSmallestPile:
			left.setValue(owner.getCurrentGame().countCardsInSmallestPile());
			break;
		case countCardsInOpponentsDecks:
			left.setValue(0);
			for (DomPlayer player : owner.getOpponents())
				left.setValue(left.getValue()
						+ player.countInDeck(left.getCardName()));
			break;
		}
		switch (right.getFunction()) {
		case countCardsInDeck:
			right.setValue(owner.countInDeck(right.getCardName()));
			break;
		case countCardTypeInDeck:
			right.setValue(owner.count(right.getCardType()));
			break;
		case countCardsInSupply:
			right.setValue(owner.getCurrentGame().countInSupply(
					right.getCardName()));
			break;
		case countCardsInHand:
			right.setValue(owner.getCardsFromHand(right.getCardName()).size());
			break;
		case countCardsInPlay:
			right.setValue(owner.getCardsFromPlay(right.getCardName()).size());
			break;
		case countAllCardsInDeck:
			right.setValue(owner.countAllCards());
			break;
		case countAvailableMoney:
			right.setValue(owner.getAvailableCoins());
			break;
		case gainsNeededToEndGame:
			right.setValue(owner.getCurrentGame().getGainsNeededToEndGame());
			break;
		case countTurns:
			right.setValue(owner.getTurns());
			break;
		case isActionPhase:
			right.setValue(owner.isInBuyPhase() ? 0 : 1);
			break;
		case countBuysLeft:
			right.setValue(owner.buysLeft);
			break;
		case getTotalMoney:
			right.setValue(owner.getTotalMoneyInDeck());
			break;
		case getTotalMoneyExcludingNativeVillage:
			right.setValue(owner.getTotalMoneyExcludingNativeVillage());
			break;
		case countVP:
			right.setValue(owner.countVictoryPoints());
			break;
		case countMAXOpponentVP:
			right.setValue(owner.countMaxOpponentsVictoryPoints());
			break;
		case countCardsLeftInDrawDeck:
			right.setValue(owner.getDeck().getDrawDeckSize());
			break;
		case countEmptyPiles:
			right.setValue(owner.getCurrentGame().countEmptyPiles());
			break;
		case countCardsLeftInSmallestPile:
			right.setValue(owner.getCurrentGame().countCardsInSmallestPile());
			break;
		case countCardsInOpponentsDecks:
			right.setValue(0);
			for (DomPlayer player : owner.getOpponents())
				right.setValue(right.getValue()
						+ player.countInDeck(right.getCardName()));
			break;
		}
		double theRightValue = right.getValue();
		switch (extraOperator) {
		case plus:
			theRightValue = right.getValue() + extraAttribute;
			break;
		case minus:
			theRightValue = right.getValue() - extraAttribute;
			break;
		case multiplyWith:
			theRightValue = right.getValue() * extraAttribute;
			break;
		case divideBy:
			theRightValue = right.getValue() / extraAttribute;
			break;
		}
		switch (comparator) {
		case equalTo:
			return left.getValue() == theRightValue;
		case smallerOrEqualThan:
			return left.getValue() <= theRightValue;
		case smallerThan:
			return left.getValue() < theRightValue;
		case greaterOrEqualThan:
			return left.getValue() >= theRightValue;
		case greaterThan:
			return left.getValue() > theRightValue;
		}

		return false;
	}

	public void addRightHand(String aType, String anAttribute) {
		right.setFunction(DomBotFunction.valueOf(aType));
		if (anAttribute == null)
			return;
		if (right.getFunction() == DomBotFunction.constant) {
			right.setValue(Double.valueOf(anAttribute).doubleValue());
			return;
		}
		if (right.getFunction() == DomBotFunction.countCardTypeInDeck) {
			right.setCardType(DomCardType.valueOf(anAttribute));
			return;
		}
		right.setCardName(DomCardName.valueOf(anAttribute));
	}

	public void addLeftHand(String aType, String anAttribute) {
		left.setFunction(DomBotFunction.valueOf(aType));
		if (anAttribute == null)
			return;
		if (left.getFunction() == DomBotFunction.constant) {
			left.setValue(Double.valueOf(anAttribute).doubleValue());
			return;
		}
		if (left.getFunction() == DomBotFunction.countCardTypeInDeck) {
			left.setCardType(DomCardType.valueOf(anAttribute));
			return;
		}
		left.setCardName(DomCardName.valueOf(anAttribute));
	}

	public void addComparator(String aComparator) {
		comparator = DomBotComparator.valueOf(aComparator);
	}

	/**
	 * @param aResolveAttrib
	 * @param aResolveAttrib2
	 */
	public void addExtraOperation(String aType, String anAttribute) {
		extraOperator = DomBotOperator.valueOf(aType);
		extraAttribute = Double.valueOf(anAttribute).doubleValue();
	}

	public DomBotFunction getLeftFunction() {
		return left.getFunction();
	}

	public void setLeftFunction(DomBotFunction leftFunction) {
		this.left.setFunction(leftFunction);
	}

	public double getLeftValue() {
		return left.getValue();
	}

	public void setLeftValue(double leftValue) {
		this.left.setValue(leftValue);
	}

	public DomCardName getLeftCardName() {
		return left.getCardName();
	}

	public void setLeftCardName(DomCardName leftCardName) {
		this.left.setCardName(leftCardName);
	}

	public DomBotComparator getComparator() {
		return comparator;
	}

	public void setComparator(DomBotComparator comparator) {
		this.comparator = comparator;
	}

	public DomBotFunction getRightFunction() {
		return right.getFunction();
	}

	public void setRightFunction(DomBotFunction rightFunction) {
		this.right.setFunction(rightFunction);
	}

	public double getRightValue() {
		return right.getValue();
	}

	public void setRightValue(double rightValue) {
		this.right.setValue(rightValue);
	}

	public DomCardName getRightCardName() {
		return right.getCardName();
	}

	public void setRightCardName(DomCardName rightCardName) {
		this.right.setCardName(rightCardName);
	}

	public DomBotOperator getExtraOperator() {
		return extraOperator;
	}

	public void setExtraOperator(DomBotOperator extraOperator) {
		this.extraOperator = extraOperator;
	}

	public double getExtraAttribute() {
		return extraAttribute;
	}

	public void setExtraAttribute(double extraAttribute) {
		this.extraAttribute = extraAttribute;
	}

	public DomCardType getRightCardType() {
		return right.getCardType();
	}

	public void setRightCardType(DomCardType rightCardType) {
		this.right.setCardType(rightCardType);
	}

	public DomCardType getLeftCardType() {
		return left.getCardType();
	}

	public void setLeftCardType(DomCardType leftCardType) {
		this.left.setCardType(leftCardType);
	}

	public Operand getLeft() {
		return left;
	}

	public void setLeft(Operand left) {
		this.left = left;
	}

	public Operand getRight() {
		return right;
	}

	public void setRight(Operand right) {
		this.right = right;
	}

	/**
	 * @param domBuyRulePanel
	 * @return
	 */
	public DomBuyConditionPanel getGuiPanel(DomBuyRulePanel domBuyRulePanel) {
		return new DomBuyConditionPanel(this, domBuyRulePanel);
	}

	public String getXML(String theRuleIndentation) {
		StringBuilder theXML = new StringBuilder();
		String newline = System.getProperty("line.separator");
		String theIndentation = "   ";
		theXML.append(theRuleIndentation).append(theIndentation);
		theXML.append("<condition>").append(newline);
		theXML.append(theRuleIndentation).append(theIndentation)
				.append(theIndentation);
		theXML.append("<left type=\"").append(left.getFunction().name())
				.append("\"");
		if (left.getFunction() == DomBotFunction.constant) {
			theXML.append(" attribute=\"").append(left.getValue()).append("\"");
		}
		if (left.getFunction() == DomBotFunction.countCardTypeInDeck) {
			theXML.append(" attribute=\"").append(left.getCardType().name())
					.append("\"");
		}
		if (left.getFunction() == DomBotFunction.countCardsInDeck
				|| left.getFunction() == DomBotFunction.countCardsInSupply
				|| left.getFunction() == DomBotFunction.countCardsInOpponentsDecks
				|| left.getFunction() == DomBotFunction.countCardsInHand
				|| left.getFunction() == DomBotFunction.countCardsInPlay) {
			theXML.append(" attribute=\"").append(left.getCardName().name())
					.append("\"");
		}
		theXML.append("/>").append(newline);
		theXML.append(theRuleIndentation).append(theIndentation)
				.append(theIndentation);
		theXML.append("<operator type=\"").append(comparator.name())
				.append("\" />").append(newline);
		theXML.append(theRuleIndentation).append(theIndentation)
				.append(theIndentation);
		theXML.append("<right type=\"").append(right.getFunction().name())
				.append("\"");
		if (right.getFunction() == DomBotFunction.constant) {
			theXML.append(" attribute=\"").append(right.getValue())
					.append("\"");
		}
		if (right.getFunction() == DomBotFunction.countCardTypeInDeck) {
			theXML.append(" attribute=\"").append(right.getCardType().name())
					.append("\"");
		}
		if (right.getFunction() == DomBotFunction.countCardsInDeck
				|| right.getFunction() == DomBotFunction.countCardsInSupply
				|| right.getFunction() == DomBotFunction.countCardsInOpponentsDecks
				|| right.getFunction() == DomBotFunction.countCardsInHand
				|| right.getFunction() == DomBotFunction.countCardsInPlay) {
			theXML.append(" attribute=\"").append(right.getCardName().name())
					.append("\"");
		}
		theXML.append("/>").append(newline);
		if (extraAttribute != 0) {
			theXML.append(theRuleIndentation).append(theIndentation)
					.append(theIndentation);
			theXML.append("<extra_operation type=\"")
					.append(extraOperator.name()).append("\"");
			theXML.append(" attribute=\"").append(extraAttribute)
					.append("\" />").append(newline);
		}
		theXML.append(theRuleIndentation).append(theIndentation);
		theXML.append("</condition>").append(newline);
		return theXML.toString();
	}
}