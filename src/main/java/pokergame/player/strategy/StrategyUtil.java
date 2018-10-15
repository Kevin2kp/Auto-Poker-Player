package pokergame.player.strategy;

import static pokergame.card.CardsUtil.splitCardsBySuit;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;
import pokergame.card.Card;
import pokergame.card.CardsUtil;

public class StrategyUtil {

  private static void validateInput(Collection<Card> cards) {
    if (cards.size() != 5) {
      throw new IllegalArgumentException();
    }
  }

  public static int cardsNeededToCompleteRoyalFlush(Collection<Card> cards) {
    validateInput(cards);

    Optional<Collection<Card>> result = splitCardsBySuit(cards).stream().max(
        Comparator.comparingInt(Collection::size));

    Collection<Card> bestSuit = result.orElseThrow(IllegalStateException::new);

    final int[] cardsNeeded = {5};
    bestSuit.stream().map(Card::getRank).forEach(rank -> {
      if (rank == 10 || rank == 11 || rank == 12 || rank == 13 || rank == 14) {
        cardsNeeded[0]--;
      }
    });

    return cardsNeeded[0];
  }

  public static int cardsNeededToCompleteStraightFlush(Collection<Card> cards) {
    validateInput(cards);

    int longestSequence = CardsUtil.longestCardSequence(cards);
    int highestNumberSameSuit = CardsUtil.highestNumberSameSuit(cards);

    if (longestSequence != highestNumberSameSuit) {
      return 5 - Math.min(longestSequence, highestNumberSameSuit);
    }

    return 5 - longestSequence;
  }

  //Fking overkill, dude

  public static int cardsNeededToCompleteFullHouse(Collection<Card> cards) {
    validateInput(cards);
    Map<Integer, Integer> rankFrequencies = CardsUtil.getRankFrequencies(cards);
    List<Integer> frequencies = rankFrequencies.entrySet().stream().map(Entry::getValue).sorted()
        .collect(Collectors.toList());

    /*
    Note: If this happens, it means there's a 5 of a kind which should be illegal according
    to the rules of poker being used for this project
     */
    if (frequencies.size() < 2) {
      return frequencies.size() - 2;
    }

    int highestFreq = frequencies.remove(frequencies.size() - 1);
    int secondHighestFreq = frequencies.remove(frequencies.size() - 1);

    int neededToComplete3ofKind = 3;
    if (highestFreq >= 3) {
      neededToComplete3ofKind = 0;
    } else {
      neededToComplete3ofKind -= highestFreq;
    }

    int neededToCompletePair = 2;
    if (secondHighestFreq >= 2) {
      neededToCompletePair = 0;
    } else {
      neededToCompletePair -= secondHighestFreq;
    }

    return neededToComplete3ofKind + neededToCompletePair;
  }

  public static int cardsNeededToCompleteFlush(Collection<Card> cards) {
    validateInput(cards);
    return 5 - CardsUtil.highestNumberSameSuit(cards);
  }

  public static int cardsNeededToCompleteStraight(Collection<Card> cards) {
    validateInput(cards);
    return 5 - CardsUtil.longestCardSequence(cards);
  }
}
