public class Player {
    String name;
    Hand hand;
    int chips;
    int bet = 0;
    
    public Player(String name, Hand hand, int chips){
        this.name = name;
        this.hand = hand;
        this.chips = chips;
    }
}
