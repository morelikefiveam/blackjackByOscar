import java.util.ArrayList;

public class Hand {

    ArrayList<Card> hand = new ArrayList<Card>();

    /*
     * Removes the top card from the deck and adds it to this hand.
     * Used for both the initial deal and subsequent hits.
     */
    public void hit(Deck deck) {
        hand.add(deck.deck.remove(0));
    }

    /*
     * Calculates the total point value of the hand.
     * Aces are initially counted as 11, but reduced to 1
     * if the total exceeds 21 — one reduction per Ace.
     */
    public int getTotal() {
        int total = 0;
        int aces = 0;

        for (Card card : hand) {
            total += card.value;
            if (card.value == 11) {
                aces++;
            }
        }

        /*
         * Reduce Ace value from 11 to 1 (subtract 10) for each Ace
         * as long as the hand is bust and there are Aces to reduce.
         */
        while (total > 21 && aces > 0) {
            total -= 10;
            aces--;
        }

        return total;
    }

    public boolean isBust() {
        return getTotal() > 21;
    }

    /*
     * Returns a string listing all cards in the hand
     * followed by the current total.
     */
    public String toString() {
        String result = "";

        for (Card card : hand) {
            result += card + ", ";
        }

        result += "Total: " + getTotal();
        return result;
    }

    /*
     * Returns the raw sum of all card values without Ace reduction.
     * Used where the exact numeric value is needed regardless of bust status.
     */
    public int getValue() {
        int total = 0;

        for (Card card : hand) {
            total += card.value;
        }

        return total;
    }
}