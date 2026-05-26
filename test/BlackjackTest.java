import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BlackjackTest {

    // --- getTotal ---

    @Test
    public void testGetTotalSimple() {
        Hand hand = new Hand();
        hand.hand.add(new Card("Ten", "Hearts", 10));
        hand.hand.add(new Card("Seven", "Clubs", 7));
        assertEquals(17, hand.getTotal());
    }

    @Test
    public void testGetTotalAceAs11() {
        // Ace + Nine = 20, Ace stays as 11
        Hand hand = new Hand();
        hand.hand.add(new Card("Ace", "Hearts", 11));
        hand.hand.add(new Card("Nine", "Clubs", 9));
        assertEquals(20, hand.getTotal());
    }

    @Test
    public void testGetTotalAceAs1() {
        // Ace + Ten + Five = 16, Ace switches to 1
        Hand hand = new Hand();
        hand.hand.add(new Card("Ace", "Hearts", 11));
        hand.hand.add(new Card("Ten", "Clubs", 10));
        hand.hand.add(new Card("Five", "Diamonds", 5));
        assertEquals(16, hand.getTotal());
    }

    @Test
    public void testGetTotalTwoAces() {
        // Ace + Ace = 12, not 22
        Hand hand = new Hand();
        hand.hand.add(new Card("Ace", "Hearts", 11));
        hand.hand.add(new Card("Ace", "Clubs", 11));
        assertEquals(12, hand.getTotal());
    }

    @Test
    public void testGetTotalEmptyHand() {
        Hand hand = new Hand();
        assertEquals(0, hand.getTotal());
    }

    // --- isBust ---

    @Test
    public void testIsBustTrue() {
        Hand hand = new Hand();
        hand.hand.add(new Card("Ten", "Hearts", 10));
        hand.hand.add(new Card("Ten", "Clubs", 10));
        hand.hand.add(new Card("Five", "Diamonds", 5));
        assertTrue(hand.isBust());
    }

    @Test
    public void testIsBustFalse() {
        Hand hand = new Hand();
        hand.hand.add(new Card("Ten", "Hearts", 10));
        hand.hand.add(new Card("Ten", "Clubs", 10));
        assertFalse(hand.isBust());
    }

    @Test
    public void testIsBustExactly21() {
        // exactly 21 is not a bust
        Hand hand = new Hand();
        hand.hand.add(new Card("Ten", "Hearts", 10));
        hand.hand.add(new Card("Ten", "Clubs", 10));
        hand.hand.add(new Card("Ace", "Diamonds", 11));
        assertFalse(hand.isBust());
    }

    // --- Deck ---

    @Test
    public void testDeckHas52Cards() {
        Deck deck = new Deck();
        deck.buildDeck();
        assertEquals(52, deck.deck.size());
    }

    @Test
    public void testDeckBuildTwiceStill52() {
        // building twice without clearing should give 104 — good edge case
        Deck deck = new Deck();
        deck.buildDeck();
        deck.buildDeck();
        assertEquals(104, deck.deck.size());
    }

    // --- Player ---

    @Test
    public void testPlayerStartingChips() {
        Player player = new Player("Oscar", new Hand(), 500, 1);
        assertEquals(500, player.chips);
    }

    @Test
    public void testPlayerBetDefault() {
        Player player = new Player("Oscar", new Hand(), 500, 1);
        assertEquals(0, player.bet);
    }
}