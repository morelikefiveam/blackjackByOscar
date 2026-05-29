import java.util.Scanner;

public class Game {

    private final Player player;
    private final Player dealer;
    private final Deck deck;
    private final Scanner scanner;

    public Game(Player player, Player dealer, Deck deck, Scanner scanner) {
        this.player = player;
        this.dealer = dealer;
        this.deck = deck;
        this.scanner = scanner;
    }

    // ================= SETUP =================

    /*
     * Deals two cards to the player and two to the dealer.
     * Shows the player's full hand and only the dealer's first card,
     * as per standard blackjack rules.
     */
    public void deal() {
        player.hand.hit(deck);
        dealer.hand.hit(deck);
        player.hand.hit(deck);
        dealer.hand.hit(deck);

        System.out.println(player.name + "'s hand: " + player.hand);
        System.out.println("Dealer showing: " + dealer.hand.hand.get(0));
    }

    // ================= MAIN FLOW =================

    /*
     * Runs a full round of blackjack in order:
     * player's turn, then dealer's turn, then winner determination.
     */
    public void playRound() {
        playerTurn();
        dealerTurn();
        Rules.determineWinner(player, dealer);
    }

    /*
     * Handles the player's turn.
     * Repeatedly prompts for hit or stand until the player stands or busts.
     * Rejects invalid input and prompts again.
     */
    private void playerTurn() {
        while (!player.hand.isBust()) {

            System.out.println("Your total: " + player.hand.getTotal());
            System.out.println("Hit or Stand? (h/s)");

            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("h")) {
                player.hand.hit(deck);
                System.out.println(player.name + "'s hand: " + player.hand);

            } else if (input.equals("s")) {
                break;

            } else {
                System.out.println("Invalid input. Please enter h or s.");
            }
        }
    }

    /*
     * Handles the dealer's turn.
     * The dealer must hit until their total reaches 17 or more —
     * this mirrors standard casino blackjack rules.
     */
    private void dealerTurn() {
        while (dealer.hand.getTotal() < 17) {
            dealer.hand.hit(deck);
        }

        System.out.println("Dealer hand: " + dealer.hand);
    }

    // ================= BETTING =================

    /*
     * Displays the player's current chip balance and prompts for a bet.
     * Delegates input reading to readBet() and applies the bet to the player.
     */
    public void placeBet() {
        System.out.println("Current chip balance: " + player.chips);

        int bet = readBet();
        player.placeBet(bet);
    }

    /*
     * Repeatedly prompts the player for a valid bet amount.
     * Rejects non-numeric input and values outside the valid range.
     * Returns the validated bet as an integer.
     */
    private int readBet() {

        while (true) {

            System.out.println("Enter your bet (1 - " + player.chips + "):");
            String input = scanner.nextLine().trim();

            if (!input.matches("\\d+")) {
                System.out.println("Invalid input. Enter a whole number.");
                continue;
            }

            int bet = Integer.parseInt(input);

            if (bet > 0 && bet <= player.chips) {
                return bet;
            }

            System.out.println("Bet must be between 1 and " + player.chips);
        }
    }
}