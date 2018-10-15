package pokergame.player.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pokergame.card.Card.Suit.Clubs;
import static pokergame.card.Card.Suit.Diamonds;
import static pokergame.card.Card.Suit.Hearts;
import static pokergame.card.Card.Suit.Spades;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import pokergame.card.Card;

public class StrategyUtilTest {

  private void testInputNeededForStraightFlush(Card[] goodCards, Card[] badCards) {

    List<Card> cards = new ArrayList<>();
    cards.addAll(Arrays.asList(goodCards));
    cards.addAll(Arrays.asList(badCards));

    int result = StrategyUtil.cardsNeededToCompleteStraightFlush(cards);
    assertEquals(badCards.length, result);
  }

  private void testInputNeededForFullHouse(Card[] goodCards, Card[] badCards) {

    List<Card> cards = new ArrayList<>();
    cards.addAll(Arrays.asList(goodCards));
    cards.addAll(Arrays.asList(badCards));

    int result = StrategyUtil.cardsNeededToCompleteFullHouse(cards);
    assertEquals(badCards.length, result);
  }

  @Test
  public void cardsNeededToCompleteRoyalFlushReturnsCorrect() {

    Card[] goodCards = {
        new Card(Clubs, 10),
        new Card(Clubs, 11),
        new Card(Clubs, 12),
    };

    Card[] badCards = {
        new Card(Hearts, 13),
        new Card(Diamonds, 14)
    };

    List<Card> cards = new ArrayList<>();
    cards.addAll(Arrays.asList(goodCards));
    cards.addAll(Arrays.asList(badCards));

    int result = StrategyUtil.cardsNeededToCompleteRoyalFlush(cards);
    assertEquals(badCards.length, result);
  }

  /*
  Straight is complete but flush is incomplete
   */

  @Test
  public void neededForStraightFlushReturnsCorrectCase1() {

    Card[] goodCards = {
        new Card(Clubs, 10),
        new Card(Clubs, 11),
        new Card(Clubs, 12),
    };

    Card[] badCards = {
        new Card(Hearts, 13),
        new Card(Diamonds, 14)
    };

    testInputNeededForStraightFlush(goodCards, badCards);
  }

  /*
  Flush is complete but straight is incomplete
   */

  @Test
  public void neededForStraightFlushReturnsCorrectCase2() {

    Card[] goodCards = {
        new Card(Clubs, 10),
        new Card(Clubs, 11),
        new Card(Clubs, 12),
        new Card(Clubs, 13),
    };

    Card[] badCards = {
        new Card(Clubs, 2)
    };

    testInputNeededForStraightFlush(goodCards, badCards);
  }

  /*
  1 card away from flush and from straight
   */

  @Test
  public void neededForStraightFlushReturnsCorrectCase3() {

    Card[] goodCards = {
        new Card(Clubs, 10),
        new Card(Clubs, 11),
        new Card(Clubs, 12),
        new Card(Clubs, 13),
    };

    Card[] badCards = {
        new Card(Diamonds, 2)
    };

    testInputNeededForStraightFlush(goodCards, badCards);
  }

  /*
  1 card away from flush, 2 cards away from straight.
  Should return highest of the two
   */

  @Test
  public void neededForStraightFlushReturnsCorrectCase4() {

    Card[] goodCards = {
        new Card(Clubs, 10),
        new Card(Clubs, 11),
        new Card(Clubs, 12),
    };

    Card[] badCards = {
        new Card(Clubs, 3),
        new Card(Diamonds, 2)
    };

    testInputNeededForStraightFlush(goodCards, badCards);
  }

   /*
  Full house, should return 0
   */

  @Test
  public void neededForFullHouseReturnsCorrectCase1() {

    Card[] cards = {
        new Card(Clubs, 2),
        new Card(Diamonds, 2),
        new Card(Hearts, 2),
        new Card(Clubs, 3),
        new Card(Diamonds, 3)
    };

    assertEquals(0, StrategyUtil.cardsNeededToCompleteFullHouse(Arrays.asList(cards)));
  }

