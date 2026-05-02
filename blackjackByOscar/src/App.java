import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner (System.in);

        File saveFile = new File("player.txt");
        String name = "";
        int chips = 500;
        if (saveFile.exists() && saveFile.length() > 0) {
            Scanner fileScanner = new Scanner(saveFile);
            String line = fileScanner.nextLine();
            String[] parts = line.split(",");
            name = parts[0];
            chips = Integer.parseInt(parts[1]);
            fileScanner.close();
        } else {
            System.out.println("What is your name?");
            name = scanner.nextLine();
        }
        Deck deck = new Deck();
        Player player = new Player(name, new Hand(), chips);
        Player dealer = new Player("Dealer", new Hand(), 0);
        Game game = new Game(player, dealer, deck);
        boolean playing = true;
        
        while (playing && player.chips > 0) {
            deck.buildDeck();
            deck.shuffle();
            player.hand.hand.clear();
            dealer.hand.hand.clear();
            game.placeBet();
            game.deal();
            game.play();
            FileWriter writer = new FileWriter("player.txt");
            writer.write(player.name + "," + player.chips);
            writer.close();
            System.out.println("Play again? (y/n)");
            String input = scanner.next();
            if (input.equals("n")){
                System.out.println("Goodbye! And remember, the house always wins... ");
                playing = false;
            }
        }
    }
}