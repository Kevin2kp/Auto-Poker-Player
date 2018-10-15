package pokergame.player;

import java.util.Collection;
import java.util.function.Consumer;
import pokergame.card.Card;
import pokergame.player.strategy.DefaultStrategy;
import pokergame.player.strategy.Strategy;

public class PokerPlayer implements Consumer<Collection<Card>> {

  private final Strategy strategy;
  private final String id;

  public PokerPlayer() {
    this("", new DefaultStrategy());
  }

  public PokerPlayer(String id) {
    this(id, new DefaultStrategy());
  }

  public PokerPlayer(String id, Strategy strategy) {
    this.id = id;
    this.strategy = strategy;
  }

  public Collection<Card> getCards() {
    return strategy.getCards();
  }

  public void setCards(Collection<Card> cards) {
    this.strategy.setCards(cards);
  }

  public Collection<Card> processTurn() {
    return strategy.processTurn();
  }

  public int missingCards() {
    return 5 - strategy.getCards().size();
  }

  @Override
  public void accept(Collection<Card> cards) {
    this.strategy.accept(cards);
  }

  @Override
  public String toString() {
    return id;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || o.getClass() != this.getClass()) {
      return false;
    }

    PokerPlayer other = (PokerPlayer) o;
    return id.equals(other.id);
  }
}
