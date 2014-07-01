package be.aga.dominionSimulator.gai;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.uncommons.maths.random.MersenneTwisterRNG;
import org.uncommons.maths.random.Probability;
import org.uncommons.watchmaker.framework.CachingFitnessEvaluator;
import org.uncommons.watchmaker.framework.CandidateFactory;
import org.uncommons.watchmaker.framework.EvolutionEngine;
import org.uncommons.watchmaker.framework.EvolutionObserver;
import org.uncommons.watchmaker.framework.EvolutionaryOperator;
import org.uncommons.watchmaker.framework.FitnessEvaluator;
import org.uncommons.watchmaker.framework.GenerationalEvolutionEngine;
import org.uncommons.watchmaker.framework.SelectionStrategy;
import org.uncommons.watchmaker.framework.TerminationCondition;

import be.aga.dominionSimulator.DomBuyRule;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.gai.factory.BuyRuleListFactory;
import be.aga.dominionSimulator.gai.factory.EvolutionPipelineFactory;
import be.aga.dominionSimulator.gai.fitnesse.BotEvaluator;
import be.aga.dominionSimulator.gai.selection.BotRouletteSelection;
import be.aga.dominionSimulator.gai.selection.BotTournamentSelection;

public class GeneticAlgorithm {

	private static final int ELITES = 50;
	private static final int INITIAL_POPULATION_SIZE = 100;
	private static final double TOURNAMENT_PRESSURE = 0.9;

	private List<EvolutionObserver<List<DomBuyRule>>> observers;

	public GeneticAlgorithm() {
		observers = new ArrayList<>();
	}

	public void addObserver(EvolutionObserver<List<DomBuyRule>> observer) {
		observers.add(observer);
	}

	public void run(List<DomCardName> realm, List<DomBuyRule> opponent,
			TerminationCondition termination) {
		Probability prob = new Probability(TOURNAMENT_PRESSURE);
		SelectionStrategy<List<DomBuyRule>> selectionStrategy = new BotTournamentSelection(
				prob);

		run(realm, opponent, termination, new BotRouletteSelection());
	}

	public void run(List<DomCardName> realm, List<DomBuyRule> opponent,
			TerminationCondition termination,
			SelectionStrategy<List<DomBuyRule>> selectionStrategy) {

		CandidateFactory<List<DomBuyRule>> candidateFactory = new BuyRuleListFactory(
				realm);
		EvolutionaryOperator<List<DomBuyRule>> evolutionScheme = EvolutionPipelineFactory.INSTANCE
				.generatePipeline(realm);
		FitnessEvaluator<List<DomBuyRule>> fitnessEvaluator = new BotEvaluator(
				opponent);

		Random rng = new MersenneTwisterRNG();
		FitnessEvaluator<List<DomBuyRule>> cachedFE = new CachingFitnessEvaluator<>(
				fitnessEvaluator);
		EvolutionEngine<List<DomBuyRule>> engine = new GenerationalEvolutionEngine<>(
				candidateFactory, evolutionScheme, fitnessEvaluator,
				selectionStrategy, rng);

		attachObserver(engine);

		engine.evolve(INITIAL_POPULATION_SIZE, ELITES, termination);
	}

	private void attachObserver(EvolutionEngine<List<DomBuyRule>> engine) {
		observers.forEach(observer -> engine.addEvolutionObserver(observer));
	}
}
