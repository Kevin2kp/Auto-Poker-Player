package pokergame.player.strategy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.TreeSet;
import pokergame.card.Card;

public class DefaultStrategy implements Strategy {

  private Collection<Card> cards;

  public DefaultStrategy() {
    this.cards = new TreeSet<>();
  }

  public DefaultStrategy(Collection<Card> cards) {
    this.cards = new TreeSet<>(cards);
  }

  @Override
  public Collection<Card> processTurn() {
    return Collections.emptyList();
  }

  @Override
  public Collection<Card> getCards() {
    return cards;
  }

  @Override
  public void setCards(Collection<Card> cards) {
    this.cards = new ArrayList<>(cards);
  }

  @Override
  public void accept(Collection<Card> cards) {
    this.cards.addAll(cards);
  }
}
