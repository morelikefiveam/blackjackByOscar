import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        File saveFile = new File("player.txt");
        String name = "";
        int chips = 500;

        System.out.println("--- THE TOWER ---");
        System.out.println("1. New Game");
        System.out.println("2. Load Game");
        int decision = Integer.parseInt(scanner.nextLine().trim());
        if (decision == 1){
            System.out.println("What is your name?");
            name = scanner.nextLine();
        } else if (decision == 2){
            if (saveFile.exists() && saveFile.length() > 0){
            Scanner fileScanner = new Scanner(saveFile);
            String line = fileScanner.nextLine();
            String[] parts = line.split(",");
            name = parts[0];
            chips = Integer.parseInt(parts[1]);
            fileScanner.close();
            } else {
                System.out.println("No save file detected! Starting new game");
                System.out.println("What is your name?");
                name = scanner.nextLine();
            }
        }

        
        Deck deck = new Deck();
        Player player = new Player(name, new Hand(), chips);
        Player dealer = new Player("Dealer", new Hand(), 0);
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
                FileWriter writer = new FileWriter("player.txt");
                writer.write(player.name + "," + player.chips);
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
            FileWriter writer = new FileWriter("player.txt");
            writer.write("");
            writer.close();
        }
    }
}