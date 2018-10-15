package pokergame.rules;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pokergame.card.Card.Suit.Clubs;
import static pokergame.card.Card.Suit.Diamonds;
import static pokergame.card.Card.Suit.Hearts;
import static pokergame.card.Card.Suit.Spades;

import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pokergame.card.Card;

public class PokerHandsTest {

  @Test
  public void isRoyalFlushReturnsFalse() {
    Card[] cards = {
        new Card(Clubs, 10),
        new Card(Clubs, 11),
        new Card(Clubs, 12),
        new Card(Clubs, 13),
        new Card(Diamonds, 14)
    };

    Assertions.assertFalse(PokerHands.isRoyalFlush(Arrays.asList(cards)));
  }

  @Test
  public void isRoyalFlushReturnsTrue() {
    Card[] cards = {
        new Card(Clubs, 10),
        new Card(Clubs, 11),
        new Card(Clubs, 12),
        new Card(Clubs, 13),
        new Card(Clubs, 14)
    };

    assertTrue(PokerHands.isRoyalFlush(Arrays.asList(cards)));
  }

  @Test
  public void isStraightFlushReturnsFalse() {
    Card[] cards = {
        new Card(Clubs, 2),
        new Card(Clubs, 3),
        new Card(Clubs, 4),
        new Card(Clubs, 5),
        new Card(Clubs, 7)
    };

    assertFalse(PokerHands.isStraightFlush(Arrays.asList(cards)));
  }

  @Test
  public void isStraightFlushReturnsTrue() {
    Card[] cards = {
        new Card(Clubs, 2),
        new Card(Clubs, 3),
        new Card(Clubs, 4),
        new Card(Clubs, 5),
        new Card(Clubs, 6),
    };

    assertTrue(PokerHands.isStraightFlush(Arrays.asList(cards)));
  }

  @Test
  public void isStraightReturnsFalse() {
    Card[] cards = {
        new Card(Clubs, 2),
        new Card(Clubs, 3),
        new Card(Clubs, 4),
        new Card(Diamonds, 5),
        new Card(Clubs, 7),
    };

    assertFalse(PokerHands.isStraight(Arrays.asList(cards)));
  }

  @Test
  public void isStraightReturnsTrue() {
    Card[] cards = {
        new Card(Clubs, 2),
        new Card(Clubs, 3),
        new Card(Clubs, 4),
        new Card(Diamonds, 5),
        new Card(Clubs, 6),
    };

    assertTrue(PokerHands.isStraight(Arrays.asList(cards)));
  }

  @Test
  public void isFullHouseReturnsFalse() {
    Card[] cards = {
        new Card(Clubs, 2),
        new Card(Diamonds, 2),
        new Card(Clubs, 5),
        new Card(Diamonds, 3),
        new Card(Hearts, 3)
    };

    assertFalse(PokerHands.isFullHouse(Arrays.asList(cards)));
  }

  @Test
  public void isFullHouseReturnsTrue() {
    Card[] cards = {
        new Card(Clubs, 2),
        new Card(Diamonds, 2),
        new Card(Clubs, 3),
        new Card(Diamonds, 3),
        new Card(Hearts, 3)
    };

    assertTrue(PokerHands.isFullHouse(Arrays.asList(cards)));
  }

  @Test
  public void isFlushReturnsFalse() {
    Card[] cards = {
        new Card(Clubs, 2),
        new Card(Clubs, 3),
        new Card(Clubs, 4),
        new Card(Clubs, 5),
        new Card(Diamonds, 7)
    };

    assertFalse(PokerHands.isFlush(Arrays.asList(cards)));
  }

  @Test
  public void isFlushReturnsTrue() {
    Card[] cards = {
        new Card(Clubs, 2),
        new Card(Clubs, 3),
        new Card(Clubs, 4),
        new Card(Clubs, 5),
        new Card(Clubs, 7),
    };

    assertTrue(PokerHands.isFlush(Arrays.asList(cards)));
  }

  @Test
  public void isThreeOfAKindReturnsFalseOnMissing3ofAKind() {
    Card[] cards = {
        new Card(Clubs, 2),
        new Card(Diamonds, 2),
        new Card(Hearts, 3),
        new Card(Clubs, 3)
    };

    assertFalse(PokerHands.isThreeOfAKind(Arrays.asList(cards)));
  }

