package be.aga.dominionSimulator.gai.fitnesse;

import java.util.ArrayList;
import java.util.List;

import org.uncommons.watchmaker.framework.FitnessEvaluator;

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
		List<Double> ratio = new ArrayList<>();
		Integer wins = 0;

		for (int i = 0; i < GAMES_REQUIRED_FOR_GOOD_AVERAGE; i++) {
			DomGame theGame = new DomGame(null, players);
			theGame.run();
			Integer candidateVictory = candidatePlayer.countVictoryPoints();
			Integer opponentVictory = opponentPlayer.countVictoryPoints();
			ratio.add(Double.valueOf(candidateVictory)
					/ Double.valueOf(opponentVictory));

			if (candidateVictory > opponentVictory) {
				wins++;
			}
			theGame.getBoard().reset();
		}
		Double ratioAverage = ratio.stream().mapToDouble(x -> x).average()
				.getAsDouble();
		Double winAverage = Double.valueOf(wins)/GAMES_REQUIRED_FOR_GOOD_AVERAGE;
		return ratioAverage + 100 * winAverage;
	}

	@Override
	public boolean isNatural() {
		return true;
	}

}
