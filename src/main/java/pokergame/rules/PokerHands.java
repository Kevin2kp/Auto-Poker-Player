package pokergame.rules;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeSet;
import pokergame.card.Card;
import pokergame.card.Card.Suit;
import pokergame.card.CardsUtil;

public class PokerHands {

  private static void validateInputHands(Collection<Card> cards1, Collection<Card> cards2) {
    if (cards1.size() != 5 || cards2.size() != 5) {
      throw new IllegalArgumentException("Both set of hands must contain 5 cards each");
    }
  }

  public static boolean isRoyalFlush(Collection<Card> cards) {

    Collection<Collection<Card>> cardsOfSameSuit = CardsUtil.splitCardsBySuit(cards);
    Collection<Collection<Card>> straightFlushes = new ArrayList<>();
    for (Collection<Card> suit : cardsOfSameSuit) {
      if (isStraightFlush(suit)) {
        straightFlushes.add(suit);
      }
    }

    for (Collection<Card> straightFlush : straightFlushes) {
      Map<Integer, Integer> rankFrequencies = CardsUtil.getRankFrequencies(straightFlush);
      boolean isRoyalFlush =
          rankFrequencies.containsKey(10)
              && rankFrequencies.containsKey(11)
              && rankFrequencies.containsKey(12)
              && rankFrequencies.containsKey(13)
              && rankFrequencies.containsKey(14);

      if (isRoyalFlush) {
        return true;
      }
    }

    return false;
  }

  public static boolean isStraightFlush(Collection<Card> cards) {

    //List-ception

    Collection<Collection<Card>> cardsOfSameSuit = CardsUtil.splitCardsBySuit(cards);

    for (Collection<Card> suit : cardsOfSameSuit) {
      boolean isStraight = isStraight(suit);
      boolean isFlush = isFlush(suit);

      if (isStraight && isFlush) {
        return true;
      }
    }

    return false;
  }

  public static boolean isStraight(Collection<Card> cards) {
    return CardsUtil.longestCardSequence(cards) >= 5;
  }

  public static boolean isFourOfAKind(Collection<Card> cards) {
    Map<Integer, Integer> rankFrequency = CardsUtil.getRankFrequencies(cards);
    return rankFrequency.size() > 0 && Collections.max(rankFrequency.values()) >= 4;
  }

  public static boolean isFullHouse(Collection<Card> cards) {
    Map<Integer, Integer> rankFrequencies = CardsUtil.getRankFrequencies(cards);
    return rankFrequencies.size() > 0 && rankFrequencies.containsValue(2) && rankFrequencies
        .containsValue(3);
  }

  public static boolean isFlush(Collection<Card> cards) {
    Map<Suit, Integer> suitFrequency = CardsUtil.getSuitFrequencies(cards);
    return suitFrequency.size() > 0 && Collections.max(suitFrequency.values()) >= 5;
  }

  public static boolean isThreeOfAKind(Collection<Card> cards) {
    Map<Integer, Integer> rankFrequencies = CardsUtil.getRankFrequencies(cards);
    return rankFrequencies.size() > 0 && Collections.max(rankFrequencies.values()) >= 3;
  }

  public static boolean isTwoPair(Collection<Card> cards) {
    Map<Integer, Integer> rankFrequencies = CardsUtil.getRankFrequencies(cards);

    int ranksRepeatedMoreThanOnce = 0;
    for (Integer rankFreq : rankFrequencies.values()) {
      if (rankFreq >= 2) {
        ranksRepeatedMoreThanOnce++;
      }
    }
    return rankFrequencies.size() > 0 && ranksRepeatedMoreThanOnce >= 2;
  }


  public static boolean isOnePair(Collection<Card> cards) {

    Map<Integer, Integer> rankFrequencies = CardsUtil.getRankFrequencies(cards);
    return rankFrequencies.size() > 0 && Collections.max(rankFrequencies.values()) >= 2;
  }

  public static int compareRoyalFlush(Collection<Card> cards1, Collection<Card> cards2) {
    int suitValueCards1 = cards1.iterator().next().getSuit().getRank();
    int suitValueCards2 = cards2.iterator().next().getSuit().getRank();
    return suitValueCards1 - suitValueCards2;
  }

