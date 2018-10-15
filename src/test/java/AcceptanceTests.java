import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.CardScanner;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeSet;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pokergame.GameSetup;
import pokergame.PokerGame;
import pokergame.card.Card;
import pokergame.card.CardsUtil;
import pokergame.player.PokerPlayer;
import pokergame.player.strategy.StrategyUtil;
import pokergame.rules.PokerHands;
import pokergame.rules.PokerHands.Type;

public class AcceptanceTests {

  private static PokerPlayer aip;
  private static PokerPlayer winner;
  private static TreeSet<Card> hand;
  private static TreeSet<Card> expected;
  private static PokerGame game;
  private static Map<Integer, String> games;
  private static Type handType;
  private static Collection<Card> discarded;

  @BeforeAll
  static void loadTestFile() throws FileNotFoundException {
    String filename = System.getProperty("inputFile");

    InputStream inputStream = null;
    if (filename != null && new File(filename).isFile()) {
      inputStream = new FileInputStream(filename);
    } else {
      inputStream = ClassLoader.getSystemResourceAsStream("inputs.csv");
    }

    Scanner scanner = new Scanner(inputStream);
    games = new HashMap<>();

    int tests = scanner.nextInt();
    int index = 0;
    while (index++ != tests) {
      int testId = scanner.nextInt();
      String line = scanner.nextLine();
      games.put(testId, line);
    }

    GameSetup.setMaxRounds(1);
    GameSetup.shuffleDeck(false);
  }

  private static void loadGame(int id) {

    expected = new TreeSet<>();
    Collection<Card> cards = new ArrayList<>();
    CardScanner cardScanner = new CardScanner(games.get(id));

    while (cards.size() != 52 && cardScanner.hasNext()) {
      cards.add(cardScanner.next());
    }

    while (cardScanner.hasNext()) {
      expected.add(cardScanner.next());
    }

    GameSetup.setDeckCards(cards);
    game = GameSetup.getGame();
  }

  public static void oneRoundSummary() {
    game.nextPlayerTurn();
    aip = game.activePlayer();
    hand = new TreeSet<>(aip.getCards());
    game.nextPlayerTurn();
    discarded = new TreeSet<>(game.cardsDiscardedByLastPlayer());
    handType = PokerHands.handType(hand);
    winner = game.computeWinner();
  }

  public static void instantSummary() {
    game.nextPlayerTurn();
    aip = game.activePlayer();
    hand = new TreeSet<>(aip.getCards());
    handType = PokerHands.handType(hand);
    winner = game.computeWinner();
  }

  @Test
  public void line45() {
    loadGame(45);
    oneRoundSummary();
    assertEquals(Type.ROYAL_FLUSH, handType);
    assertArrayEquals(expected.toArray(), discarded.toArray());
  }

  @Test
  public void line46() {
    loadGame(46);
    oneRoundSummary();
    assertEquals(Type.STRAIGHT_FLUSH, handType);
    assertArrayEquals(expected.toArray(), discarded.toArray());
  }

  @Test
  public void line48() {
    loadGame(48);
    oneRoundSummary();
    assertEquals(Type.FULL_HOUSE, handType);
    assertArrayEquals(expected.toArray(), discarded.toArray());
  }

  @Test
  public void line49() {
    loadGame(49);
    oneRoundSummary();
    assertEquals(Type.FLUSH, handType);
    assertArrayEquals(expected.toArray(), discarded.toArray());
  }

  @Test
  public void line50() {
    loadGame(50);
    oneRoundSummary();
    assertEquals(Type.STRAIGHT, handType);
    assertArrayEquals(expected.toArray(), discarded.toArray());
  }

  @Test
  public void line51() {
    loadGame(51);
    oneRoundSummary();
    assertEquals(1, StrategyUtil.cardsNeededToCompleteRoyalFlush(hand));
    assertArrayEquals(expected.toArray(), discarded.toArray());
  }

  @Test
  public void line52() {
    loadGame(52);
    oneRoundSummary();
    assertEquals(1,
        StrategyUtil.cardsNeededToCompleteStraightFlush(hand));
    assertArrayEquals(expected.toArray(), discarded.toArray());
  }

  @Test
  public void line53() {
    loadGame(53);
    oneRoundSummary();
    assertEquals(1,
        StrategyUtil.cardsNeededToCompleteFlush(hand));
    assertArrayEquals(expected.toArray(), discarded.toArray());
  }

  @Test
  public void line54() {
    loadGame(54);
    oneRoundSummary();
    assertEquals(1,
        StrategyUtil.cardsNeededToCompleteStraight(hand));
    assertArrayEquals(expected.toArray(), discarded.toArray());
  }

