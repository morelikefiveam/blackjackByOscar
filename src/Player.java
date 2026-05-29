/*
 * Represents a player at the blackjack table.
 * Stores the player's identity, chip balance, current hand,
 * and active bet. Also serves as a node in the linked list
 * of saved players, via the next field.
 */
public class Player {

    String name;
    Hand hand;
    int chips;
    int id;
    int bet = 0;
    Player next; // reference to the next player in the linked list

    public Player(String name, Hand hand, int chips, int id) {
        this.name = name;
        this.hand = hand;
        this.chips = chips;
        this.id = id;
    }

    /*
     * Places a bet by setting the active bet amount
     * and deducting it from the player's chip balance.
     */
    public void placeBet(int amount) {
        bet = amount;
        chips -= amount;
    }
}