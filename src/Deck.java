import java.util.ArrayList;
import java.util.Collections;

public class Deck {

    public ArrayList<Card> deck = new ArrayList<Card>(52);

    // the four suits in a standard deck
    String[] suits = {"Hearts", "Diamonds", "Spades", "Clubs"};

    // card names in ascending order, excluding Ace which is placed last
    String[] name = {"Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King", "Ace"};

    /*
     * Blackjack point values corresponding to each card name above.
     * Face cards (Jack, Queen, King) are worth 10.
     * Ace is worth 11 by default — reduction to 1 is handled in Hand.
     */
    int[] value = {2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11};

    /*
     * Populates the deck with all 52 cards by combining
     * every suit with every card name and its corresponding value.
     */
    public void buildDeck() {
        for (String suit : suits) {
            for (int i = 0; i < name.length; i++) {
                deck.add(new Card(name[i], suit, value[i]));
            }
        }
    }

    /*
     * Randomly shuffles the deck using Java's Collections utility.
     * Should be called after buildDeck() before each round.
     */
    public void shuffle() {
        Collections.shuffle(deck);
    }
}