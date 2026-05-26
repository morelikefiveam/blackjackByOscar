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

        Rules.determineWinner(player, dealer);
    }

    public void placeBet() {
        BetManager.placeBet(player, scanner);
    }
}