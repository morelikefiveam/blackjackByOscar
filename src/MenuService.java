import java.io.File;
import java.util.Scanner;

public class MenuService {

    /*
     * Displays the main menu and returns the selected Player.
     * Loops until a valid choice is made — returning null signals
     * the player chose to go back to the menu from the load screen.
     */
    public static Player showMenu(Scanner scanner) {
        File saveFile = new File("player.txt");

        while (true) {
            System.out.println("--- THE TOWER ---");
            System.out.println("1. New Game");
            System.out.println("2. Load Game");

            int choice = App.getIntInput(scanner, 1, 2);

            if (choice == 1) {
                return newPlayer(scanner, saveFile);
            }

            Player loaded = loadPlayer(scanner, saveFile);
            if (loaded != null) {
                return loaded;
            }
        }
    }

    // ---------------- NEW PLAYER ----------------

    /*
     * Creates a new Player with a user-entered name and 500 starting chips.
     * Generates a unique ID by counting existing lines in the save file
     * and incrementing by 1.
     */
    private static Player newPlayer(Scanner scanner, File file) {
        System.out.println("Enter name:");
        String name = App.getStringInput(scanner);

        int id = 1;

        if (file.exists()) {
            try (Scanner counter = new Scanner(file)) {
                while (counter.hasNextLine()) {
                    counter.nextLine();
                    id++; // one increment per existing player
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return new Player(name, new Hand(), 500, id);
    }

    // ---------------- LOAD PLAYER ----------------

    /*
     * Reads all saved players from the file and builds a linked list.
     * Displays each player with a number and prompts the user to choose one.
     * Returns null if the file is empty or the player selects 0 to go back.
     */
    private static Player loadPlayer(Scanner scanner, File file) {

        if (!file.exists() || file.length() == 0) {
            System.out.println("No saves found.");
            return null;
        }

        Player head = null;
        Player current = null;
        int count = 1;

        try (Scanner fileScanner = new Scanner(file)) {
            System.out.println("0. Return to main menu");

            /*
             * Read each line, parse the player data, and append to the
             * linked list. Each node's next field points to the following player.
             */
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                if (line.isEmpty()) continue;

                String[] parts = line.split(",");
                Player p = new Player(
                    parts[1],
                    new Hand(),
                    Integer.parseInt(parts[2]),
                    Integer.parseInt(parts[0])
                );

                if (head == null) {
                    head = p;
                } else {
                    current.next = p;
                }

                current = p;
                System.out.println(count + ". " + p.name + " (" + p.chips + " chips)");
                count++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        int choice = App.getIntInput(scanner, 0, count - 1);

        if (choice == 0) return null;

        /*
         * Traverse the linked list to find the player at the chosen position.
         * List is 1-indexed from the user's perspective.
         */
        Player selected = head;
        for (int i = 1; i < choice; i++) {
            selected = selected.next;
        }

        return selected;
    }
}