public class Player {
    String name;
    Hand hand;
    int chips;
    int id;
    int bet = 0;
    Player next; // recursive data structure (linked list node)

    public Player(String name, Hand hand, int chips, int id){
        this.id = id;
        this.name = name;
        this.hand = hand;
        this.chips = chips; 
        
    }
}