  @Test
  public void isThreeOfAKindReturnsTrueOn3OfAKind() {
    Card[] cards = {
        new Card(Clubs, 2),
        new Card(Diamonds, 2),
        new Card(Hearts, 2),
        new Card(Clubs, 3)
    };

    assertTrue(PokerHands.isThreeOfAKind(Arrays.asList(cards)));
  }

  @Test
  public void isTwoPairReturnsFalseOnMissingTwoPair() {
    Card[] cards = {
        new Card(Clubs, 2),
        new Card(Diamonds, 2),
        new Card(Hearts, 3),
        new Card(Clubs, 4)
    };

    assertFalse(PokerHands.isTwoPair(Arrays.asList(cards)));
  }

  @Test
  public void isTwoPairReturnsTrueOnTwoPair() {
    Card[] cards = {
        new Card(Clubs, 2),
        new Card(Diamonds, 2),
        new Card(Hearts, 3),
        new Card(Clubs, 3)
    };

    assertTrue(PokerHands.isTwoPair(Arrays.asList(cards)));
  }

  @Test
  public void isOnePairReturnsFalseIfMissingPair() {
    Card[] cards = {
        new Card(Clubs, 2),
        new Card(Diamonds, 3),
        new Card(Hearts, 4)
    };

    assertFalse(PokerHands.isOnePair(Arrays.asList(cards)));
  }

  @Test
  public void isOnePairReturnsTrueOnPair() {
    Card[] cards = {
        new Card(Clubs, 2),
        new Card(Diamonds, 2),
        new Card(Hearts, 3)
    };

    assertTrue(PokerHands.isOnePair(Arrays.asList(cards)));
  }

  @Test
  public void compareTwoRoyalFlush() {
    Card[] smallRoyalFlush = {
        new Card(Clubs, 10),
        new Card(Clubs, 11),
        new Card(Clubs, 12),
        new Card(Clubs, 13),
        new Card(Clubs, 14)
    };

    Card[] largeRoyalFlush = {
        new Card(Diamonds, 10),
        new Card(Diamonds, 11),
        new Card(Diamonds, 12),
        new Card(Diamonds, 13),
        new Card(Diamonds, 14)
    };

    int result = PokerHands.compareRoyalFlush(Arrays.asList(smallRoyalFlush),
        Arrays.asList(largeRoyalFlush));

    assertTrue(result < 0);
  }


  @Test
  public void compareTwoStraightFlush() {
    Card[] small = {
        new Card(Spades, 8),
        new Card(Spades, 9),
        new Card(Spades, 10),
        new Card(Spades, 11),
        new Card(Spades, 12),
    };

    Card[] large = {
        new Card(Diamonds, 9),
        new Card(Diamonds, 10),
        new Card(Diamonds, 11),
        new Card(Diamonds, 12),
        new Card(Diamonds, 13),
    };

    int result = PokerHands.compareStraightFlush(Arrays.asList(small),
        Arrays.asList(large));

    assertTrue(result < 0);
  }

  @Test
  public void compareTwoStraightFlushCase2() {
    Card[] small = {
        new Card(Clubs, 9),
        new Card(Clubs, 10),
        new Card(Clubs, 11),
        new Card(Clubs, 12),
        new Card(Clubs, 13),
    };

    Card[] large = {
        new Card(Diamonds, 9),
        new Card(Diamonds, 10),
        new Card(Diamonds, 11),
        new Card(Diamonds, 12),
        new Card(Diamonds, 13),
    };

    int result = PokerHands.compareStraightFlush(Arrays.asList(small),
        Arrays.asList(large));

    assertTrue(result < 0);
  }

  /**
   * Case 1: Flushes with at least 1 differing rank
   *
   * @implSpec Flush with highest ranked card is larger
   */

  @Test
  public void compareTwoFlush() {
    Card[] small = {
        new Card(Clubs, 9),
        new Card(Clubs, 10),
        new Card(Clubs, 11),
        new Card(Clubs, 12),
        new Card(Clubs, 6),
    };

    Card[] large = {
        new Card(Diamonds, 5),
        new Card(Diamonds, 10),
        new Card(Diamonds, 11),
        new Card(Diamonds, 12),
        new Card(Diamonds, 14),
    };

    int result = PokerHands.compareFlush(Arrays.asList(small),
        Arrays.asList(large));

    assertTrue(result < 0);
  }

