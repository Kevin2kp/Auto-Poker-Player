package pokergame.card;

public class Card implements Comparable<Card> {

  private final Suit suit;
  private final int rank;

  public Card(Suit suit, int rank) {
    if (rank < 2 || rank > 14) {
      throw new IllegalArgumentException("Rank must be between 2 and 14");
    }
    this.suit = suit;
    this.rank = rank;
  }

  public Suit getSuit() {
    return suit;
  }

  public int getRank() {
    return rank;
  }

  @Override
  public boolean equals(Object other) {
    if (other == null || other.getClass() != this.getClass()) {
      return false;
    }

    return hashCode() == other.hashCode();
  }

  @Override
  public int compareTo(Card o) {
    return hashCode() - o.hashCode();
  }

  @Override
  public int hashCode() {
    return suit.getRank() + rank * 1000000;
  }

  @Override
  public String toString() {

    String rankString;
    switch (rank) {
      case 11:
        rankString = "Jack";
        break;
      case 12:
        rankString = "Queen";
        break;
      case 13:
        rankString = "King";
        break;
      case 14:
        rankString = "Ace";
        break;

      default:
        rankString = "" + rank;
    }

    return String.format("%s of %s", rankString, suit.toString());
  }

  public enum Suit {
    Clubs("Clubs", 1),
    Diamonds("Diamonds", 2),
    Hearts("Hearts", 3),
    Spades("Spades", 4);

    private final int value;
    private final String name;

    Suit(String name, int value) {
      this.value = value;
      this.name = name;
    }

    public int getRank() {
      return this.value;
    }

    @Override
    public String toString() {
      return this.name;
    }
  }
}
