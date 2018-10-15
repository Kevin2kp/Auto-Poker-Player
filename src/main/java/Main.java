import io.CardScanner;
import io.Config;
import io.InputParser;
import io.out.Logger;
import io.out.QuietLogger;
import io.out.VerboseLogger;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import pokergame.GameSetup;
import pokergame.PokerGame;
import pokergame.card.Card;
import pokergame.player.PokerPlayer;

public class Main {

  private static final String REGULAR_DECK =
      "C2 C3 C4 C5 C6 C7 C8 C9 C10 CJ CQ CK CA "
          + "D2 D3 D4 D5 D6 D7 D8 D9 D10 DJ DQ DK DA "
          + "H2 H3 H4 H5 H6 H7 H8 H9 H10 HJ HQ HK HA "
          + "S2 S3 S4 S5 S6 S7 S8 S9 S10 SJ SQ SK SA";

  private OutputStream outputStream;
  private Scanner lineScanner;
  private Logger logger;
  private PokerGame game;

  public Main(String[] argv) throws FileNotFoundException {
    Config config = InputParser.parseConfig(argv);

    if (config.getInputFile() != null) {
      lineScanner = new Scanner(new FileInputStream(config.getInputFile()));
    } else {
      lineScanner = new Scanner(REGULAR_DECK);
      GameSetup.shuffleDeck(true);
    }

    if (config.getOutputFile() != null) {
      outputStream = new FileOutputStream(config.getOutputFile());
    } else {
      outputStream = System.out;
    }
    logger = config.isQuiet() ? new QuietLogger(outputStream) : new VerboseLogger(outputStream);

    if (config.getRounds() != null) {
      GameSetup.setMaxRounds(config.getRounds());
    }
  }

  public void run() throws IOException {
    int count = 0;
    while (loadNextGame()) {
      logger.printGameCount(count);
      gameLoop();
      count++;
    }

    lineScanner.close();
    outputStream.close();
  }

  public boolean loadNextGame() {
    if (lineScanner.hasNextLine()) {
      String line = lineScanner.nextLine();
      loadDeck(line);
      game = GameSetup.getGame();
      return true;
    }

    return false;
  }

  public void loadDeck(String deckString) {
    ArrayList<Card> deck = new ArrayList<>();
    CardScanner scanner = new CardScanner(deckString);
    while (scanner.hasNext()) {
      Card card = scanner.next();
      deck.add(card);
    }
    GameSetup.setDeckCards(deck);
  }

  public void gameLoop() {
    while (!game.isGameOver()) {

      logger.printRound(game.getCurrentRound());
      processPlayerTurns();
      game.endRound();
    }

    PokerPlayer winner = game.computeWinner();
    logger.printWinner(winner);
  }

  private void processPlayerTurns() {
    while (!game.allPlayersDone()) {

      PokerPlayer player = game.activePlayer();
      logger.printPlayer(player);
      logger.printHand(player.getCards());

      game.nextPlayerTurn();
      game.dealCards();

      Collection<Card> discarded = game.cardsDiscardedByLastPlayer();
      Collection<Card> received = game.cardsReceivedByLastPlayer();

      logger.printDiscarded(player, discarded);
      logger.printReceived(player, received);
    }
  }

  public static void main(String[] argv) {

    try {
      Main main = new Main(argv);
      main.run();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}