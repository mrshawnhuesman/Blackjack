package View;

import Model.Player;

import java.io.IOException;

/**
 * Description: Interaction with the user
 * @author csc439team3
 * @version 1.0
 */
public abstract class View {

    public View() {
    }

    /**
     * Description: Prompt a player to buy a number of chips
     * @author csc439team3
     * @version 1.0
     * @return number of chips purchased
     */
    public abstract int askPlayerToBuyChips() throws IOException;

    /**
     * Description: Prompt player to make bet
     * @author Babita Thapa
     * @version 1.0
     * @param player player making bet
     * @return bet amount
     */
    public abstract int askPlayerToMakeBet(Player player) throws IOException;

    /**
     * Description: Ask player to make a valid blackjack move
     * @author csc439team3
     * @param player current player
     * @return move ('h', 'd', or 's')
     * @version 1.0
     */
    public abstract char askPlayerToMakeMove(Player player) throws IOException;

    /**
     * Description: Print out the winner of the game to the console
     * @author csc439team3
     * @param winner Blackjack winner ("player" or "dealer")
     * @version 1.0
     */
    public abstract void decideWinner(String winner);
}

