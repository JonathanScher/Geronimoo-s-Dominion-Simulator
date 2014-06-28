package be.aga.dominionSimulator.gai.fitnesse;

import java.util.ArrayList;
import java.util.List;

import org.uncommons.watchmaker.framework.FitnessEvaluator;

import be.aga.dominionSimulator.DomBuyRule;
import be.aga.dominionSimulator.DomGame;
import be.aga.dominionSimulator.DomPlayer;

public class BotEvaluator implements FitnessEvaluator<List<DomBuyRule>> {

	private static final int GAMES_REQUIRED_FOR_GOOD_AVERAGE = 100;
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
		
		for (int i = 0; i < GAMES_REQUIRED_FOR_GOOD_AVERAGE; i++) {
			DomGame theGame = new DomGame(null, players);
			theGame.run();
			ratio.add(Double.valueOf(candidatePlayer.countVictoryPoints())
					/ Double.valueOf(opponentPlayer.countVictoryPoints()));
			theGame.getBoard().reset();
		}
		Double result = ratio.stream().mapToDouble(x->x).average().getAsDouble();
		return result;
	}

	@Override
	public boolean isNatural() {
		return true;
	}

}
