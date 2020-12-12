package View;

import Controller.Controller;
import Model.Player;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Description: Used to test MVC objects. These methods will not interact
 * with a user. This will create automated user responses for each test case
 * @author csc439team3
 * @version 1.0
 */
public class TestView extends View {
    private int chipAmount = 0;
    private int betAmount = 0;
    private char move;

    private static final Logger logger = Logger.getLogger(Controller.class.getName());

    /**
     * Description: Create a new View.TestView with a chip amount and bet amount
     * @author Shawn Huesman
     * @version 1.0
     * @param betAmount artificial player bet amount
     * @param chipAmount artificial player chip amount to add
     */
    public TestView(int chipAmount, int betAmount) {
        logger.entering(getClass().getName(), "View.TestView constructor with chipAmount, betAmount");
        this.chipAmount = chipAmount;
        this.betAmount = betAmount;
        logger.exiting(getClass().getName(), "View.TestView constructor with chipAmount, betAmount");
    }

    /**
     * Description: Create a new View.TestView with a chip amount, bet amount, and move
     * @author Shawn Huesman
     * @version 1.0
     * @param betAmount artificial player bet amount
     * @param chipAmount artificial player chip amount to add
     * @param move artificial move character ('h', 's', 'd', or 'q')
     */
    public TestView(int chipAmount, int betAmount, char move) {
        logger.entering(getClass().getName(), "View.TestView constructor with chipAmount, betAmount, move");
        this.chipAmount = chipAmount;
        this.betAmount = betAmount;
        this.move = move;
        logger.exiting(getClass().getName(), "View.TestView constructor with chipAmount, betAmount, move");
    }


    /**
     * Description: Make sure tested bet amount is between 0 and 5000
     * @author Shawn Huesman
     * @version 1.0
     * @return bet amount (to ensure controller compatibility)
     */
    public int askPlayerToBuyChips() {
        logger.entering(getClass().getName(), "askPlayerToBuyChips");

        // Check that bet is valid (between 0-5000)
        if (getChipAmount() > 5000 || getChipAmount() < 0) {
            logger.warning("invalid buy chip amount: " + getChipAmount());
            throw new IllegalArgumentException();
        }

        logger.exiting(getClass().getName(), "askPlayerToBuyChips");
        return getChipAmount();
    }

    /**
     * Description: Make sure artificial bet is between 10 and 500 and less than or equal to players
     * total chips
     * @author Shawn Huesman
     * @version 1.0
     */
    public int askPlayerToMakeBet(Player player){
        logger.entering(getClass().getName(), "askPlayerToMakeBet");

        // Check that bet amount is valid (not less than 0)
        if (getBetAmount() < 10){
            logger.info("bet amount too low: " + betAmount);
            throw new IllegalArgumentException();
        }

        // Check that bet amount is valid (not greater than 500)
        if (getBetAmount() > 500){
            logger.info("bet amount too high: " + betAmount);
            throw new IllegalArgumentException();
        }

        if (getBetAmount() > player.getChips()) {
            logger.info("bet amount too high : " + betAmount + " for player chip amount: " + chipAmount);
            throw new IllegalArgumentException();
        }

        logger.exiting(getClass().getName(), "askPlayerToMakeBet");
        return getBetAmount();
    }

    /**
     * Description: Ask player to make a valid blackjack move
     * @author csc439team3
     * @param player current player
     * @return move ('h', 'd', or 's')
     * @version 1.0
     */
    @Override
    public char askPlayerToMakeMove(Player player) throws IOException {
        logger.entering(getClass().getName(), "askPlayerToMakeMove");
        boolean doubleEligible = false;

        if (player.getHand().size() == 2 && 9 <= player.getHand().score() &&
                player.getHand().score() <= 11 && player.getChips() >= player.getPlayerBet()) {
            logger.info("Model.Player is double eligible");
            doubleEligible = true;
        }

        Character move = Character.toLowerCase(getMove());
        logger.info("Model.Player making move: " + move);

        // Assume that if user types anything beginning with 'q' that they are trying to type quit
        if (move.equals('q')){
            logger.info("Model.Player Quit");
            throw new IOException();
        }

        // If player inputs something besides hit, stand, double, or quit, re-prompt them to move
        if (!(move.equals('h') || move.equals('s') || move.equals('d') && doubleEligible)) {
            logger.info("Model.Player made invalid move, asking to make another move");
            askPlayerToMakeMove(player);
        }

        logger.exiting(getClass().getName(), "askPlayerToMakeMove");
        return move;
    }

    /**
     * Description: Get the bet amount from the player that he/she currently wants to bet
     * @author Celine Wardrop
     * @version 1.0
     * @return bet amount that the player bets (it should be between $10- $500)
     */
    public int getBetAmount() {
        logger.entering(getClass().getName(), "getBetAmount");
        logger.exiting(getClass().getName(), "getBetAmount");
        return this.betAmount;
    }

    /**
     * Description: Get the chip amount from the player that is currently being used
     * @author Shawn Huesman
     * @version 1.0
     * @return chip amount that the player wants to spend(it should be between 0-5000)
     */
    public int getChipAmount() {
        logger.entering(getClass().getName(), "getChipAmount");
        logger.exiting(getClass().getName(), "getChipAmount");
        return this.chipAmount;
    }

    /**
     * Description: Print out the winner of the game to the console
     * @author csc439team3
     * @param winner Blackjack winner ("player" or "dealer")
     * @version 1.0
     */
    public void decideWinner(String winner){
        logger.entering(getClass().getName(), "decideWinner");
        if (winner.equals("player") || winner.equals("dealer")) {
            System.out.print("The winner is: " + winner);
        }
        else {
            throw new IllegalArgumentException("Invalid winner");
        }
        logger.exiting(getClass().getName(), "decideWinner");
    }

    /**
     * Description: Get the current move
     * @author Shawn Huesman
     * @return current move valid: ('h','s','d','q')
     * @version 1.0
     */
    public char getMove() {
        logger.entering(getClass().getName(), "getMove");
        logger.exiting(getClass().getName(), "getMove");
        return move;
    }

}
