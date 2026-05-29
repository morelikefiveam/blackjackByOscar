public class Card {

    public String name;
    public String suit;
    public int value;

    /*
     * Represents a single playing card with a name, suit and point value.
     * Value reflects the card's worth in blackjack:
     * number cards are face value, face cards are 10, and Aces are 11
     * (with Ace reduction handled in Hand).
     */
    public Card(String name, String suit, int value) {
        this.name = name;
        this.suit = suit;
        this.value = value;
    }

    public String toString() {
        return name + " of " + suit;
    }
}