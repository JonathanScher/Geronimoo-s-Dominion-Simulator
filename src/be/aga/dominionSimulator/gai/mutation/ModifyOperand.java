package be.aga.dominionSimulator.gai.mutation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.uncommons.watchmaker.framework.EvolutionaryOperator;

import be.aga.dominionSimulator.DomBuyCondition;
import be.aga.dominionSimulator.DomBuyRule;
import be.aga.dominionSimulator.Operand;
import be.aga.dominionSimulator.enums.DomBotFunction;
import be.aga.dominionSimulator.gai.factory.BuyConditionFactory;
import be.aga.dominionSimulator.gai.factory.OperandFactory;

public class ModifyOperand implements EvolutionaryOperator<List<DomBuyRule>> {

	BuyConditionFactory conditionFactory;
	OperandFactory operandFactory;

	@Override
	public List<List<DomBuyRule>> apply(
			List<List<DomBuyRule>> selectedCandidates, Random rng) {
		List<List<DomBuyRule>> newGuys = new ArrayList<>(selectedCandidates);
		newGuys.parallelStream().forEach(x -> mutate(x, rng));
		return newGuys;
	}

	public void mutate(List<DomBuyRule> element, Random rng) {
		Integer indexOfGeneToMutate = rng.nextInt(element.size() - 1);
		DomBuyRule geneToMutate = element.get(indexOfGeneToMutate);
		DomBuyCondition conditionToMutate = geneToMutate.getBuyConditions()
				.get(0);
		Operand left = conditionToMutate.getLeft();
		if (DomBotFunction.isActionPhase.equals(left.getFunction())) {
			conditionToMutate = conditionFactory
					.generateRandomBuyCondition(rng);
		} else {
			conditionToMutate.setRight(operandFactory.generateRandomOperand(
					rng, true));
		}
	}
}
