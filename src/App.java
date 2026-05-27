import java.util.Scanner;

public class App {

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        while (true) {

            Player player = MenuService.showMenu(scanner);
            if (player == null) continue;

            Deck deck = new Deck();
            Player dealer = new Player("Dealer", new Hand(), 0, 0);

            Game game = new Game(player, dealer, deck, scanner);

            boolean playing = true;

            while (playing && player.chips > 0) {

                deck.deck.clear();
                deck.buildDeck();
                deck.shuffle();

                player.hand.hand.clear();
                dealer.hand.hand.clear();

                game.placeBet();
                game.deal();
                game.playRound();

                if (player.chips <= 0) break;

                System.out.println("Play again? (y/n)");
                playing = scanner.nextLine().trim().equalsIgnoreCase("y");
            }

            if (player.chips <= 0) {
                System.out.println("You're broke. Game over.");
            }
        }
    }


    public static int getIntInput(Scanner scanner, int min, int max) {

        while (true) {
            String input = scanner.nextLine().trim();

            if (!input.matches("-?\\d+")) {
                System.out.println("Invalid input. Enter a whole number.");
                continue;
            }

            int value = Integer.parseInt(input);

            if (value >= min && value <= max) {
                return value;
            }

            System.out.println("Enter a number between " + min + " and " + max);
        }
    }

    public static String getStringInput(Scanner scanner) {

        while (true) {
            String input = scanner.nextLine().trim();

            if (!input.isEmpty()) {
                return input;
            }

            System.out.println("Input cannot be empty.");
        }
    }
}