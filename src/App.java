import java.util.Scanner;
import java.io.File;
public class App {

    /*
     * Entry point of the application.
     * Runs the outer menu loop, allowing players to start or load a game
     * and return to the menu when done.
     * The inner loop runs rounds until the player quits or runs out of chips.
     */
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        while (true) {

            Player player = MenuService.showMenu(scanner);
            if (player == null)
                continue; // null means the player went back to the menu

            Deck deck = new Deck();
            Player dealer = new Player("Dealer", new Hand(), 0, 0);

            Game game = new Game(player, dealer, deck, scanner);

            boolean playing = true;

            /*
             * Round loop — rebuilds and shuffles the deck each round,
             * clears both hands, then runs a full round of blackjack.
             */
            while (playing && player.chips > 0) {

                deck.deck.clear();
                deck.buildDeck();
                deck.shuffle();

                player.hand.hand.clear();
                dealer.hand.hand.clear();

                game.placeBet();
                game.deal();
                game.playRound();

                if (player.chips <= 0)
                    break;

                System.out.println("Play again? (y/n)");
                playing = scanner.nextLine().trim().equalsIgnoreCase("y");
            }

            if (player.chips <= 0) {
                System.out.println(player.name + ", leave here at once!");
                System.out.println("Your name has been struck from the record.");
                System.out.println("You may start anew or wallow in misery for eternity...");
                SaveManager.delete(new File("player.txt"), player.id);
            }
        }
    }

    /*
     * Repeatedly prompts for an integer until a valid value
     * within the range [min, max] is entered.
     * Rejects non-numeric input and out-of-range values.
     * Parameters:
     * scanner - the active Scanner reading from System.in
     * min - minimum acceptable value (inclusive)
     * max - maximum acceptable value (inclusive)
     * Returns the validated integer.
     */
    public static int getIntInput(Scanner scanner, int min, int max) {

        while (true) {
            String input = scanner.nextLine().trim();

            if (!input.matches("-?\\d+")) {
                System.out.println("Leave here at once");
                continue;
            }

            int value = Integer.parseInt(input);

            if (value >= min && value <= max) {
                return value;
            }

            System.out.println("Enter a number between " + min + " and " + max);
        }
    }

    /*
     * Repeatedly prompts for a string until a non-empty value is entered.
     * Parameters:
     * scanner - the active Scanner reading from System.in
     * Returns the validated string.
     */
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