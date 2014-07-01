package be.aga.dominionSimulator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PileOfCards implements Iterable<DomCard> {
	private List<DomCard> cards = new ArrayList<DomCard>();

	private PileOfCardsObserver observer;

	public PileOfCards(PileOfCardsObserver observer) {
		this.observer = observer;
	}

	@Override
	public Iterator<DomCard> iterator() {
		return cards.iterator();
	}

	public void add(DomCard card) {
		if(cards.isEmpty()) {
			observer.notEmpty();
		}
		cards.add(card);
	}

	public boolean isEmpty() {
		return cards.isEmpty();
	}

	public DomCard remove(int i) {
		DomCard result = cards.remove(i);
		if(cards.isEmpty()) {
			observer.empty();
		}
		return result;
	}

	public int size() {
		return cards.size();
	}

}
