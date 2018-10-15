package pokergame.player.strategy;

import java.util.Collection;
import java.util.function.Consumer;
import pokergame.card.Card;

public interface Strategy extends Consumer<Collection<Card>> {

  Collection<Card> processTurn();

  Collection<Card> getCards();

  void setCards(Collection<Card> cards);
}
