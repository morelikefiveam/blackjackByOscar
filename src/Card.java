public class Card {
    public String name;
    public String suit;
    public int value;
    

    public Card(String name, String suit, int value){
        this.name = name;
        this.suit = suit;
        this.value = value;
    }
    public String toString(){ 
            return suit + " of " + name;
        }
}
