import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    public ArrayList<Card> deck = new ArrayList<Card>(52);
    String[] suits = {"Hearts", "Diamonds", "Spades", "Clubs"};
    String[] name = { "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King", "Ace"};
    int[] value = { 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11};
    
    public void buildDeck(){ 
        for (String suit : suits){ 
            for (int i = 0; i < name.length; i++){
                deck.add(new Card(suit, name[i], value[i]));
            }
        }
    }
    public void shuffle(){ 
        Collections.shuffle(deck);
    }
    
}
