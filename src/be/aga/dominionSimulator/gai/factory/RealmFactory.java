package be.aga.dominionSimulator.gai.factory;

import java.util.ArrayList;
import java.util.List;

import be.aga.dominionSimulator.enums.DomCardName;

public class RealmFactory {
	public List<DomCardName> generateSmithyRealm() {
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
