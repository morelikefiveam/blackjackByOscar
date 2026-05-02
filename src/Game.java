import java.util.Scanner;

public class Game {
    Player player;
    Player dealer;
    Deck deck;

    public Game(Player player, Player dealer, Deck deck) {
        this.player = player;
        this.dealer = dealer;
        this.deck = deck;
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
        Scanner scanner = new Scanner(System.in);
        boolean standing = false;
        while (!standing && !player.hand.isBust()) {
            System.out.println("Your total:" + player.hand.getTotal());
            System.out.println("Hit or Stand? (h/s)");
            String input = scanner.nextLine();
            if (input.equals("h")) {
                player.hand.hit(deck);
                System.out.println(player.name + "'s hand: " + player.hand);
            } else if (input.equals("s")) {
                standing = true;
            }
        }
        while (dealer.hand.getTotal() < 17) {
            dealer.hand.hit(deck);
        }
        System.out.println("Dealer showing: " + dealer.hand);
        determineWinner();
    }

    public void placeBet() {
        Scanner betScanner = new Scanner(System.in);
        System.out.println("Current chip balance: " + player.chips);
        System.out.println("How many chips do you want to bet?");
        while (true) {
            int bet = betScanner.nextInt();
            
            if (bet > player.chips || bet <= 0) {
                System.out.println("Invalid bet! Try again");
            } else {
                player.bet = bet;
                player.chips -= player.bet;
                break;
            }
        }
    }

    public void determineWinner() {
        if (player.hand.isBust()) {
            System.out.println("Dealer wins!");
            System.out.println("You lost " + player.bet + " chips!");
            System.out.println("Current chip balance: " + player.chips);
        } else if (dealer.hand.isBust()) {
            System.out.println("You win!");
            System.out.println("Added " + player.bet *2 + " chips to your balance");  
            player.chips += player.bet * 2;
            System.out.println("Current chip balance: " + player.chips);
        } else if (player.hand.getTotal() < dealer.hand.getTotal()) {
            System.out.println("Dealer wins!");
            System.out.println("You lost " + player.bet + " chips!");
            System.out.println("Current chip balance: " + player.chips);
        } else if (dealer.hand.getTotal() < player.hand.getTotal()) {
            System.out.println("You win!");
            System.out.println("Added " + player.bet *2 + " chips to your balance");
             player.chips += player.bet * 2;
            System.out.println("Current chip balance: " + player.chips);
        } else if (player.hand.getTotal() == dealer.hand.getTotal()) {
            System.out.println("Draw!");
            System.out.println("Returning your chips...");
            player.chips += player.bet; 
            System.out.println("Current chip balance: " + player.chips);
        }
    }
}