package pokergame;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import pokergame.card.Card;
import pokergame.player.PokerPlayer;
import pokergame.player.strategy.AipStrategy;
import pokergame.rules.PokerHands;

public class PokerGame {

  private int maxRounds = 5;
  private final PokerPlayer opponent;
  private final PokerPlayer autoPlayer;
  private final PokerPlayer[] players;
  private int currentRound = 0;
  private int activePlayerIndex = 0;
  private int playerReceivingCardsIndex = 0;
  private LinkedList<Card> deck;
  private Collection<Card> discardedByLastPlayer;
  private Collection<Card> receivedByLastPlayer;

  PokerGame(Collection<Card> cards) {
    deck = new LinkedList<>(cards);
    opponent = new PokerPlayer("Opponent");
    autoPlayer = new PokerPlayer("Aip Strategy", new AipStrategy());
    players = new PokerPlayer[]{opponent, autoPlayer};
    dealInitialHands();
  }

  public boolean isGameOver() {
    return currentRound == maxRounds;
  }

  public boolean allPlayersDone() {
    return activePlayerIndex >= players.length;
  }

  public boolean doneDealing() {
    return playerReceivingCardsIndex >= players.length;
  }

  public PokerPlayer activePlayer() {
    return players[activePlayerIndex];
  }

  public int getCurrentRound() {
    return currentRound;
  }

  public void setDeckCards(Collection<Card> deckCards) {
    this.deck = new LinkedList<>(deckCards);
  }

  public Collection<Card> cardsDiscardedByLastPlayer() {
    return discardedByLastPlayer;
  }

  public Collection<Card> cardsReceivedByLastPlayer() {
    return receivedByLastPlayer;
  }

  public void setMaxRounds(int maxRounds) {
    this.maxRounds = maxRounds;
  }

  public void nextPlayerTurn() {
    PokerPlayer currentPlayer = players[activePlayerIndex++];
    discardedByLastPlayer = currentPlayer.processTurn();
  }

  private void dealInitialHands() {
    for (PokerPlayer player : players) {

      LinkedList<Card> cards = new LinkedList<>();
      while (cards.size() != 5) {
        cards.add(deck.pollFirst());
      }

      player.setCards(cards);
    }
  }

  public void dealCards() {

    PokerPlayer player = players[playerReceivingCardsIndex++];
    int missingCards = player.missingCards();
    if (missingCards == 0) {
      receivedByLastPlayer = Collections.emptyList();
      return;
    }

    Collection<Card> newCards = new LinkedList<>();
    while (missingCards-- > 0 && deck.size() > 0) {
      newCards.add(deck.pollFirst());
    }

    receivedByLastPlayer = newCards;
    player.accept(newCards);
  }

  /**
   * Ends the current round. Resets active player.
   */
  public void endRound() {

    activePlayerIndex = 0;
    playerReceivingCardsIndex = 0;
    currentRound++;
  }

  public PokerPlayer computeWinner() {
    int diff = PokerHands.compareHands(opponent.getCards(), autoPlayer.getCards());
    return diff < 0 ? autoPlayer : opponent;
  }
}
