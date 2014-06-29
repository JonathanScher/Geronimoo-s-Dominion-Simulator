package be.aga.dominionSimulator.gai;

import java.util.ArrayList;
import java.util.List;

import be.aga.dominionSimulator.DomBuyRule;

public class BuyStrategy extends ArrayList<DomBuyRule> {

	private static final long serialVersionUID = 2333025382674843577L;

	public BuyStrategy() {
	}

	public BuyStrategy(List<DomBuyRule> candidate) {
		super(candidate);
	}

	@Override
	public String toString() {
		StringBuilder theXML = new StringBuilder();
		String newline = System.getProperty("line.separator");
		theXML.append("<player name=\"").append("generated").append("\"")
				.append(newline);
		theXML.append(" author=\"").append("Jonathan's GAI").append("\"")
				.append(newline);
		theXML.append(" description=\"")
				.append("Bot created with a Genetic Algorithm").append("\"");
		theXML.append(">").append(newline);
		theXML.append(" <type name=\"").append("Bot").append("\"/>")
				.append(newline);
		for (DomBuyRule theRule : this) {
			theXML.append(theRule.getXML());
		}
		theXML.append("</player>").append(newline);
		return theXML.toString();

	}
}
