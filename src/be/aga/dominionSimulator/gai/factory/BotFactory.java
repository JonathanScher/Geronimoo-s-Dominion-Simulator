package be.aga.dominionSimulator.gai.factory;

import java.util.List;

import be.aga.dominionSimulator.DomBuyRule;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.gai.BuyStrategy;

public class BotFactory {
	public List<DomBuyRule> generateBigMoney() {
		List<DomBuyRule> bigMoney = new BuyStrategy();
		bigMoney.add(new DomBuyRule(DomCardName.Province));
		bigMoney.add(new DomBuyRule(DomCardName.Gold));
		bigMoney.add(new DomBuyRule(DomCardName.Silver));
		return bigMoney;
	}

}
