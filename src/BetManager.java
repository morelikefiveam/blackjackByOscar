import java.util.Scanner;

public class BetManager {

    public static void placeBet(Player player, Scanner scanner) {

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
}