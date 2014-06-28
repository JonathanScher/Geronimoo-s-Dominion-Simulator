package be.aga.dominionSimulator.gai;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.uncommons.maths.random.MersenneTwisterRNG;
import org.uncommons.maths.random.Probability;
import org.uncommons.watchmaker.framework.CandidateFactory;
import org.uncommons.watchmaker.framework.EvolutionEngine;
import org.uncommons.watchmaker.framework.EvolutionObserver;
import org.uncommons.watchmaker.framework.EvolutionaryOperator;
import org.uncommons.watchmaker.framework.FitnessEvaluator;
import org.uncommons.watchmaker.framework.GenerationalEvolutionEngine;
import org.uncommons.watchmaker.framework.PopulationData;
import org.uncommons.watchmaker.framework.SelectionStrategy;
import org.uncommons.watchmaker.framework.operators.EvolutionPipeline;
import org.uncommons.watchmaker.framework.operators.ListCrossover;
import org.uncommons.watchmaker.framework.termination.TargetFitness;

import be.aga.dominionSimulator.DomBuyRule;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.gai.factory.BuyRuleListFactory;
import be.aga.dominionSimulator.gai.fitnesse.BotEvaluator;
import be.aga.dominionSimulator.gai.mutation.AddGene;
import be.aga.dominionSimulator.gai.mutation.ModifyOperand;
import be.aga.dominionSimulator.gai.mutation.ProtectedListInversion;
import be.aga.dominionSimulator.gai.mutation.RemoveGene;
import be.aga.dominionSimulator.gai.selection.BotTournamentSelection;

public class GeneticAlgorithm {

	private static final double TARGET_FITNESSE = 1.3;
	private static final int INITIAL_POPULATION_SIZE = 10;
	private static final double TOURNAMENT_PRESSURE = 0.9;

	public GeneticAlgorithm() {
	}

	public void runGeneticAlgorithm() {
		List<DomCardName> realm = smithyRealm();
		List<DomBuyRule> bigMoney = getBigMoney();
		Probability prob = new Probability(TOURNAMENT_PRESSURE);

		List<EvolutionaryOperator<List<DomBuyRule>>> pipeline = new ArrayList<>();
		pipeline.add(new ListCrossover<>());
		pipeline.add(new ProtectedListInversion<>(new Probability(0.2)));
		pipeline.add(new AddGene(realm, new Probability(0.2)));
		pipeline.add(new ModifyOperand(realm, new Probability(0.2)));
		pipeline.add(new RemoveGene(new Probability(0.2)));
		CandidateFactory<List<DomBuyRule>> candidateFactory = new BuyRuleListFactory(
				realm);
		EvolutionaryOperator<List<DomBuyRule>> evolutionScheme = new EvolutionPipeline<>(
				pipeline);
		FitnessEvaluator<List<DomBuyRule>> fitnessEvaluator = new BotEvaluator(
				bigMoney);
		SelectionStrategy<List<DomBuyRule>> selectionStrategy = new BotTournamentSelection(
				prob);
		Random rng = new MersenneTwisterRNG();

		EvolutionEngine<List<DomBuyRule>> engine = new GenerationalEvolutionEngine<>(
				candidateFactory, evolutionScheme, fitnessEvaluator,
				selectionStrategy, rng);
		
		engine.addEvolutionObserver(new EvolutionObserver<List<DomBuyRule>>()
				{
				    public void populationUpdate(PopulationData<? extends List<DomBuyRule>> data)
				    {
				        System.out.printf("Generation %d: %s\n",
				                          data.getGenerationNumber(),
				                          data.getBestCandidate());
				    }
				});
		
		List<DomBuyRule> result = engine.evolve(INITIAL_POPULATION_SIZE, 0, new TargetFitness(TARGET_FITNESSE, true));
		DomPlayer toXML = new DomPlayer("solution");
		toXML.addBuyRules(result);
		System.out.println(toXML.getXML());
	}

	public List<DomBuyRule> getBigMoney() {
		List<DomBuyRule> bigMoney = new ArrayList<>();
		bigMoney.add(new DomBuyRule(DomCardName.Province));
		bigMoney.add(new DomBuyRule(DomCardName.Gold));
		bigMoney.add(new DomBuyRule(DomCardName.Silver));
		return bigMoney;
	}

	public List<DomCardName> smithyRealm() {
		List<DomCardName> realm = new ArrayList<DomCardName>();
		realm.add(DomCardName.Province);
		realm.add(DomCardName.Duchy);
		realm.add(DomCardName.Estate);
		realm.add(DomCardName.Gold);
		realm.add(DomCardName.Silver);
		realm.add(DomCardName.Copper);
		realm.add(DomCardName.Smithy);
		return realm;

	}
}
