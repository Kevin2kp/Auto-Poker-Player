package pokergame.player.strategy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import pokergame.card.Card;
import pokergame.card.CardsUtil;
import pokergame.rules.PokerHands;

public class AipStrategy implements Strategy {

  private Collection<Card> cards;

  public AipStrategy() {
    this.cards = new TreeSet<>();
  }

  public AipStrategy(Collection<Card> cards) {
    this.cards = cards;
  }

  private Collection<Card> dropCardToCompleteRoyalFlush() {
    List<Collection<Card>> separatedBySuit = CardsUtil.splitCardsBySuit(cards).stream()
        .filter(lst -> lst.size() > 0)
        .sorted(Comparator.comparing(Collection::size))
        .collect(Collectors.toList());

    Collection<Card> badCards = separatedBySuit.get(0);
    Collection<Card> goodCards = separatedBySuit.get(separatedBySuit.size() - 1);

    //If there is a card of different suit, exchange

    if (badCards.size() != 0) {
      this.setCards(goodCards);
      return badCards;
    }

    //Else keep looking
    for (Card card : goodCards) {
      int rank = card.getRank();
      if (rank < 10 || rank > 14) {
        goodCards.remove(card);
        setCards(goodCards);
        return Collections.singleton(card);
      }
    }

    return Collections.emptyList();
  }

  private Collection<Card> dropCardToCompleteFlush() {
    List<Collection<Card>> separatedBySuit = CardsUtil.splitCardsBySuit(cards).stream()
        .filter(lst -> lst.size() > 0)
        .sorted(Comparator.comparing(Collection::size))
        .collect(Collectors.toList());

    Collection<Card> badCards = separatedBySuit.get(0);
    Collection<Card> goodCards = separatedBySuit.get(separatedBySuit.size() - 1);

    setCards(goodCards);
    return badCards;
  }

  private Collection<Card> dropCardToCompleteStraightFlush() {

    List<Collection<Card>> separatedBySuit = CardsUtil.splitCardsBySuit(cards).stream()
        .filter(lst -> lst.size() > 0)
        .sorted(Comparator.comparing(Collection::size))
        .collect(Collectors.toList());

    Collection<Card> badCards = separatedBySuit.get(0);
    Collection<Card> goodCards = separatedBySuit.get(separatedBySuit.size() - 1)
        .stream()
        .sorted(Comparator.comparing(Card::getRank))
        .collect(Collectors.toList());

    //If there is a card of different suit, exchange

    if (badCards.size() != 0) {
      this.setCards(goodCards);
      return badCards;
    }

    return Collections.emptyList();
  }

  private Collection<Card> dropCardToCompleteFullHouse() {

    List<Collection<Card>> separatedByRank = CardsUtil.splitCardsByRank(cards).stream()
        .filter(lst -> lst.size() > 0)
        .sorted(Comparator.comparing(Collection::size))
        .collect(Collectors.toList());

    //5 of a kind
    if (separatedByRank.size() == 1) {
      return Collections.emptyList();
    }

    int lastIndex = separatedByRank.size() - 1;
    Collection<Card> rank1 = separatedByRank.get(lastIndex);
    Collection<Card> rank2 = separatedByRank.get(lastIndex - 1);

    //Four of a kind

    if (rank1.size() == 4) {
      return Collections.emptyList();
    }

    //3 of a kind and something else

    if (rank1.size() == 3) {

      //Full house already

      if (rank2.size() == 2) {
        return Collections.emptyList();
      }

      Collection<Card> rank3 = separatedByRank.get(lastIndex - 2);
      Card card1 = rank2.iterator().next();
      Card card2 = rank3.iterator().next();
      setCards(rank1);


      ArrayList<Card> toDiscard = new ArrayList<>();
      toDiscard.add(card1);
      toDiscard.add(card2);

      return toDiscard;
    }

    //Two pairs

    ArrayList<Card> newHand = new ArrayList<>(rank1);
    newHand.addAll(rank2);
    setCards(newHand);

    return separatedByRank.get(lastIndex - 2);
  }

  private Collection<Card> dropAllButPair() {
    Collection<Card> pair = CardsUtil.splitCardsByRank(cards).stream()
        .max(Comparator.comparing(Collection::size)).get();
    Set<Card> cardsToExchange = new HashSet<>(cards);
    cardsToExchange.removeAll(pair);
    setCards(pair);
    return cardsToExchange;
  }

