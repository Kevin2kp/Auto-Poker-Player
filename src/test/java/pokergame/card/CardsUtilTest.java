package pokergame.card;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pokergame.card.Card.Suit.Clubs;
import static pokergame.card.Card.Suit.Diamonds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

public class CardsUtilTest {

  @Test
  public void highestNumberofSameSuitReturnsZero() {
    List<Card> cards = new ArrayList<>();
    int result = CardsUtil.highestNumberSameSuit(cards);
    assertEquals(result, 0);
  }

  @Test
  public void highestNumberofSameSuitReturnsCorrectLength() {
    Card[] cardsOfSameSuit = {
        new Card(Clubs, 2),
        new Card(Clubs, 3),
        new Card(Clubs, 4),
        new Card(Clubs, 5),
    };

    int result = CardsUtil.highestNumberSameSuit(Arrays.asList(cardsOfSameSuit));
    assertEquals(cardsOfSameSuit.length, result);
  }

  @Test
  public void highestNumberofSameSuitReturnsHighestNumber() {
    Card[] smallerGroup = {
        new Card(Clubs, 2),
        new Card(Clubs, 3),
    };

    Card[] biggerGroup = {
        new Card(Diamonds, 5),
        new Card(Diamonds, 6),
        new Card(Diamonds, 7)
    };

    List<Card> cards = new ArrayList<>();
    cards.addAll(Arrays.asList(smallerGroup));
    cards.addAll(Arrays.asList(biggerGroup));

    int result = CardsUtil.highestNumberSameSuit(cards);
    assertEquals(biggerGroup.length,
        result);
  }

  @Test
  public void longestCardSequenceReturnsZero() {
    List<Card> cards = new ArrayList<>();
    int result = CardsUtil.longestCardSequence(cards);
    assertEquals(result, 0);
  }

  @Test
  public void longestCardSequenceReturnsCorrectLength() {
    Card[] cards = {
        new Card(Clubs, 2),
        new Card(Clubs, 3),
        new Card(Clubs, 4),
        new Card(Clubs, 5),
    };

    int result = CardsUtil.longestCardSequence(Arrays.asList(cards));
    assertEquals(cards.length, result);
  }

  @Test
  public void longestCardSequenceReturnsCorrectLengthCase2() {
    Card[] shortSeq = {
        new Card(Clubs, 3),
        new Card(Clubs, 2),
    };

    Card[] longSeq = {
        new Card(Clubs, 5),
        new Card(Clubs, 6),
        new Card(Clubs, 7)
    };

    List<Card> cards = new ArrayList<>();
    cards.addAll(Arrays.asList(shortSeq));
    cards.addAll(Arrays.asList(longSeq));

    int result = CardsUtil.longestCardSequence(cards);
    assertEquals(longSeq.length, result);
  }

  @Test
  public void longestCardSequenceReturnsCorrectLengthCase3() {
    Card[] shortSeq = {
        new Card(Clubs, 6),
        new Card(Clubs, 5),
    };

    Card[] longSeq = {
        new Card(Clubs, 2),
        new Card(Clubs, 3),
        new Card(Clubs, 14)
    };

    List<Card> cards = new ArrayList<>();
    cards.addAll(Arrays.asList(shortSeq));
    cards.addAll(Arrays.asList(longSeq));

    int result = CardsUtil.longestCardSequence(cards);
    assertEquals(longSeq.length, result);
  }

  @Test
  public void getCardsInLongestSequenceReturnsLongestSequence() {

    Card[] shortSeqArr = new Card[]{
        new Card(Clubs, 2),
        new Card(Clubs, 3),
    };

    Card[] longSeqArr = {
        new Card(Clubs, 5),
        new Card(Clubs, 6),
        new Card(Clubs, 7)
    };

    List<Card> cards = new ArrayList<>();
    cards.addAll(Arrays.asList(shortSeqArr));
    cards.addAll(Arrays.asList(longSeqArr));

    List<String> expected = Arrays.stream(longSeqArr)
        .map(Card::toString)
        .collect(Collectors.toList());

    Collection<String> actual = CardsUtil.getCardsInLongestSequence(cards).stream()
        .map(Card::toString)
        .collect(Collectors.toList());

    assertEquals(expected, actual);
  }

  /*
  Ace can be either rank 1 or 14 when in sequence: Ace, Two, Three and Queen, King, Ace are valid sequences
   */

  @Test
  public void getCardsInLongestSequenceReturnsLongestSequenceCase2() {

    Card[] shortSeqArr = new Card[]{
        new Card(Clubs, 5),
        new Card(Clubs, 6),
    };

    Card[] longSeqArr = {
        new Card(Clubs, 14),
        new Card(Clubs, 2),
        new Card(Clubs, 3)
    };

    List<Card> cards = new ArrayList<>();
    cards.addAll(Arrays.asList(shortSeqArr));
    cards.addAll(Arrays.asList(longSeqArr));

    Collection<String> expected = Arrays.stream(longSeqArr)
        .map(Card::toString)
        .collect(Collectors.toSet());

    Collection<String> actual = CardsUtil.getCardsInLongestSequence(cards).stream()
        .map(Card::toString)
        .collect(Collectors.toSet());

    assertEquals(expected, actual);
  }


  /*
  Ranks 2,3 and Queen, King and Ace. There are two possible sequences of the same length.
  Should return the better of the two sequences: King, Queen and Ace
   */

  @Test
  public void getCardsInLongestSequenceReturnsLongestSequenceCase3() {

    Card[] shortSeqArr = new Card[]{
        new Card(Clubs, 2),
        new Card(Clubs, 3),
    };

    Card[] longSeqArr = {
        new Card(Clubs, 12),
        new Card(Clubs, 13),
        new Card(Clubs, 14)
    };

    List<Card> cards = new ArrayList<>();
    cards.addAll(Arrays.asList(shortSeqArr));
    cards.addAll(Arrays.asList(longSeqArr));

    Collection<String> expected = Arrays.stream(longSeqArr)
        .map(Card::toString)
        .collect(Collectors.toSet());

    Collection<String> actual = CardsUtil.getCardsInLongestSequence(cards).stream()
        .map(Card::toString)
        .collect(Collectors.toSet());

    assertEquals(expected, actual);
  }
}
