public class Player {
    String name;
    Hand hand;
    int chips;
    int id;
    int bet = 0;
    Player next;

    public Player(String name, Hand hand, int chips, int id) {
        this.name = name;
        this.hand = hand;
        this.chips = chips;
        this.id = id;
    }

    public void placeBet(int amount) {
        bet = amount;
        chips -= amount;
    }
}