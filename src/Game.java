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

    public void deal() {
        player.hand.hit(deck);
        dealer.hand.hit(deck);
        player.hand.hit(deck);
        dealer.hand.hit(deck);

        System.out.println(player.name + "'s hand: " + player.hand);
        System.out.println("Dealer showing: " + dealer.hand.hand.get(0));
    }

    // ================= MAIN FLOW =================

    public void playRound() {
        playerTurn();
        dealerTurn();
        Rules.determineWinner(player, dealer);
    }

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

    private void dealerTurn() {
        while (dealer.hand.getTotal() < 17) {
            dealer.hand.hit(deck);
        }

        System.out.println("Dealer hand: " + dealer.hand);
    }

    // ================= BETTING =================

    public void placeBet() {
        System.out.println("Current chip balance: " + player.chips);

        int bet = readBet();
        player.placeBet(bet);
    }

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