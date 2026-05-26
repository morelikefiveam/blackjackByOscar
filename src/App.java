import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

public class App {

    public static int getIntInput(Scanner scanner, int min, int max) {
        int value;

        while (true) {

            String input = scanner.nextLine().trim();

            // reject anything that is not an integer
            if (!input.matches("-?\\d+")) {
                System.out.println("Invalid input. Please enter a whole number.");
                continue;
            }

            value = Integer.parseInt(input);

            if (value >= min && value <= max) {
                return value;
            }

            System.out.println("Enter a number between " + min + " and " + max);
        }
    }

    public static String getStringInput(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine().trim();
            if (!input.isEmpty())
                return input;
            System.out.println("Input cannot be empty.");
        }
    }

    public static boolean isBlackjack(Player p) {
        return p.hand.getValue() == 21 && p.hand.hand.size() == 2;
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            menu(args, scanner);
        }
    }

    // ================= MENU =================
    public static void menu(String[] args, Scanner scanner) throws Exception {

        File saveFile = new File("player.txt");

        System.out.println("--- THE TOWER ---");
        System.out.println("1. New Game");
        System.out.println("2. Load Game");

        int decision = getIntInput(scanner, 1, 2);

        String name = "";
        int chips = 500;
        int newId = 1;

        // ================= NEW GAME =================
        if (decision == 1) {

            System.out.println("What is your name?");
            name = getStringInput(scanner);

            if (saveFile.exists()) {
                Scanner counter = new Scanner(saveFile);

                while (counter.hasNextLine()) {
                    counter.nextLine();
                    newId++;
                }

                counter.close();
            }
        }

        // ================= LOAD GAME =================
        else {

            Player head = null;
            Player current = null;
            int count = 1;

            if (saveFile.exists() && saveFile.length() > 0) {

                Scanner fileScanner = new Scanner(saveFile);

                System.out.println("Choose a player:");
                System.out.println("0. Return to Main Menu");

                while (fileScanner.hasNextLine()) {

                    String line = fileScanner.nextLine();
                    if (line.isEmpty())
                        continue;

                    String[] parts = line.split(",");
                    if (parts.length != 3)
                        continue;

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
                    name = getStringInput(scanner);

                } else {

                    int choice = getIntInput(scanner, 0, count - 1);

                    // return to menu (controlled recursion)
                    if (choice == 0) {
                        return;
                    }

                    Player chosen = head;

                    for (int i = 1; i < choice && chosen != null; i++) {
                        chosen = chosen.next;
                    }

                    if (chosen == null) {
                        System.out.println("Invalid selection. Starting new game.");
                        System.out.println("What is your name?");
                        name = getStringInput(scanner);
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
                name = getStringInput(scanner);
            }
        }

        // ================= GAME SETUP =================

        Deck deck = new Deck();
        Player player = new Player(name, new Hand(), chips, newId);
        Player dealer = new Player("Dealer", new Hand(), 0, 0);
        Game game = new Game(player, dealer, deck, scanner);

        boolean playing = true;

        // ================= GAME LOOP =================
        while (playing && player.chips > 0) {

            deck.deck.clear();
            deck.buildDeck();
            deck.shuffle();

            player.hand.hand.clear();
            dealer.hand.hand.clear();

            game.placeBet();
            game.deal();

            // ================= BLACKJACK =================
            if (isBlackjack(player)) {

                System.out.println("BLACKJACK!");

                if (isBlackjack(dealer)) {
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
                        if (line.isEmpty())
                            continue;

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

                saveManager.save(saveFile, lines);

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

        // ================= PLAYER DEATH RESET =================
        if (player.chips <= 0) {

            System.out.println(player.name + ", leave here at once!");
            System.out.println("Your name has been struck from the record.");

            ArrayList<String> lines = new ArrayList<>();

            if (saveFile.exists()) {

                Scanner fileScanner = new Scanner(saveFile);

                while (fileScanner.hasNextLine()) {

                    String line = fileScanner.nextLine();
                    if (line.isEmpty())
                        continue;

                    if (!line.startsWith(player.id + ",")) {
                        lines.add(line);
                    }
                }

                fileScanner.close();
            }

            saveManager.save(saveFile, lines);
        }

        System.out.println("\nReturning to main menu...\n");
    }
}