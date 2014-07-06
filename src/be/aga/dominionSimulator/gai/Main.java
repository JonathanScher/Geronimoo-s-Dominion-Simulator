package be.aga.dominionSimulator.gai;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import org.uncommons.maths.random.MersenneTwisterRNG;
import org.uncommons.watchmaker.framework.TerminationCondition;
import org.uncommons.watchmaker.framework.termination.ElapsedTime;
import org.uncommons.watchmaker.framework.termination.TargetFitness;
import org.uncommons.watchmaker.swing.evolutionmonitor.EvolutionMonitor;

import be.aga.dominionSimulator.DomBuyRule;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.DomGame;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.LogHandler;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.gai.factory.BotFactory;
import be.aga.dominionSimulator.gai.factory.BuyRuleListFactory;
import be.aga.dominionSimulator.gai.factory.RealmFactory;
import be.aga.dominionSimulator.gai.fitnesse.BotEvaluator;

@SuppressWarnings("unused")
public class Main {
	private static final double TARGET_FITNESSE = 85;

	public static void main(String[] args) {
		runGAI();
//		runBotEvaluatorWithTwoBigMoney();
//		runBotEvaluatorWithOneEstateBuyerAndOneBM();
//		runEngineWithTwoBigMoney();
	}

	public static void runGAI() {
		GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm();
		
		EvolutionMonitor<List<DomBuyRule>> evolutionMonitor = new EvolutionMonitor<>();
		geneticAlgorithm.addObserver(evolutionMonitor);
		JFrame frame = new JFrame();
		frame.add(evolutionMonitor.getGUIComponent());
		frame.setVisible(true);
		frame.setSize(800, 600);

		List<DomBuyRule> bot = new BotFactory().generateBigMoney();
		List<DomCardName> realm = new RealmFactory().generateSmithyRealm();
		
		TerminationCondition targetCondition = new TargetFitness(
				TARGET_FITNESSE, true);
		targetCondition = new ElapsedTime(Long.MAX_VALUE);
		// targetCondition = new GenerationCount(2000);
		geneticAlgorithm.run(realm, bot, targetCondition);
	}
	
	
	
	
	private static void runBotEvaluatorWithOneEstateBuyerAndOneBM() {
		BuyStrategy bigMoney = generateBigMoneyStrategy();

		BuyStrategy buyEstate = new BuyStrategy();
		buyEstate.add(new DomBuyRule(DomCardName.Estate));
		BotEvaluator evaluator = new BotEvaluator(bigMoney);
		List<Double> fitnesses = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			Double fitness = evaluator.getFitness(buyEstate, null);
			fitnesses.add(fitness);
			System.out.println(fitness);
		}
		
	}

	private static void runEngineWithTwoBigMoney() {
		DomEngine engine = new DomEngine();
		List<DomPlayer> players = new ArrayList<DomPlayer>();
		DomPlayer player1 = new DomPlayer("player1");
		player1.addBuyRules(generateBigMoneyStrategy());
		players.add(player1);
		
		DomPlayer player2 = new DomPlayer("player2");
		player2.addBuyRules(generateBigMoneyStrategy());
		players.add(player2);
		
		engine.startSimulation(players, false, 1000, false);
	}

	private static void runBotEvaluatorWithTwoBigMoney() {
		BuyStrategy bigMoney = generateBigMoneyStrategy();

		BuyStrategy bigMoney2 = new BuyStrategy(bigMoney);
		BotEvaluator evaluator = new BotEvaluator(bigMoney);
		List<Double> fitnesses = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			Double fitness = evaluator.getFitness(bigMoney2, null);
			fitnesses.add(fitness);
			System.out.println(fitness);
		}

	}

	public static BuyStrategy generateBigMoneyStrategy() {
		BuyStrategy bigMoney = new BuyStrategy();
		bigMoney.add(new DomBuyRule(DomCardName.Province));
		bigMoney.add(new DomBuyRule(DomCardName.Gold));
		bigMoney.add(new DomBuyRule(DomCardName.Silver));
		return bigMoney;
	}



	private static void runGames() {
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
			DomGame theGame = new DomGame(null, players, new LogHandler());
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
