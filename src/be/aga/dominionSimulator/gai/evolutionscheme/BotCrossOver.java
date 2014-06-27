package be.aga.dominionSimulator.gai.evolutionscheme;

import java.util.List;
import java.util.Random;

import org.uncommons.maths.random.Probability;
import org.uncommons.watchmaker.framework.operators.AbstractCrossover;

public class BotCrossOver extends AbstractCrossover {
	public BotCrossOver(int crossoverPoints) {
		super(crossoverPoints);
	}

	public BotCrossOver(int crossoverPoints, Probability crossoverProbability) {
		super(crossoverPoints, crossoverProbability);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected List mate(Object parent1, Object parent2,
			int numberOfCrossoverPoints, Random rng) {
		// TODO Auto-generated method stub
		return null;
	}

}
