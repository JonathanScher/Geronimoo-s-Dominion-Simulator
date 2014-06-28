package be.aga.dominionSimulator.gai;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import be.aga.dominionSimulator.DomBuyRule;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.gai.factory.BuyRuleListFactory;

public class Main {

	public static void main(String[] args) {
		List<DomCardName> deck = new ArrayList<DomCardName>();
		deck.add(DomCardName.Province);
		deck.add(DomCardName.Duchy);
		deck.add(DomCardName.Estate);
		deck.add(DomCardName.Gold);
		deck.add(DomCardName.Silver);
		deck.add(DomCardName.Copper);
		deck.add(DomCardName.Smithy);
		BuyRuleListFactory factory = new BuyRuleListFactory(deck);
		DomPlayer player = new DomPlayer("randomly generated");

		List<DomBuyRule> buyRules = factory.generateRandomCandidate(new Random(
				System.currentTimeMillis()));
		buyRules.forEach(x -> player.addBuyRule(x));
		System.out.println(player.getXML());
	}

}
