package be.aga.dominionSimulator.gai.fitnesse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.uncommons.watchmaker.framework.FitnessEvaluator;

import be.aga.dominionSimulator.DomBoard;
import be.aga.dominionSimulator.DomBuyRule;
import be.aga.dominionSimulator.DomGame;
import be.aga.dominionSimulator.DomPlayer;

public class BotEvaluator implements FitnessEvaluator<List<DomBuyRule>> {

	private static final int GAMES_REQUIRED_FOR_GOOD_AVERAGE = 1000;
	private List<DomBuyRule> opponentBuyRules;

	public BotEvaluator(List<DomBuyRule> opponentBuyRules) {
		this.opponentBuyRules = opponentBuyRules;
	}

	@Override
	public double getFitness(List<DomBuyRule> candidate,
			List<? extends List<DomBuyRule>> population) {
		DomPlayer opponentPlayer = new DomPlayer("opponent");
		opponentPlayer.addBuyRules(opponentBuyRules);
		DomPlayer candidatePlayer = new DomPlayer("candidate");
		candidatePlayer.addBuyRules(candidate);
		List<DomPlayer> players = new ArrayList<DomPlayer>();
		players.add(candidatePlayer);
		players.add(opponentPlayer);

		return startSimulation(players, false, GAMES_REQUIRED_FOR_GOOD_AVERAGE,
				false);
	}

	public double startSimulation(List<DomPlayer> thePlayers,
			boolean keepOrder, int aNumber, boolean aShowLog) {
		Integer emptyPilesEndingCount;
		Integer NUMBER_OF_GAMES;
		List<DomPlayer> players = new ArrayList<>();
		List<Double> ratio = new ArrayList<>();
		DomPlayer candidatePlayer = thePlayers.get(0);
		DomPlayer basePlayer = thePlayers.get(1);
		emptyPilesEndingCount = 0;

		NUMBER_OF_GAMES = aNumber;
		players.clear();
		players.addAll(thePlayers);
		DomBoard theBoard = null;
		for (int i = 0; i < NUMBER_OF_GAMES; i++) {
			if (!keepOrder) {
				Collections.shuffle(players);
			}
			DomGame theGame = new DomGame(theBoard, players);
			theGame.run();
			emptyPilesEndingCount += theGame.emptyPilesEnding ? 1 : 0;
			theGame.determineWinners();
			ratio.add(Double.valueOf(candidatePlayer.countVictoryPoints())
					/ Double.valueOf(basePlayer.countVictoryPoints()));
			theBoard = theGame.getBoard();
			theBoard.reset();
		}
		// restoring the player order:
		players.clear();
		players.addAll(thePlayers);
		Double winPercentage = Double.valueOf(candidatePlayer.getWins())
				/ NUMBER_OF_GAMES * 100;
		Double ratioAverage = ratio.stream().mapToDouble(x -> x).average()
				.getAsDouble();
		if (winPercentage == 0) {
			return ratioAverage;
		} else {
			return winPercentage;
		}
	}

	@Override
	public boolean isNatural() {
		return true;
	}

}
