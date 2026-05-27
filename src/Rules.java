public class Rules {

    public static void determineWinner(Player player, Player dealer) {

        int bet = player.bet;

        if (player.hand.isBust()) {
            System.out.println("Dealer wins!");
        }

        else if (dealer.hand.isBust()) {
            System.out.println("You win!");
            player.chips += bet * 2;
        }

        else if (player.hand.getTotal() > dealer.hand.getTotal()) {
            System.out.println("You win!");
            player.chips += bet * 2;
        }

        else if (player.hand.getTotal() < dealer.hand.getTotal()) {
            System.out.println("Dealer wins!");
        }

        else {
            System.out.println("Draw!");
            player.chips += bet;
        }

        System.out.println("Current chip balance: " + player.chips);
    }
}