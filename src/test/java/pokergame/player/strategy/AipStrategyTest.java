package pokergame.player.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pokergame.card.Card.Suit.Clubs;
import static pokergame.card.Card.Suit.Diamonds;
import static pokergame.card.Card.Suit.Hearts;

import java.util.Arrays;
import java.util.Collection;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import pokergame.card.Card;

public class AipStrategyTest {

  private void testNoCardsAreExchanged(Strategy strategy) {
    Collection<Card> discarded = strategy.processTurn();
    assertEquals(0, discarded.size());
  }

  private void testRightCardsAreExchanged(Strategy strategy, SortedSet<String> expected) {

    Collection<Card> discarded = strategy.processTurn();
    SortedSet<String> actual = discarded.stream().map(Card::toString)
        .collect(Collectors.toCollection(TreeSet::new));
    assertEquals(expected, actual);
  }

  @Test
  public void doesNothingOnRoyalFlush() {

    Card[] hand = new Card[]{
        new Card(Clubs, 10),
        new Card(Clubs, 11),
        new Card(Clubs, 12),
        new Card(Clubs, 13),
        new Card(Clubs, 14)
    };

    AipStrategy strategy = new AipStrategy(Arrays.asList(hand));
    testNoCardsAreExchanged(strategy);
  }

  @Test
  public void doesNothingOnStraightFlush() {

    Card[] hand = new Card[]{
        new Card(Clubs, 2),
        new Card(Clubs, 3),
        new Card(Clubs, 4),
        new Card(Clubs, 5),
        new Card(Clubs, 6),
    };

    AipStrategy strategy = new AipStrategy(Arrays.asList(hand));
    testNoCardsAreExchanged(strategy);
  }

  @Test
  public void doesNothingOnFullHouse() {

    Card[] hand = new Card[]{
        new Card(Clubs, 2),
        new Card(Diamonds, 2),
        new Card(Hearts, 2),
        new Card(Clubs, 3),
        new Card(Diamonds, 3),
    };

    AipStrategy strategy = new AipStrategy(Arrays.asList(hand));
    testNoCardsAreExchanged(strategy);
  }

  @Test
  public void doesNothingOnFlush() {

    Card[] hand = new Card[]{
        new Card(Clubs, 2),
        new Card(Clubs, 4),
        new Card(Clubs, 6),
        new Card(Clubs, 8),
        new Card(Clubs, 10),
    };

    AipStrategy strategy = new AipStrategy(Arrays.asList(hand));
    testNoCardsAreExchanged(strategy);
  }

  @Test
  public void doesNothingOnStraight() {

    Card[] hand = new Card[]{
        new Card(Clubs, 2),
        new Card(Diamonds, 3),
        new Card(Clubs, 4),
        new Card(Diamonds, 5),
        new Card(Clubs, 6),
    };

    AipStrategy strategy = new AipStrategy(Arrays.asList(hand));
    testNoCardsAreExchanged(strategy);
  }

  @Test
  public void triesToCompleteRoyalFlush() {

    Card badCard = new Card(Diamonds, 2);
    SortedSet<String> expected = new TreeSet<>();
    expected.add(badCard.toString());

    Card[] hand = new Card[]{
        badCard,
        new Card(Clubs, 11),
        new Card(Clubs, 12),
        new Card(Clubs, 13),
        new Card(Clubs, 14)
    };

    AipStrategy strategy = new AipStrategy(Arrays.asList(hand));
    testRightCardsAreExchanged(strategy, expected);
  }

  @Test
  public void triesToCompleteStraightFlush() {

    Card badCard = new Card(Diamonds, 2);
    SortedSet<String> expected = new TreeSet<>();
    expected.add(badCard.toString());

    Card[] hand = new Card[]{
        badCard,
        new Card(Clubs, 2),
        new Card(Clubs, 3),
        new Card(Clubs, 4),
        new Card(Clubs, 5)
    };

    Strategy strategy = new AipStrategy(Arrays.asList(hand));
    testRightCardsAreExchanged(strategy, expected);
  }

  @Test
  public void triesToCompleteFullHouse() {

    Card badCard = new Card(Clubs, 2);
    Card badCard2 = new Card(Hearts, 5);
    SortedSet<String> expected = new TreeSet<>();
    expected.add(badCard.toString());
    expected.add(badCard2.toString());

    Card[] hand = new Card[]{
        new Card(Clubs, 3),
        new Card(Diamonds, 3),
        new Card(Hearts, 3),
        badCard,
        badCard2
    };

    Strategy strategy = new AipStrategy(Arrays.asList(hand));
    testRightCardsAreExchanged(strategy, expected);
  }

