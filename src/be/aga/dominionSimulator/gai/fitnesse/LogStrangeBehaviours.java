package be.aga.dominionSimulator.gai.fitnesse;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.uncommons.watchmaker.framework.FitnessEvaluator;

import be.aga.dominionSimulator.DomBuyRule;
import be.aga.dominionSimulator.gai.BuyStrategy;

import com.google.common.collect.MapMaker;

public class LogStrangeBehaviours implements FitnessEvaluator<List<DomBuyRule>> {
	private static final int ABNORMAL_BEHAVIOUR_THRESHOLD = 15;
	private static final Logger LOGGER = Logger
			.getLogger(LogStrangeBehaviours.class);
	private final FitnessEvaluator<List<DomBuyRule>> delegate;

	// This field is marked as transient, even though the class is not
	// Serializable, because
	// Terracotta will respect the fact it is transient and not try to share it.
	private final transient ConcurrentMap<List<DomBuyRule>, Double> cache = new MapMaker()
			.weakKeys().makeMap();

	/**
	 * Creates a caching fitness evaluator that wraps the specified evaluator.
	 * 
	 * @param delegate
	 *            The fitness evaluator that performs the actual calculations.
	 */
	public LogStrangeBehaviours(FitnessEvaluator<List<DomBuyRule>> delegate) {
		this.delegate = delegate;
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * This implementation performs a cache look-up every time it is invoked. If
	 * the fitness evaluator has already calculated the fitness score for the
	 * specified candidate that score is returned without delegating to the
	 * wrapped evaluator.
	 * </p>
	 */
	public double getFitness(List<DomBuyRule> candidate,
			List<? extends List<DomBuyRule>> population) {
		Double cachedFitness = cache.get(candidate);
		Double fitness = delegate.getFitness(candidate, population);
		cache.put(candidate, fitness);

		if (LOGGER.isInfoEnabled()) {
			if (cachedFitness != null && Math.abs(fitness - cachedFitness) > ABNORMAL_BEHAVIOUR_THRESHOLD) {
				LOGGER.info("anormal behaviour for candidate"
						+ candidate.hashCode() + ": " + cachedFitness
						+ " becomes " + fitness);
				BuyStrategy strategy = new BuyStrategy(candidate);
				LOGGER.info(strategy);
				LOGGER.setLevel(Level.INFO);
				BotEvaluator.LOGGER.setLevel(Level.INFO);
			}
		}

		return fitness;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isNatural() {
		return delegate.isNatural();
	}
}