  /**
   * Case 1: Flushes with identical ranks and different suits
   *
   * @implSpec Flush with best suit is larger
   */

  @Test
  public void compareTwoFlushCase2() {
    Card[] small = {
        new Card(Clubs, 9),
        new Card(Clubs, 10),
        new Card(Clubs, 11),
        new Card(Clubs, 12),
        new Card(Clubs, 13),
    };

    Card[] large = {
        new Card(Diamonds, 9),
        new Card(Diamonds, 10),
        new Card(Diamonds, 11),
        new Card(Diamonds, 12),
        new Card(Diamonds, 13),
    };

    int result = PokerHands.compareFlush(Arrays.asList(small),
        Arrays.asList(large));

    assertTrue(result < 0);
  }

  @Test
  public void compareStraightCase1() {
    Card[] small = {
        new Card(Diamonds, 8),
        new Card(Diamonds, 9),
        new Card(Diamonds, 10),
        new Card(Diamonds, 11),
        new Card(Diamonds, 12),
    };

    Card[] large = {
        new Card(Clubs, 9),
        new Card(Clubs, 10),
        new Card(Clubs, 11),
        new Card(Clubs, 12),
        new Card(Clubs, 13),
    };

    int result = PokerHands.compareStraight(Arrays.asList(small),
        Arrays.asList(large));

    assertTrue(result < 0);
  }

  @Test
  public void compareStraightCase2() {
    Card[] small = {
        new Card(Diamonds, 9),
        new Card(Clubs, 10),
        new Card(Clubs, 11),
        new Card(Clubs, 12),
        new Card(Clubs, 13),
    };

    Card[] large = {
        new Card(Clubs, 9),
        new Card(Diamonds, 10),
        new Card(Diamonds, 11),
        new Card(Diamonds, 12),
        new Card(Diamonds, 13),
    };

    int result = PokerHands.compareStraight(Arrays.asList(small),
        Arrays.asList(large));

    assertTrue(result < 0);
  }

  @Test
  public void compareStraightCase3() {
    Card[] small = {
        new Card(Diamonds, 2),
        new Card(Clubs, 3),
        new Card(Clubs, 4),
        new Card(Clubs, 5),
        new Card(Clubs, 14),
    };

    Card[] large = {
        new Card(Clubs, 2),
        new Card(Diamonds, 3),
        new Card(Diamonds, 4),
        new Card(Diamonds, 5),
        new Card(Diamonds, 6),
    };

    int result = PokerHands.compareStraight(Arrays.asList(small),
        Arrays.asList(large));

    assertTrue(result < 0);
  }

  @Test
  public void compareFullHouse() {
    Card[] small = {
        new Card(Diamonds, 2),
        new Card(Clubs, 2),
        new Card(Clubs, 2),
        new Card(Clubs, 3),
        new Card(Clubs, 3),
    };

    Card[] large = {
        new Card(Clubs, 4),
        new Card(Diamonds, 4),
        new Card(Diamonds, 4),
        new Card(Diamonds, 5),
        new Card(Diamonds, 5),
    };

    int result = PokerHands.compareFullHouse(Arrays.asList(small),
        Arrays.asList(large));

    assertTrue(result < 0);
  }

  @Test
  public void compareFourOfAKind() {
    Card[] small = {
        new Card(Diamonds, 2),
        new Card(Clubs, 2),
        new Card(Clubs, 2),
        new Card(Clubs, 2),
        new Card(Clubs, 3),
    };

    Card[] large = {
        new Card(Clubs, 4),
        new Card(Diamonds, 4),
        new Card(Diamonds, 4),
        new Card(Diamonds, 4),
        new Card(Diamonds, 5),
    };

    int result = PokerHands.compareFourOfAKind(Arrays.asList(small),
        Arrays.asList(large));

    assertTrue(result < 0);
  }

