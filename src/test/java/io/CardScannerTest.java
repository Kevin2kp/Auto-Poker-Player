package io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pokergame.card.Card.Suit.Clubs;
import static pokergame.card.Card.Suit.Diamonds;
import static pokergame.card.Card.Suit.Hearts;
import static pokergame.card.Card.Suit.Spades;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import pokergame.card.Card;

public class CardScannerTest {

  @Test
  public void testScanner() {

    Card[] cards = {
        new Card(Clubs, 2),
        new Card(Clubs, 3),
        new Card(Clubs, 4),
        new Card(Clubs, 5),
        new Card(Diamonds, 11),
        new Card(Hearts, 12),
        new Card(Spades, 13),
        new Card(Clubs, 14)
    };

    List<String> expected = Arrays.stream(cards)
        .map(Card::toString)
        .collect(Collectors.toList());

    List<String> actual = new ArrayList<>();
    CardScanner cardScanner = new CardScanner("C2 C3 C4 C5 DJ HQ SK CA");
    while (cardScanner.hasNext()) {
      Card card = cardScanner.next();
      actual.add(card.toString());
    }

    assertEquals(expected, actual);
  }
}
