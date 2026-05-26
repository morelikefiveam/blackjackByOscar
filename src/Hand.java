import java.util.ArrayList;

public class Hand {
    ArrayList<Card> hand = new ArrayList<Card>();

    public void hit(Deck deck) {
        hand.add(deck.deck.remove(0));
    }

    public int getTotal() {
        int total = 0;
        int aces = 0;
        for (Card card : hand) {
            total += card.value;
            if (card.value == 11) {
                aces++;
            }
        }
        while (total > 21 && aces > 0) {
            total -= 10;
            aces--;
        }
        return total;
    }

    public boolean isBust() {
        return getTotal() > 21;
    }

    public String toString() {
        String result = "";
        for (Card card : hand) {
            result += card + ", ";

        }
        result += "Total: " + getTotal();
        return result;
    }

    public int getValue() {
        int total = 0;

        for (Card card : hand) {
            total += card.value;
        }

        return total;
    }
}
