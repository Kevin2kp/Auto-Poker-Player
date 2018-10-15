package pokergame.card;

import static pokergame.card.Card.Suit.Clubs;
import static pokergame.card.Card.Suit.Diamonds;
import static pokergame.card.Card.Suit.Hearts;
import static pokergame.card.Card.Suit.Spades;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import pokergame.card.Card.Suit;

public class CardsUtil {

  public static Collection<Card> getCardsOfSuit(Suit suit, Collection<Card> cards) {
    return cards.stream().filter(card -> card.getSuit().equals(suit)).collect(Collectors.toList());
  }

  public static Collection<Card> getCardsOfRank(int rank, Collection<Card> cards) {
    return cards.stream().filter(card -> card.getRank() == rank).collect(Collectors.toList());
  }

  public static Map<Integer, Integer> getRankFrequencies(Collection<Card> cards) {
    Map<Integer, Integer> rankCount = new TreeMap<>();
    for (Card card : cards) {
      Integer count = rankCount.putIfAbsent(card.getRank(), 1);
      if (count != null) {
        rankCount.put(card.getRank(), ++count);
      }
    }

    return rankCount;
  }

  public static Map<Suit, Integer> getSuitFrequencies(Collection<Card> cards) {
    Map<Suit, Integer> suitFrequencies = new TreeMap<>();
    for (Card card : cards) {
      Integer count = suitFrequencies.putIfAbsent(card.getSuit(), 1);
      if (count != null) {
        suitFrequencies.put(card.getSuit(), ++count);
      }
    }

    return suitFrequencies;
  }

  public static Collection<Collection<Card>> splitCardsByRank(Collection<Card> cards) {
    Collection<Collection<Card>> splitCards = new ArrayList<>();
    Set<Integer> ranks = cards.stream().map(Card::getRank).collect(Collectors.toSet());
    for (int i : ranks) {
      splitCards.add(getCardsOfRank(i, cards));
    }

    return splitCards;
  }

  public static Collection<Collection<Card>> splitCardsBySuit(Collection<Card> cards) {
    Collection<Collection<Card>> splitCards = new ArrayList<>();
    splitCards.add(getCardsOfSuit(Clubs, cards));
    splitCards.add(getCardsOfSuit(Diamonds, cards));
    splitCards.add(getCardsOfSuit(Hearts, cards));
    splitCards.add(getCardsOfSuit(Spades, cards));
    return splitCards;
  }

  public static int highestNumberSameSuit(Collection<Card> cards) {

    Map<Suit, Integer> suitCount = getSuitFrequencies(cards);
    if (suitCount.size() == 0) {
      return 0;
    }

    return Collections.max(suitCount.values());
  }

  public static int longestCardSequence(Collection<Card> cards) {

    Collection<Integer> ranks = cards.stream().map(Card::getRank).sorted()
        .collect(Collectors.toCollection(LinkedHashSet::new));

    Iterator<Integer> iterator = ranks.iterator();
    if (!iterator.hasNext()) {
      return 0;
    }

    int currentSequenceLength = 1;
    int previous = iterator.next();
    int maxRank = Collections.max(cards, Comparator.comparing(Card::getRank)).getRank();

    if (previous == 2 && maxRank == 14) {
      currentSequenceLength++;
    }

    List<Integer> sequenceLengths = new ArrayList<>();
    while (iterator.hasNext()) {

      int current = iterator.next();
      if (previous + 1 == current) {
        currentSequenceLength++;
      } else {
        sequenceLengths.add(currentSequenceLength);
        currentSequenceLength = 1;
      }

      previous = current;
    }

    sequenceLengths.add(currentSequenceLength);
    return Collections.max(sequenceLengths);
  }

  public static Collection<Card> getCardsInLongestSequence(Collection<Card> cards) {

    Stream<Card> cardStream = cards.stream().sorted(Comparator.comparing(Card::getRank));
    List<TreeSet<Card>> sequences = new ArrayList<>();

    Iterator<Card> iterator = cardStream.iterator();
    if (!iterator.hasNext()) {
      return new LinkedList<>();
    }

    Card highestCard = Collections.max(cards);
    Card prevCard = iterator.next();
    TreeSet<Card> currentSequence = new TreeSet<>();

    if (prevCard.getRank() == 2 && highestCard.getRank() == 14) {
      currentSequence.add(highestCard);
    }
    currentSequence.add(prevCard);

    while (iterator.hasNext()) {

      Card currentCard = iterator.next();
      int currentRank = currentCard.getRank();
      int previousRank = prevCard.getRank();

      if (previousRank + 1 == currentRank) {
        currentSequence.add(prevCard);
        currentSequence.add(currentCard);
      } else {
        sequences.add(currentSequence);
        currentSequence = new TreeSet<>();
        currentSequence.add(currentCard);
      }

      prevCard = currentCard;
    }

    sequences.add(currentSequence);

    if (sequences.size() == 1) {
      return sequences.get(0);
    }

    sequences.sort(Comparator.comparing(Collection::size));
    TreeSet<Card> seq1 = sequences.get(sequences.size() - 1);
    TreeSet<Card> seq2 = sequences.get(sequences.size() - 2);

    if (seq1.size() > seq2.size()) {
      return seq1;
    }

    Collection<Card> bestSeq;
    Card seq1first = seq1.first();
    Card seq2first = seq2.first();

    if (seq1first.compareTo(seq2first) > 0) {
      bestSeq = seq1;
    } else {
      bestSeq = seq2;
    }

    return bestSeq;
  }
}