  @Test
  public void triesToCompleteFlush() {

    Card badCard = new Card(Diamonds, 2);
    SortedSet<String> expected = new TreeSet<>();
    expected.add(badCard.toString());

    Card[] hand = new Card[]{
        new Card(Clubs, 2),
        new Card(Clubs, 4),
        new Card(Clubs, 6),
        new Card(Clubs, 8),
        badCard,
    };

    Strategy strategy = new AipStrategy(Arrays.asList(hand));
    testRightCardsAreExchanged(strategy, expected);
  }

  @Test
  public void triesToCompleteStraight() {
    Card badCard = new Card(Hearts, 7);
    SortedSet<String> expected = new TreeSet<>();
    expected.add(badCard.toString());

    Card[] hand = new Card[]{
        new Card(Clubs, 2),
        new Card(Diamonds, 3),
        new Card(Clubs, 4),
        new Card(Diamonds, 5),
        badCard,
    };

    Strategy strategy = new AipStrategy(Arrays.asList(hand));
    testRightCardsAreExchanged(strategy, expected);
  }

  @Test
  public void exchangesTwoOnThreeOfSameSuit() {

    Card badCard1 = new Card(Diamonds, 8);
    Card badCard2 = new Card(Hearts, 9);

    SortedSet<String> expected = new TreeSet<>();
    expected.add(badCard1.toString());
    expected.add(badCard2.toString());

    Card[] hand = new Card[]{
        new Card(Clubs, 2),
        new Card(Clubs, 4),
        new Card(Clubs, 6),
        badCard1,
        badCard2,
    };

    Strategy strategy = new AipStrategy(Arrays.asList(hand));
    testRightCardsAreExchanged(strategy, expected);
  }

  //Test case is invalid

  /*
    void exchangesTwoOnThreeOfAKind() {

    Card badCard1 = new Card(Diamonds, 3);
    Card badCard2 = new Card(Hearts, 5);

    SortedSet<String> expected = new TreeSet<>();
    expected.add(badCard1.toString());
    expected.add(badCard2.toString());

    Card[] hand = new Card[]{
        new Card(Clubs, 2),
        new Card(Diamonds, 2),
        new Card(Hearts, 2),
        badCard1,
        badCard2,
    };

    Strategy strategy = new AipStrategy(Arrays.asList(hand));
    testRightCardsAreExchanged(strategy, expected);
  }
   */

  @Test
  public void exchangesTwoOnThreeSequence() {

    Card badCard1 = new Card(Diamonds, 6);
    Card badCard2 = new Card(Hearts, 7);

    SortedSet<String> expected = new TreeSet<>();
    expected.add(badCard1.toString());
    expected.add(badCard2.toString());

    Card[] hand = new Card[]{
        new Card(Clubs, 2),
        new Card(Clubs, 3),
        new Card(Diamonds, 4),
        badCard1,
        badCard2,
    };

    Strategy strategy = new AipStrategy(Arrays.asList(hand));
    testRightCardsAreExchanged(strategy, expected);
  }

  @Test
  public void exchangesOneOnTwoPair() {

    Card badCard1 = new Card(Hearts, 5);
    SortedSet<String> expected = new TreeSet<>();
    expected.add(badCard1.toString());

    Card[] hand = new Card[]{
        new Card(Clubs, 2),
        new Card(Diamonds, 2),
        new Card(Clubs, 3),
        new Card(Diamonds, 3),
        badCard1,
    };

    Strategy strategy = new AipStrategy(Arrays.asList(hand));
    testRightCardsAreExchanged(strategy, expected);
  }

  @Test
  public void exchangesThreeOnPair() {

    Card badCard1 = new Card(Clubs, 3);
    Card badCard2 = new Card(Diamonds, 5);
    Card badCard3 = new Card(Hearts, 6);

    SortedSet<String> expected = new TreeSet<>();
    expected.add(badCard1.toString());
    expected.add(badCard2.toString());
    expected.add(badCard3.toString());

    Card[] hand = new Card[]{
        new Card(Clubs, 2),
        new Card(Diamonds, 2),
        badCard1,
        badCard2,
        badCard3,
    };

    Strategy strategy = new AipStrategy(Arrays.asList(hand));
    testRightCardsAreExchanged(strategy, expected);
  }

  @Test
  public void keepsHighestTwoOnBadHand() {

    Card badCard1 = new Card(Clubs, 2);
    Card badCard2 = new Card(Diamonds, 3);
    Card badCard3 = new Card(Clubs, 5);

    SortedSet<String> expected = new TreeSet<>();
    expected.add(badCard1.toString());
    expected.add(badCard2.toString());
    expected.add(badCard3.toString());

    Card[] hand = new Card[]{
        new Card(Hearts, 7),
        new Card(Diamonds, 8),
        badCard1,
        badCard2,
        badCard3,
    };

    Strategy strategy = new AipStrategy(Arrays.asList(hand));
    testRightCardsAreExchanged(strategy, expected);
  }
}