  @Test
  public void compareThreeOfAKind() {
    Card[] small = {
        new Card(Diamonds, 2),
        new Card(Clubs, 2),
        new Card(Clubs, 2),
        new Card(Clubs, 5),
        new Card(Clubs, 3),
    };

    Card[] large = {
        new Card(Clubs, 4),
        new Card(Diamonds, 4),
        new Card(Diamonds, 4),
        new Card(Diamonds, 6),
        new Card(Diamonds, 5),
    };

    int result = PokerHands.compareThreeOfAKind(Arrays.asList(small),
        Arrays.asList(large));

    assertTrue(result < 0);
  }

  @Test
  public void compareTwoPair() {
    Card[] small = {
        new Card(Diamonds, 2),
        new Card(Clubs, 2),
        new Card(Clubs, 3),
        new Card(Clubs, 3),
        new Card(Clubs, 4),
    };

    Card[] large = {
        new Card(Clubs, 4),
        new Card(Diamonds, 4),
        new Card(Diamonds, 5),
        new Card(Diamonds, 5),
        new Card(Diamonds, 6),
    };

    int result = PokerHands.compareTwoPair(Arrays.asList(small),
        Arrays.asList(large));

    assertTrue(result < 0);
  }

  @Test
  public void compareTwoPairCase2() {
    Card[] small = {
        new Card(Clubs, 2),
        new Card(Diamonds, 2),
        new Card(Clubs, 3),
        new Card(Clubs, 4),
        new Card(Diamonds, 4),
    };

    Card[] large = {
        new Card(Diamonds, 3),
        new Card(Hearts, 3),
        new Card(Hearts, 4),
        new Card(Spades, 4),
        new Card(Diamonds, 6),
    };

    int result = PokerHands.compareTwoPair(Arrays.asList(small),
        Arrays.asList(large));

    assertTrue(result < 0);
  }

  @Test
  public void comparePair() {
    Card[] small = {
        new Card(Clubs, 2),
        new Card(Diamonds, 2),
        new Card(Clubs, 3),
        new Card(Clubs, 4),
        new Card(Diamonds, 5),
    };

    Card[] large = {
        new Card(Diamonds, 3),
        new Card(Hearts, 3),
        new Card(Diamonds, 4),
        new Card(Diamonds, 5),
        new Card(Diamonds, 6),
    };

    int result = PokerHands.comparePair(Arrays.asList(small),
        Arrays.asList(large));

    assertTrue(result < 0);
  }

  @Test
  public void comparePairCase2() {
    Card[] small = {
        new Card(Clubs, 2),
        new Card(Diamonds, 2),
        new Card(Clubs, 3),
        new Card(Clubs, 4),
        new Card(Diamonds, 5),
    };

    Card[] large = {
        new Card(Hearts, 2),
        new Card(Spades, 2),
        new Card(Diamonds, 4),
        new Card(Diamonds, 5),
        new Card(Diamonds, 6),
    };

    int result = PokerHands.comparePair(Arrays.asList(small),
        Arrays.asList(large));

    assertTrue(result < 0);
  }

  @Test
  public void compareHighestCard() {
    Card[] small = {
        new Card(Clubs, 2),
        new Card(Diamonds, 3),
        new Card(Clubs, 5),
        new Card(Clubs, 6),
        new Card(Diamonds, 9),
    };

    Card[] large = {
        new Card(Hearts, 2),
        new Card(Spades, 4),
        new Card(Diamonds, 6),
        new Card(Diamonds, 8),
        new Card(Diamonds, 14),
    };

    int result = PokerHands.compareHighestCard(Arrays.asList(small),
        Arrays.asList(large));

    assertTrue(result < 0);
  }

  @Test
  public void compareHighestCardCase2() {
    Card[] small = {
        new Card(Clubs, 2),
        new Card(Diamonds, 2),
        new Card(Clubs, 3),
        new Card(Clubs, 4),
        new Card(Clubs, 9),
    };

    Card[] large = {
        new Card(Hearts, 2),
        new Card(Spades, 5),
        new Card(Diamonds, 9),
        new Card(Diamonds, 8),
        new Card(Diamonds, 7),
    };

    int result = PokerHands.compareHighestCard(Arrays.asList(small),
        Arrays.asList(large));

    assertTrue(result < 0);
  }
}
