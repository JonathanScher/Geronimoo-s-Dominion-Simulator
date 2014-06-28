package be.aga.dominionSimulator.gai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.jfree.data.statistics.MeanAndStandardDeviation;

import be.aga.dominionSimulator.DomBoard;
import be.aga.dominionSimulator.DomBuyRule;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.DomGame;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.gai.factory.BuyRuleListFactory;

public class Main {

	public static void main(String[] args) {
		List<DomPlayer> players = new ArrayList<>();
		DomPlayer player1 = createRandomPlayer();
		players.add(player1);
		// players.add(player2);

		DomPlayer player2 = new DomPlayer("simpleBM");
		player2.addBuyRule(new DomBuyRule(DomCardName.Province));
		player2.addBuyRule(new DomBuyRule(DomCardName.Gold));
		player2.addBuyRule(new DomBuyRule(DomCardName.Silver));
		players.add(player2);
		List<Integer> player1VictoryPoints = new ArrayList<>();
		List<Integer> player2VictoryPoints = new ArrayList<>();
		List<Double> ratio = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			DomGame theGame = new DomGame(null, players);
			theGame.run();
			player1VictoryPoints.add(player1.countVictoryPoints());
			player2VictoryPoints.add(player2.countVictoryPoints());
			ratio.add(Double.valueOf(player1.countVictoryPoints())
					/ Double.valueOf(player2.countVictoryPoints()));
			theGame.getBoard().reset();
		}
		System.out.println("player 1 average victory points: "
				+ player1VictoryPoints.stream().mapToDouble(x -> x).average());
		System.out.println("player 2 average victory points: "
				+ player2VictoryPoints.stream().mapToDouble(x -> x).average());
		System.out.println("ratio                            "
				+ ratio.stream().mapToDouble(x -> x).average());
	}

	public static DomPlayer createRandomPlayer() {
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
		return player;
	}
}
