import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        File saveFile = new File("player.txt");
        String name = "";
        int chips = 500;
        int newId = 1;

        System.out.println("--- THE TOWER ---");
        System.out.println("1. New Game");
        System.out.println("2. Load Game");
        int decision = Integer.parseInt(scanner.nextLine().trim());
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
        } else if (decision == 2) {
            Player head = null;
            Player current = null;
            int count = 1;

            if (saveFile.exists() && saveFile.length() > 0) {
                Scanner fileScanner = new Scanner(saveFile);
                System.out.println("Choose a player:");
                while (fileScanner.hasNextLine()) {
                    String line = fileScanner.nextLine();
                    if (line.isEmpty()) {
                        continue;
                    }
                    String[] parts = line.split(",");
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
                    System.out.println("No saves detected! Starting new game");
                    System.out.println("What is your name?");
                    name = scanner.nextLine();
                } else {
                    int choice = Integer.parseInt(scanner.nextLine().trim());
                    Player chosen = head;
                    for (int i = 1; i < choice; i++) {
                        chosen = chosen.next;
                    }
                    name = chosen.name;
                    chips = chosen.chips;
                    newId = chosen.id;
                }
            } else {
                System.out.println("No saves detected! Starting new game");
                System.out.println("What is your name?");
                name = scanner.nextLine();
            }
        }

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
            game.play();
            if (player.chips > 0) {
                ArrayList<String> lines = new ArrayList<String>();
                if (saveFile.exists()) {
                    Scanner fileScanner = new Scanner(saveFile);
                    while (fileScanner.hasNextLine()) {
                        String line = fileScanner.nextLine();
                        if (line.isEmpty()) {
                            continue;
                        }
                        if (line.startsWith(player.id + ",")) {
                            lines.add(player.id + "," + player.name + "," + player.chips);
                        } else {
                            lines.add(line);
                        }
                    }
                    fileScanner.close();
                } else {
                    lines.add(player.id + "," + player.name + "," + player.chips);
                }
                FileWriter writer = new FileWriter("player.txt");
                for (String line : lines) {
                    writer.write(line + "\n");
                }
                writer.close();
                System.out.println("Play again? (y/n)");
                String input = scanner.nextLine();
                if (input.equals("n")) {
                    System.out.println("Goodbye! And remember, the house always wins... ");
                    playing = false;
                }
            }
        }

        if (player.chips <= 0) {
            System.out.println(player.name + ", leave here at once!");
            System.out.println("Your name has been struck from the record.");
            System.out.println("You may start anew or wallow in misery for eternity");

            ArrayList<String> lines = new ArrayList<String>();
            if (saveFile.exists()) {
                Scanner fileScanner = new Scanner(saveFile);
                while (fileScanner.hasNextLine()) {
                    String line = fileScanner.nextLine();
                    if (line.isEmpty()) {
                        continue;
                    }
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
    }
}