package View;

import Controller.Controller;
import Model.Player;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Description: Interaction with the user on the command line
 * @author csc439team3
 * @version 1.0
 */

public class CLIView extends View {

    private static final Logger logger = Logger.getLogger(Controller.class.getName());
    /**
     * Description: Create new View.CLIView object
     * @author Shawn Huesman
     * @version 1.0
     */
    public CLIView() {
        Controller.setLoggingConfig("View.CLIView");
        logger.entering(getClass().getName(), "View.CLIView default constructor");
        logger.exiting(getClass().getName(), "View.CLIView default constructor");
    }

    /**
     * Description: Prompt player to purchase an amount of chips from 0 chips to 5000 chips
     * @author Shawn Huesman
     * @version 1.0
     * @return amount of chips purchased
     */
    public int askPlayerToBuyChips() throws IOException{
        logger.entering(getClass().getName(), "askPlayerToBuyChips");

        // Prompt user to buy chips
        System.out.println("How much would you like to spend on chips? $1 = 1 chip");

        // Read in user input
        Scanner userInput = new Scanner(System.in);
        String chipAmount = userInput.nextLine();
        logger.info("Model.Player purchased " + chipAmount + " chips");

        // Check if game has been terminated
        if (chipAmount.equalsIgnoreCase("Quit")){
            logger.info("Model.Player quit");
            throw new IOException();
        }

        // Check that bet is valid (between 0-5000)
        if (Integer.parseInt(chipAmount) > 5000 || Integer.parseInt(chipAmount) < 0) {
            logger.info("Model.Player bet invalid number of chips: " + chipAmount);
            System.out.println("Not a valid amount for chips. Allowed 0-5000");
            askPlayerToBuyChips();
        }

        logger.exiting(getClass().getName(), "askPlayerToBuyChips");
        return Integer.parseInt(chipAmount);
    }

    /**
     * Description: Prompt player to make a bet from 10 chips to 500 chips that is less than or equal to
     * player's total chips
     * @author Celine Wardrop
     * @version 1.0
     * @param player current player making bet
     * @retrun amount the player bet
     */
    public int askPlayerToMakeBet(Player player) throws IOException{
        logger.entering(getClass().getName(), "askPlayerToMakeBet");
        // Prompt user to make a bet
        System.out.println("How much would you like to bet? ");

        // Read user input
        Scanner userInput = new Scanner(System.in);
        String betAmount = userInput.nextLine();
        logger.info("Model.Player bet amount: " + betAmount);

        // Check if game has been terminated
        if (betAmount.equalsIgnoreCase("Quit")){
            logger.info("Model.Player quit");
            throw new IOException();
        }

        // Check that bet amount is valid (not less than 0)
        if (Integer.parseInt(betAmount) < 10){
            System.out.println("Sorry, that bet is too low. Try again.");
            logger.info("Model.Player too low bet amount");
            askPlayerToMakeBet(player);
        }

        // Check that bet amount is valid (not greater than 500)
        if (Integer.parseInt(betAmount) > 500){
            System.out.println("Sorry, that bet is too high. Try again.");
            logger.info("Model.Player too high bet amount");
            askPlayerToMakeBet(player);
        }

        if (Integer.parseInt(betAmount) > player.getChips()) {
            System.out.println("Sorry, you do not have enough chips to make that bet. Try again");
            logger.info("Model.Player doesnt have enough chips to make bet. Chip amount: " + player.getChips());
            askPlayerToMakeBet(player);
        }

        logger.exiting(getClass().getName(), "askPlayerToMakeBet");
        return Integer.parseInt(betAmount);
    }

    /**
     * Description: Ask player to move. Allow them to double if they have 2 cards, their score is between 9 and 11 and
     * they have sufficient chips to make the double (double amount of current bet). Re-prompt user to move if they
     * make an invalid move.
     * @author csc439team3
     * @param player current player
     * @return player's move ('h', 'd', or 's')
     * @version 1.0
     */
    public char askPlayerToMakeMove(Player player) throws IOException {
        logger.entering(getClass().getName(), "askPlayerToMakeMove");
        boolean doubleEligible = false;

        if (player.getHand().size() == 2 && 9 <= player.getHand().score() &&
            player.getHand().score() <= 11 && player.getChips() >= player.getPlayerBet()) {
            doubleEligible = true;
            logger.info("Model.Player is double eligible with hand size: " + player.getHand().size() +
                    " and score: " + player.getHand().score());
            System.out.println("What is your next move? h for hit, s for stand, d for double: ");
        }
        else {
            logger.info("Model.Player is not double eligible with hand size: " + player.getHand().size() +
                    " and score: " + player.getHand().score());
            System.out.println("What is your next move? h for hit, s for stand");
        }

        Scanner userInput = new Scanner(System.in);
        Character move = Character.toLowerCase(userInput.nextLine().charAt(0));
        logger.info("Model.Player chose move: " + move + " with input: " + userInput);

        // Assume that if user types anything beginning with 'q' that they are trying to type quit
        if (move.equals('q')){
            logger.info("Model.Player Quit");
            throw new IOException();
        }

        // If player inputs something besides hit, stand, double, or quit, re-prompt them to move
        if (!(move.equals('h') || move.equals('s') || move.equals('d') && doubleEligible)) {
            logger.info("Model.Player needs to make another move with invalid move: "
                    + move + " with double eligibility: " + doubleEligible);
            askPlayerToMakeMove(player);
        }

        logger.exiting(getClass().getName(), "askPlayerToMakeMove");
        return move;
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
            System.out.println("The winner is: " + winner);
        }
        else {
            logger.warning("Invalid winner");
            throw new IllegalArgumentException("Invalid winner");
        }

        logger.exiting(getClass().getName(), "decideWinner");
    }
}
