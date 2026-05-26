import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;

public class App {

    public static int getIntInput(Scanner scanner, int min, int max) {
        int value;

        while (true) {
            try {
                value = Integer.parseInt(scanner.nextLine().trim());

                if (value >= min && value <= max) {
                    return value;
                }

                System.out.println("Enter a number between " + min + " and " + max);

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        menu(args, scanner);
    }

    // ✅ RECURSIVE MENU (safe recursion instead of main recursion)
    public static void menu(String[] args, Scanner scanner) throws Exception {
        File saveFile = new File("player.txt");

        System.out.println("--- THE TOWER ---");
        System.out.println("1. New Game");
        System.out.println("2. Load Game");

        int decision = getIntInput(scanner, 1, 2);

        String name = "";
        int chips = 500;
        int newId = 1;

        if (decision == 1) {

            System.out.println("What is your name?");
            name = scanner.nextLine();

            if (saveFile.exists()) {
                Scanner counter = new Scanner(saveFile);

                while (counter.hasNextLine()) {
                    counter.nextLine();
                    newId++;
                }

                counter.close();
            }

        } else {

            Player head = null;
            Player current = null;
            int count = 1;

            if (saveFile.exists() && saveFile.length() > 0) {

                Scanner fileScanner = new Scanner(saveFile);

                System.out.println("Choose a player:");
                System.out.println("0. Return to Main Menu");

                while (fileScanner.hasNextLine()) {

                    String line = fileScanner.nextLine();
                    if (line.isEmpty()) continue;

                    String[] parts = line.split(",");

                    if (parts.length != 3) continue;

                    int savedId = Integer.parseInt(parts[0].trim());
                    String savedName = parts[1];
                    int savedChips = Integer.parseInt(parts[2].trim());

                    Player newPlayer = new Player(savedName, new Hand(), savedChips, savedId);

                    if (head == null) {
                        head = newPlayer;
                        current = head;
                    } else {
                        current.next = newPlayer;
                        current = current.next;
                    }

                    System.out.println(count + ". " + savedName + " (" + savedChips + " chips)");
                    count++;
                }

                fileScanner.close();

                if (head == null) {
                    System.out.println("No saves detected! Starting new game.");
                    System.out.println("What is your name?");
                    name = scanner.nextLine();

                } else {

                    int choice = getIntInput(scanner, 0, count - 1);

                    // 🔁 recursion back to menu (SAFE)
                    if (choice == 0) {
                        menu(args, scanner);
                        return;
                    }

                    Player chosen = head;

                    for (int i = 1; i < choice && chosen != null; i++) {
                        chosen = chosen.next;
                    }

                    if (chosen == null) {
                        System.out.println("Invalid selection. Starting new game.");
                        System.out.println("What is your name?");
                        name = scanner.nextLine();
                        chips = 500;
                        newId = 1;
                    } else {
                        name = chosen.name;
                        chips = chosen.chips;
                        newId = chosen.id;
                    }
                }

            } else {
                System.out.println("No saves detected! Starting new game.");
                System.out.println("What is your name?");
                name = scanner.nextLine();
            }
        }

        // ================= GAME START =================

        Deck deck = new Deck();
        Player player = new Player(name, new Hand(), chips, newId);
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

            // Blackjack check (2-card rule)
            if (player.hand.getValue() == 21 && player.hand.hand.size() == 2) {

                System.out.println("BLACKJACK!");

                if (dealer.hand.getValue() == 21 && dealer.hand.hand.size() == 2) {
                    System.out.println("Push! Dealer also has Blackjack.");
                } else {
                    System.out.println("Blackjack pays double!");
                    player.chips += player.bet * 2;
                }

            } else {
                game.play();
            }

            // ================= SAVE =================

            if (player.chips > 0) {

                ArrayList<String> lines = new ArrayList<>();
                boolean found = false;

                if (saveFile.exists()) {

                    Scanner fileScanner = new Scanner(saveFile);

                    while (fileScanner.hasNextLine()) {

                        String line = fileScanner.nextLine();
                        if (line.isEmpty()) continue;

                        if (line.startsWith(player.id + ",")) {
                            lines.add(player.id + "," + player.name + "," + player.chips);
                            found = true;
                        } else {
                            lines.add(line);
                        }
                    }

                    fileScanner.close();
                }

                if (!found) {
                    lines.add(player.id + "," + player.name + "," + player.chips);
                }

                FileWriter writer = new FileWriter("player.txt");

                for (String line : lines) {
                    writer.write(line + "\n");
                }

                writer.close();

                String input = "";

                while (!input.equals("y") && !input.equals("n")) {
                    System.out.println("Play again? (y/n)");
                    input = scanner.nextLine().trim().toLowerCase();

                    if (!input.equals("y") && !input.equals("n")) {
                        System.out.println("Please enter y or n.");
                    }
                }

                if (input.equals("n")) {
                    System.out.println("Goodbye! And remember, the house always wins...");
                    playing = false;
                }
            }
        }

        // ================= CHIP LOSS RESET =================

        if (player.chips <= 0) {

            System.out.println(player.name + ", leave here at once!");
            System.out.println("Your name has been struck from the record.");

            ArrayList<String> lines = new ArrayList<>();

            if (saveFile.exists()) {

                Scanner fileScanner = new Scanner(saveFile);

                while (fileScanner.hasNextLine()) {

                    String line = fileScanner.nextLine();
                    if (line.isEmpty()) continue;

                    if (!line.startsWith(player.id + ",")) {
                        lines.add(line);
                    }
                }

                fileScanner.close();
            }

            FileWriter writer = new FileWriter("player.txt");

            for (String line : lines) {
                writer.write(line + "\n");
            }

            writer.close();
        }

        
        System.out.println("\nReturning to main menu...\n");
        menu(args, scanner);
    }
}