  public static int compareStraightFlush(Collection<Card> cards1, Collection<Card> cards2) {
    validateInputHands(cards1, cards2);
    TreeSet<Card> sorted1 = new TreeSet<>(cards1);
    TreeSet<Card> sorted2 = new TreeSet<>(cards2);

    Card lowest1 = sorted1.pollFirst();
    Card lowest2 = sorted2.pollFirst();

    return lowest1.compareTo(lowest2);
  }

  public static int compareFlush(Collection<Card> cards1, Collection<Card> cards2) {
    validateInputHands(cards1, cards2);

    TreeSet<Card> sorted1 = new TreeSet<>(cards1);
    TreeSet<Card> sorted2 = new TreeSet<>(cards2);

    int diff;
    do {
      Card current1 = sorted1.pollLast();
      Card current2 = sorted2.pollLast();

      diff = current1.compareTo(current2);

      if (diff != 0) {
        return diff;
      }

    } while (sorted1.size() > 0);

    return diff;
  }

  public static int compareStraight(Collection<Card> cards1, Collection<Card> cards2) {
    validateInputHands(cards1, cards2);

    TreeSet<Card> sorted1 = new TreeSet<>(cards1);
    TreeSet<Card> sorted2 = new TreeSet<>(cards2);

    Card lowest1 = sorted1.pollFirst();
    Card lowest2 = sorted2.pollFirst();

    Card highest1 = sorted1.last();
    Card highest2 = sorted2.last();

    if (lowest1.getRank() == 2 && highest1.getRank() == 14) {
      highest1 = sorted1.lower(highest1);
    }

    if (lowest2.getRank() == 2 && highest2.getRank() == 14) {
      highest2 = sorted2.lower(highest2);
    }

    return highest1.compareTo(highest2);
  }

  public static int compareFullHouse(Collection<Card> cards1, Collection<Card> cards2) {
    validateInputHands(cards1, cards2);

    Collection<Collection<Card>> splitByRank1 = CardsUtil.splitCardsByRank(cards1);
    Collection<Collection<Card>> splitByRank2 = CardsUtil.splitCardsByRank(cards2);

    Collection<Card> triplet1 = Collections
        .max(splitByRank1, Comparator.comparing(Collection::size));
    Collection<Card> triplet2 = Collections
        .max(splitByRank2, Comparator.comparing(Collection::size));

    int rank1 = triplet1.iterator().next().getRank();
    int rank2 = triplet2.iterator().next().getRank();

    return rank1 - rank2;
  }

  public static int compareFourOfAKind(Collection<Card> cards1, Collection<Card> cards2) {
    validateInputHands(cards1, cards2);

    Collection<Card> fourOfKind1 = CardsUtil.splitCardsByRank(cards1).stream()
        .max(Comparator.comparing(Collection::size)).get();

    Collection<Card> fourOfKind2 = CardsUtil.splitCardsByRank(cards2).stream()
        .max(Comparator.comparing(Collection::size)).get();

    return fourOfKind1.iterator().next().getRank() - fourOfKind2.iterator().next().getRank();
  }

  public static int compareThreeOfAKind(Collection<Card> cards1, Collection<Card> cards2) {
    validateInputHands(cards1, cards2);

    Collection<Card> threeOfKind1 = CardsUtil.splitCardsByRank(cards1).stream()
        .max(Comparator.comparing(Collection::size)).get();

    Collection<Card> threeOfKind2 = CardsUtil.splitCardsByRank(cards2).stream()
        .max(Comparator.comparing(Collection::size)).get();

    return threeOfKind1.iterator().next().getRank() - threeOfKind2.iterator().next().getRank();
  }

  public static int compareTwoPair(Collection<Card> cards1, Collection<Card> cards2) {

    Comparator<Collection<Card>> comparator = Comparator
        .comparingInt(lst -> lst.iterator().next().getRank());

    Collection<Card> bestPair1 = CardsUtil.splitCardsByRank(cards1).stream()
        .filter(lst -> lst.size() > 1)
        .max(comparator).get();

    Collection<Card> bestPair2 = CardsUtil.splitCardsByRank(cards2).stream()
        .filter(lst -> lst.size() > 1)
        .max(comparator).get();

    Card bestCard1 = Collections.max(bestPair1);
    Card bestCard2 = Collections.max(bestPair2);

    return bestCard1.compareTo(bestCard2);
  }

