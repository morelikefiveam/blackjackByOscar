import java.io.File;
import java.util.Scanner;

public class MenuService {

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
    private static Player newPlayer(Scanner scanner, File file) {
        System.out.println("Enter name:");
        String name = App.getStringInput(scanner);

        int id = 1;

        if (file.exists()) {
            try (Scanner counter = new Scanner(file)) {
                while (counter.hasNextLine()) {
                    counter.nextLine();
                    id++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return new Player(name, new Hand(), 500, id);
    }

    // ---------------- LOAD PLAYER ----------------
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
                
                System.out.println(count + ". " + p.name + " (" + p.chips + ")");
                count++;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        int choice = App.getIntInput(scanner, 0, count - 1);

        if (choice == 0) return null;

        Player selected = head;

        for (int i = 1; i < choice; i++) {
            selected = selected.next;
        }

        return selected;
    }
}