  private Collection<Card> dropCardToCompleteStraight() {
    Stream<Card> cardStream = cards.stream().sorted(Comparator.comparing(Card::getRank));
    Iterator<Card> iter = cardStream.iterator();

    Card first = null;
    Card second = null;
    Card last = null;
    Card cardtoChange = null;
    Collection<Card> cardsToKeep = new ArrayList<>();

    if (iter.hasNext()) {
      first = iter.next();
    }

    if (iter.hasNext()) {
      second = iter.next();
      cardsToKeep.add(second);
    }

    while (iter.hasNext()) {

      Card current = iter.next();

      if (!iter.hasNext()) {
        last = current;
        break;
      }

      cardsToKeep.add(current);
    }

    if (first.getRank() + 1 == second.getRank()) {
      cardsToKeep.add(first);
      cardtoChange = last;
    } else {
      cardsToKeep.add(last);
      cardtoChange = first;
    }

    setCards(cardsToKeep);
    return Collections.singleton(cardtoChange);
  }

  private Collection<Card> drop2Card2CompleteFlush() {
    Stream<Collection<Card>> splitBySuit = CardsUtil.splitCardsBySuit(cards).stream()
        .sorted(Comparator.comparing(Collection::size));

    ArrayList<Card> toDrop = new ArrayList<>();
    ArrayList<Card> cardstoKeep = new ArrayList<>();
    Iterator<Collection<Card>> iter = splitBySuit.iterator();
    while (iter.hasNext()) {

      Collection<Card> current = iter.next();

      //Last

      if (!iter.hasNext()) {
        cardstoKeep.addAll(current);
        break;
      }

      toDrop.addAll(current);
    }

    setCards(cardstoKeep);
    return toDrop;
  }

  private Collection<Card> drop2Card2CompleteStraight() {
    Collection<Card> cardsToKeep = CardsUtil.getCardsInLongestSequence(cards);
    Set<Card> cardsToExchange = new HashSet<>(cards);
    cardsToExchange.removeAll(cardsToKeep);
    setCards(cardsToKeep);
    return cardsToExchange;
  }

  private Collection<Card> keepHighestTwo() {
    TreeSet<Card> cardsToExchange = new TreeSet<>(cards);
    ArrayList<Card> cardsToKeep = new ArrayList<>();
    cardsToKeep.add(cardsToExchange.pollLast());
    cardsToKeep.add(cardsToExchange.pollLast());
    setCards(cardsToKeep);
    return cardsToExchange;
  }

  private boolean hasGoodHand() {

    if (PokerHands.isRoyalFlush(cards)) {
      return true;
    }

    if (PokerHands.isStraightFlush(cards)) {
      return true;
    }

    if (PokerHands.isFourOfAKind(cards)) {
      return true;
    }

    if (PokerHands.isFullHouse(cards)) {
      return true;
    }

    if (PokerHands.isFlush(cards)) {
      return true;
    }

    return PokerHands.isStraight(cards);
  }

  private Collection<Card> tryToCompleteGoodHand() {
    if (StrategyUtil.cardsNeededToCompleteRoyalFlush(cards) == 1) {
      return dropCardToCompleteRoyalFlush();
    }

    if (StrategyUtil.cardsNeededToCompleteStraightFlush(cards) == 1) {
      return dropCardToCompleteStraightFlush();
    }

    if (StrategyUtil.cardsNeededToCompleteFullHouse(cards) == 1) {
      return dropCardToCompleteFullHouse();
    }

    if (StrategyUtil.cardsNeededToCompleteFlush(cards) == 1) {
      return dropCardToCompleteFlush();
    }

    if (StrategyUtil.cardsNeededToCompleteStraight(cards) == 1) {
      return dropCardToCompleteStraight();
    }

    return Collections.emptyList();
  }

  @Override
  public Collection<Card> processTurn() {

    if (hasGoodHand()) {
      return Collections.emptyList();
    }

    Collection<Card> discarded = tryToCompleteGoodHand();
    if (!discarded.equals(Collections.EMPTY_LIST)) {
      return discarded;
    }

    if (CardsUtil.highestNumberSameSuit(cards) == 3) {
      return drop2Card2CompleteFlush();
    }

    /*
    if (PokerHands.isThreeOfAKind(cards)) {
      //return Never reached, overlaps with 1 card away from full house
    }
     */

    if (CardsUtil.longestCardSequence(cards) == 3) {
      return drop2Card2CompleteStraight();
    }

    /*
    if (PokerHands.isTwoPair(cards)) {
      //Never reached, overlaps with 1 card away from full house
      return;
    }
     */

    if (PokerHands.isOnePair(cards)) {
      return dropAllButPair();
    }

    return keepHighestTwo();
  }

  @Override
  public Collection<Card> getCards() {
    return this.cards;
  }

  @Override
  public void setCards(Collection<Card> cards) {
    this.cards = new TreeSet<>(cards);
  }

  @Override
  public void accept(Collection<Card> cards) {
    this.cards.addAll(cards);
  }
}
