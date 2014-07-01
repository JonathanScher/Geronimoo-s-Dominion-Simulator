package be.aga.dominionSimulator.gai.factory;

import java.util.ArrayList;
import java.util.List;

import org.uncommons.maths.random.Probability;
import org.uncommons.watchmaker.framework.EvolutionaryOperator;
import org.uncommons.watchmaker.framework.operators.EvolutionPipeline;
import org.uncommons.watchmaker.framework.operators.ListCrossover;

import be.aga.dominionSimulator.DomBuyRule;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.gai.mutation.AddGene;
import be.aga.dominionSimulator.gai.mutation.ModifyOperand;
import be.aga.dominionSimulator.gai.mutation.ProtectedListInversion;
import be.aga.dominionSimulator.gai.mutation.RemoveGene;

public class EvolutionPipelineFactory {

	public static EvolutionPipelineFactory INSTANCE = new EvolutionPipelineFactory();

	private EvolutionPipelineFactory() {
	}

	public EvolutionPipeline<List<DomBuyRule>> generatePipeline(
			List<DomCardName> realm) {
		List<EvolutionaryOperator<List<DomBuyRule>>> pipeline = new ArrayList<>();
		pipeline.add(new ListCrossover<>());
		pipeline.add(new ProtectedListInversion<>(new Probability(0.2)));
		pipeline.add(new AddGene(realm, new Probability(0.2)));
		pipeline.add(new ModifyOperand(realm, new Probability(0.2)));
		pipeline.add(new RemoveGene(new Probability(0.2)));
		return new EvolutionPipeline<>(pipeline);
	}
}
