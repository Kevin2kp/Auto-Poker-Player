package io;

import static pokergame.card.Card.Suit.Clubs;
import static pokergame.card.Card.Suit.Diamonds;
import static pokergame.card.Card.Suit.Hearts;
import static pokergame.card.Card.Suit.Spades;

import java.util.Scanner;
import java.util.regex.Pattern;
import pokergame.card.Card;
import pokergame.card.Card.Suit;

public class CardScanner {

  private static final Pattern pattern = Pattern.compile("[A-Z](\\d\\d?|[JQKA])");
  private Scanner scanner;

  public CardScanner(String source) {
    scanner = new Scanner(source);
  }

  public boolean hasNext() {
    return scanner.hasNext(pattern);
  }

  public Card next() {
    String cardStr = scanner.next(pattern);
    Suit suit = null;
    switch (cardStr.charAt(0)) {
      case 'C':
        suit = Clubs;
        break;
      case 'D':
        suit = Diamonds;
        break;
      case 'H':
        suit = Hearts;
        break;
      case 'S':
        suit = Spades;
    }

    int rank = -1;
    String rankStr = cardStr.substring(1, cardStr.length());
    if (rankStr.matches("[0-9]{1,2}")) {
      rank = Integer.parseInt(rankStr);
    } else {

      switch (rankStr.charAt(0)) {
        case 'J':
          rank = 11;
          break;

        case 'Q':
          rank = 12;
          break;

        case 'K':
          rank = 13;
          break;

        case 'A':
          rank = 14;
          break;
        default:
      }
    }

    return new Card(suit, rank);
  }
}
