package be.aga.dominionSimulator;

import be.aga.dominionSimulator.enums.DomBotFunction;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class Operand {
	private DomBotFunction function;
	private double value;
	private DomCardName cardName;
	private DomCardType cardType;

	public Operand() {
	}

	public DomBotFunction getFunction() {
		return function;
	}

	public void setFunction(DomBotFunction function) {
		this.function = function;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public DomCardName getCardName() {
		return cardName;
	}

	public void setCardName(DomCardName leftCardName) {
		this.cardName = leftCardName;
	}

	public DomCardType getCardType() {
		return cardType;
	}

	public void setCardType(DomCardType cardType) {
		this.cardType = cardType;
	}
}