   /*
  4 of a kind, should return 1 card
   */

  @Test
  public void neededForFullHouseReturnsCorrectCase2() {

    Card[] goodCards = {
        new Card(Clubs, 2),
        new Card(Diamonds, 2),
        new Card(Hearts, 2),
        new Card(Diamonds, 3)
    };

    Card[] badCards = {
        new Card(Spades, 2),
    };

    testInputNeededForFullHouse(goodCards, badCards);
  }

   /*
  2 pairs, should return one card
   */

  @Test
  public void neededForFullHouseReturnsCorrectCase3() {

    Card[] goodCards = {
        new Card(Clubs, 2),
        new Card(Diamonds, 2),
        new Card(Diamonds, 3),
        new Card(Spades, 3),
    };

    Card[] badCards = {
        new Card(Diamonds, 5)
    };

    testInputNeededForFullHouse(goodCards, badCards);
  }

     /*
  1 pair should return two cards
   */

  @Test
  public void neededForFullHouseReturnsCorrectCase4() {

    Card[] goodCards = {
        new Card(Clubs, 2),
        new Card(Diamonds, 2),
        new Card(Diamonds, 3),
    };

    Card[] badCards = {
        new Card(Spades, 4),
        new Card(Diamonds, 5)
    };

    testInputNeededForFullHouse(goodCards, badCards);
  }

  /*
  No pairs, should return three cards
   */

  @Test
  public void neededForFullHouseReturnsCorrectCase5() {

    Card[] goodCards = {
        new Card(Clubs, 2),
        new Card(Diamonds, 3),
    };

    Card[] badCards = {
        new Card(Spades, 4),
        new Card(Diamonds, 5),
        new Card(Diamonds, 6),
    };

    testInputNeededForFullHouse(goodCards, badCards);
  }

  @Test
  public void neededForFlushReturnsZero() {
    Card[] cards = {
        new Card(Clubs, 2),
        new Card(Clubs, 3),
        new Card(Clubs, 4),
        new Card(Clubs, 5),
        new Card(Clubs, 7)
    };

    assertEquals(0, StrategyUtil.cardsNeededToCompleteFlush(Arrays.asList(cards)));
  }

  @Test
  public void neededForFlushReturnsCorrectNumber() {
    Card[] goodCards = {
        new Card(Clubs, 2),
        new Card(Clubs, 3),
        new Card(Clubs, 4),
    };

    Card[] badCards = {
        new Card(Diamonds, 2),
        new Card(Diamonds, 3)
    };

    List<Card> cards = new ArrayList<>();
    cards.addAll(Arrays.asList(goodCards));
    cards.addAll(Arrays.asList(badCards));

    assertEquals(badCards.length, StrategyUtil.cardsNeededToCompleteFlush(cards));
  }

  @Test
  public void neededForStraightReturnsZero() {
    Card[] cards = {
        new Card(Clubs, 2),
        new Card(Clubs, 3),
        new Card(Clubs, 4),
        new Card(Clubs, 5),
        new Card(Clubs, 6)
    };

    assertEquals(0, StrategyUtil.cardsNeededToCompleteStraight(Arrays.asList(cards)));
  }

  @Test
  public void neededForStraightReturnsCorrectNumber() {
    Card[] goodCards = {
        new Card(Clubs, 2),
        new Card(Clubs, 3),
        new Card(Clubs, 4),
    };

    Card[] badCards = {
        new Card(Diamonds, 2),
        new Card(Diamonds, 3)
    };

    List<Card> cards = new ArrayList<>();
    cards.addAll(Arrays.asList(goodCards));
    cards.addAll(Arrays.asList(badCards));

    assertEquals(badCards.length, StrategyUtil.cardsNeededToCompleteStraight(cards));
  }
}
