package be.aga.dominionSimulator.gai.mutation;

import java.util.List;
import java.util.Random;

import org.uncommons.maths.random.Probability;

import be.aga.dominionSimulator.DomBuyCondition;
import be.aga.dominionSimulator.DomBuyRule;
import be.aga.dominionSimulator.Operand;
import be.aga.dominionSimulator.enums.DomBotFunction;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.gai.factory.BuyConditionFactory;
import be.aga.dominionSimulator.gai.factory.OperandFactory;

public class ModifyOperand extends AbstractMutation {

	private BuyConditionFactory conditionFactory;
	private OperandFactory operandFactory;

	public ModifyOperand(List<DomCardName> realm, Probability probability) {
		super(probability);
		conditionFactory = new BuyConditionFactory(realm);
		operandFactory = new OperandFactory(realm);
	}

	public void mutate(List<DomBuyRule> element, Random rng) {
		Integer size = element.size();
		Integer indexOfGeneToMutate = 0;
		if (size > 0) {
			if (size > 1) {
				indexOfGeneToMutate = rng.nextInt(size - 1);
			}

			DomBuyRule geneToMutate = element.get(indexOfGeneToMutate);
			DomBuyCondition conditionToMutate = geneToMutate.getBuyConditions()
					.get(0);
			Operand left = conditionToMutate.getLeft();
			if (DomBotFunction.isActionPhase.equals(left.getFunction())) {
				conditionToMutate = conditionFactory
						.generateRandomBuyCondition(rng);
			} else {
				conditionToMutate.setRight(operandFactory
						.generateRandomOperand(rng, true));
			}
		}
	}
}
