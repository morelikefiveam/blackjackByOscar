import java.util.Scanner;

public class Game {
    Player player;
    Player dealer;
    Deck deck;
    Scanner scanner;

    public Game(Player player, Player dealer, Deck deck, Scanner scanner) {
        this.player = player;
        this.dealer = dealer;
        this.deck = deck;
        this.scanner = scanner;
    }

    public void deal() {
        player.hand.hit(deck);
        dealer.hand.hit(deck);
        player.hand.hit(deck);
        dealer.hand.hit(deck);

        System.out.println(player.name + "'s hand: " + player.hand);
        System.out.println("Dealer showing: " + dealer.hand.hand.get(0));
    }

    public void play() {

        boolean standing = false;

        while (!standing && !player.hand.isBust()) {

            System.out.println("Your total: " + player.hand.getTotal());
            System.out.println("Hit or Stand? (h/s)");

            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("h")) {
                player.hand.hit(deck);
                System.out.println(player.name + "'s hand: " + player.hand);

            } else if (input.equals("s")) {
                standing = true;

            } else {
                System.out.println("Invalid input. Please enter h or s.");
            }
        }

        while (dealer.hand.getTotal() < 17) {
            dealer.hand.hit(deck);
        }

        System.out.println("Dealer hand: " + dealer.hand);
        determineWinner();
    }

    public void placeBet() {

        System.out.println("Current chip balance: " + player.chips);

        int bet;

        while (true) {

            System.out.println("Enter your bet (1 - " + player.chips + "):");

            String input = scanner.nextLine().trim();

            if (!input.matches("\\d+")) {
                System.out.println("Invalid input. Enter a whole number.");
                continue;
            }

            bet = Integer.parseInt(input);

            if (bet > 0 && bet <= player.chips) {
                break;
            }

            System.out.println("Bet must be between 1 and " + player.chips);
        }

        player.bet = bet;

        player.chips -= bet;
    }

    public void determineWinner() {

        int bet = player.bet;

        if (player.hand.isBust()) {
            System.out.println("Dealer wins!");

            // bet already deducted in placeBet → nothing to do

        } else if (dealer.hand.isBust()) {
            System.out.println("You win!");
            player.chips += bet * 2;

        } else if (player.hand.getTotal() < dealer.hand.getTotal()) {
            System.out.println("Dealer wins!");

        } else if (dealer.hand.getTotal() < player.hand.getTotal()) {
            System.out.println("You win!");
            player.chips += bet * 2;

        } else {
            System.out.println("Draw!");
            player.chips += bet; // refund original bet
        }

        System.out.println("Current chip balance: " + player.chips);
    }
}