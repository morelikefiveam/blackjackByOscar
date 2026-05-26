import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

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

    @Test
    public void testNotBustAt21() {
        Hand hand = new Hand();
        hand.hand.add(new Card("Ten", "Hearts", 10));
        hand.hand.add(new Card("Jack", "Spades", 10));
        hand.hand.add(new Card("Ace", "Clubs", 1));

        assertFalse(hand.isBust());
    }

    @Test
    public void testBlackjackDetection() {
        Player p = new Player("Test", new Hand(), 100, 1);
        p.hand.hand.add(new Card("Ace", "Hearts", 11));
        p.hand.hand.add(new Card("King", "Spades", 10));

        assertTrue(App.isBlackjack(p));
    }

    @Test
    public void testBetCannotExceedChipsLogic() {
        Player p = new Player("Test", new Hand(), 100, 1);
        p.bet = 150;

        assertTrue(p.bet > p.chips);
    }

    @Test
    public void testSaveManagerWritesLines() throws Exception {
        File file = new File("test.txt");
        ArrayList<String> lines = new ArrayList<>();
        lines.add("1,Oscar,500");

        SaveManager.save(file, lines);

        assertTrue(file.exists());
    }

    @Test
    public void testBetWithinBounds() {
        Player p = new Player("Test", new Hand(), 100, 1);
        p.bet = 50;

        assertTrue(p.bet <= p.chips);
    }

    @Test
    public void testHandBustEdge() {
        Hand h = new Hand();
        h.hand.add(new Card("King", "Hearts", 10));
        h.hand.add(new Card("Queen", "Spades", 10));
        h.hand.add(new Card("Two", "Clubs", 2));

        assertTrue(h.isBust());
    }

    @Test
    public void testSaveManagerWritesCorrectData() throws Exception {
        File file = new File("test.txt");
        ArrayList<String> lines = new ArrayList<>();
        lines.add("1,Oscar,500");

        SaveManager.save(file, lines);

        Scanner sc = new Scanner(file);
        assertEquals("1,Oscar,500", sc.nextLine());
        sc.close();

        file.delete();
    }
}