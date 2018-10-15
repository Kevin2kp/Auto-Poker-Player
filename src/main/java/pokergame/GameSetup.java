package pokergame;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import pokergame.card.Card;

public class GameSetup {

  private static final int MAX_ROUNDS_DEF = 5;
  private static List<Card> cards;
  private static int maxRounds = MAX_ROUNDS_DEF;
  private static boolean shuffleDeck = true;

  public static void setDeckCards(Collection<Card> deckCards) {
    cards = new ArrayList<>(deckCards);
  }

  public static void setMaxRounds(int maxRounds) {
    GameSetup.maxRounds = maxRounds;
  }

  public static void shuffleDeck(boolean shuffleDeck) {
    GameSetup.shuffleDeck = shuffleDeck;
  }

  public static PokerGame getGame() {
    if (shuffleDeck) {
      Collections.shuffle(cards);
    }

    PokerGame game = new PokerGame(cards);
    game.setMaxRounds(maxRounds);

    return game;
  }
}