  @Test
  public void line55() {
    loadGame(55);
    oneRoundSummary();
    assertEquals(3,
        CardsUtil.highestNumberSameSuit(hand));
    assertArrayEquals(expected.toArray(), discarded.toArray());
  }

  @Test
  public void line56() {
    loadGame(56);
    oneRoundSummary();
    assertTrue(PokerHands.isThreeOfAKind(hand));
    assertArrayEquals(expected.toArray(), discarded.toArray());
  }

  @Test
  public void line57() {
    loadGame(57);
    oneRoundSummary();
    assertEquals(3,
        CardsUtil.longestCardSequence(hand));
    assertArrayEquals(expected.toArray(), discarded.toArray());
  }

  @Test
  public void line58() {
    loadGame(58);
    oneRoundSummary();
    assertTrue(PokerHands.isTwoPair(hand));
    assertArrayEquals(expected.toArray(), discarded.toArray());
  }

  @Test
  public void line59() {
    loadGame(58);
    oneRoundSummary();
    assertTrue(PokerHands.isOnePair(hand));
    assertArrayEquals(expected.toArray(), discarded.toArray());
  }

  @Test
  public void line60() {
    loadGame(58);
    oneRoundSummary();
    assertArrayEquals(expected.toArray(), discarded.toArray());
  }

  @Test
  public void line63() {
    loadGame(63);
    instantSummary();
    assertEquals(aip, winner);
  }

  @Test
  public void line64() {
    loadGame(64);
    instantSummary();
    assertEquals(aip, winner);
  }

  @Test
  public void line65() {
    loadGame(65);
    instantSummary();
    assertEquals(aip, winner);
  }

  @Test
  public void line66() {
    loadGame(66);
    instantSummary();
    assertEquals(aip, winner);
  }

  @Test
  public void line67() {
    loadGame(67);
    instantSummary();
    assertEquals(aip, winner);
  }

  @Test
  public void line68() {
    loadGame(68);
    instantSummary();
    assertEquals(aip, winner);
  }

  @Test
  public void line69() {
    loadGame(69);
    instantSummary();
    assertEquals(aip, winner);
  }

  @Test
  public void line70() {
    loadGame(70);
    instantSummary();
    assertEquals(aip, winner);
  }

  @Test
  public void line71() {
    loadGame(71);
    instantSummary();
    assertEquals(aip, winner);
  }

  @Test
  public void line75() {
    loadGame(75);
    instantSummary();
    assertEquals(aip, winner);
  }

  @Test
  public void line76() {
    loadGame(76);
    instantSummary();
    assertEquals(aip, winner);
  }

  @Test
  public void line77() {
    loadGame(77);
    instantSummary();
    assertEquals(aip, winner);
  }

  @Test
  public void line78() {
    loadGame(78);
    instantSummary();
    assertEquals(aip, winner);
  }

  @Test
  public void line79() {
    loadGame(79);
    instantSummary();
    assertEquals(aip, winner);
  }

  @Test
  public void line80() {
    loadGame(80);
    instantSummary();
    assertEquals(aip, winner);
  }

  @Test
  public void line81() {
    loadGame(81);
    instantSummary();
    assertEquals(aip, winner);
  }

  @Test
  public void line83() {
    loadGame(83);
    instantSummary();
    assertEquals(aip, winner);
  }

  @Test
  public void line84() {
    loadGame(84);
    instantSummary();
    assertEquals(aip, winner);
  }

  @Test
  public void line86() {
    loadGame(86);
    instantSummary();
    assertEquals(aip, winner);
  }

  @Test
  public void line87() {
    loadGame(87);
    instantSummary();
    assertEquals(aip, winner);
  }

  @Test
  public void line88() {
    loadGame(88);
    instantSummary();
    assertEquals(aip, winner);
  }

  @Test
  public void line90() {
    loadGame(90);
    instantSummary();
    assertEquals(aip, winner);
  }

  @Test
  public void line92() {
    loadGame(92);
    instantSummary();
    assertEquals(aip, winner);
  }

  @Test
  public void line94() {
    loadGame(94);
    instantSummary();
    assertEquals(aip, winner);
  }

  @Test
  public void line95() {
    loadGame(95);
    instantSummary();
    assertEquals(aip, winner);
  }

  @Test
  public void line96() {
    loadGame(96);
    instantSummary();
    assertEquals(aip, winner);
  }

  @Test
  public void line98() {
    loadGame(98);
    instantSummary();
    assertEquals(aip, winner);
  }

  @Test
  public void line100() {
    loadGame(100);
    instantSummary();
    assertEquals(aip, winner);
  }
}
