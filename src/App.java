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