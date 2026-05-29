/*
 * Handles the rules and outcome logic for a round of blackjack.
 * Kept separate from Game to give winner determination its own responsibility,
 * making it easier to extend (e.g. blackjack bonus, side bets).
 */
public class Rules {

    /*
     * Determines the winner of a round by comparing the player's and dealer's hands.
     * Outcomes in order of priority:
     *   1. Player bust — dealer wins, player loses their bet
     *   2. Dealer bust — player wins, receives double their bet back
     *   3. Player total higher — player wins, receives double their bet back
     *   4. Dealer total higher — dealer wins, player loses their bet
     *   5. Equal totals — draw, player receives their bet back
     */
    public static void determineWinner(Player player, Player dealer) {

        int bet = player.bet;

        if (player.hand.isBust()) {
            System.out.println("Dealer wins!");

        } else if (dealer.hand.isBust()) {
            System.out.println("You win!");
            player.chips += bet * 2;

        } else if (player.hand.getTotal() > dealer.hand.getTotal()) {
            System.out.println("You win!");
            player.chips += bet * 2;

        } else if (player.hand.getTotal() < dealer.hand.getTotal()) {
            System.out.println("Dealer wins!");

        } else {
            System.out.println("Draw!");
            player.chips += bet; // return bet on draw
        }

        System.out.println("Current chip balance: " + player.chips);
    }
}