  public static int comparePair(Collection<Card> cards1, Collection<Card> cards2) {
    Collection<Card> pair1 = CardsUtil.splitCardsByRank(cards1).stream()
        .max(Comparator.comparing(Collection::size)).get();

    Collection<Card> pair2 = CardsUtil.splitCardsByRank(cards2).stream()
        .max(Comparator.comparing(Collection::size)).get();

    Card bestCard1 = Collections.max(pair1);
    Card bestCard2 = Collections.max(pair2);

    return bestCard1.compareTo(bestCard2);
  }

  public static int compareHighestCard(Collection<Card> cards1, Collection<Card> cards2) {
    Card best1 = Collections.max(cards1);
    Card best2 = Collections.max(cards2);
    return best1.compareTo(best2);
  }

  public enum Type implements Comparable<Type> {
    HIGH_CARD(0, "High card"),
    PAIR(1, "Pair"),
    TWO_PAIRS(2, "Two pairs"),
    THREE_OF_A_KIND(3, "Three of a kind"),
    STRAIGHT(4, "Straight"),
    FLUSH(5, "Flush"),
    FULL_HOUSE(6, "Full house"),
    FOUR_OF_A_KIND(7, "Four of a kind"),
    STRAIGHT_FLUSH(8, "Straight flush"),
    ROYAL_FLUSH(9, "Royal flush");

    final int value;
    final String name;

    Type(int value, String name) {
      this.value = value;
      this.name = name;
    }

    public int getValue() {
      return value;
    }

    public String getName() {
      return name;
    }
  }

  public static Type handType(Collection<Card> cards) {
    if (PokerHands.isRoyalFlush(cards)) {
      return Type.ROYAL_FLUSH;
    }

    if (PokerHands.isStraightFlush(cards)) {
      return Type.STRAIGHT_FLUSH;
    }

    if (PokerHands.isFourOfAKind(cards)) {
      return Type.FOUR_OF_A_KIND;
    }

    if (PokerHands.isFullHouse(cards)) {
      return Type.FULL_HOUSE;
    }

    if (PokerHands.isFlush(cards)) {
      return Type.FLUSH;
    }

    if (PokerHands.isStraight(cards)) {
      return Type.STRAIGHT;
    }

    if (PokerHands.isThreeOfAKind(cards)) {
      return Type.THREE_OF_A_KIND;
    }

    if (PokerHands.isTwoPair(cards)) {
      return Type.TWO_PAIRS;
    }

    if (PokerHands.isOnePair(cards)) {
      return Type.PAIR;
    }

    return Type.HIGH_CARD;
  }

  public static int compareHands(Collection<Card> cards1, Collection<Card> cards2) {

    Type p1HandType = handType(cards1);
    Type p2HandType = handType(cards2);

    int result;
    if (p1HandType == p2HandType) {
      switch (p1HandType) {
        case HIGH_CARD:
          result = PokerHands.compareHighestCard(cards1, cards2);
          break;
        case PAIR:
          result = PokerHands.comparePair(cards1, cards2);
          break;
        case TWO_PAIRS:
          result = PokerHands.compareTwoPair(cards1, cards2);
          break;
        case THREE_OF_A_KIND:
          result = PokerHands.compareThreeOfAKind(cards1, cards2);
          break;
        case STRAIGHT:
          result = PokerHands.compareStraight(cards1, cards2);
          break;
        case FLUSH:
          result = PokerHands.compareFlush(cards1, cards2);
          break;
        case FULL_HOUSE:
          result = PokerHands.compareFullHouse(cards1, cards2);
          break;
        case FOUR_OF_A_KIND:
          result = PokerHands.compareFourOfAKind(cards1, cards2);
          break;
        case STRAIGHT_FLUSH:
          result = PokerHands.compareStraightFlush(cards1, cards2);
          break;
        case ROYAL_FLUSH:
          result = PokerHands.compareRoyalFlush(cards1, cards2);
          break;
        default:
          result = 0;
      }

      return result;
    }

    return p1HandType.getValue() - p2HandType.getValue();
  }
}
