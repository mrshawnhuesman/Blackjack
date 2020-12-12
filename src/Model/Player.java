package Model;

/**
 * Description: Blackjack Model.Player which holds a hand of cards and a number of chips
 * @author csc439team3
 * @version 1.0
 */
public class Player {
    private int chips;
    private Hand hand = new Hand();
    private int playerBet;

    /**
     * Description: Model.Player constuctor which makes a player object with 0 chips and no cards
     * @author Shawn Huesman
     * @version 1.0
     */
    public Player() {

    }

    /**
     * Description: Add a number of chips to player's total chip count
     * @author Shawn Huesman
     * @param amount number of chips to be added
     * @version 1.0
     */
    public void addChips(int amount) {
        this.chips = getChips() + amount;
    }

    /**
     * Description: Get the number of chips the player currently has
     * @author Babita Thapa
     * @version 1.0
     * @return current chip amount
     */
    public int getChips() {
        return this.chips;
    }

    /**
     * Description: Remove a number of chips off player's total chip amount
     * @author Celine Wardrop
     * @param amount number of chips to be removed
     * @version 1.0
     */
    public void decrementChips(int amount){
        if (amount > getChips()) {
            throw new IllegalArgumentException();
        }
        else {
            this.chips = getChips() - amount;
        }
    }

    /**
     * Description: Get the players current cards
     * @author Babita Thapa
     * @version 1.0
     * @return Arraylist of current cards
     */
    public Hand getHand(){
        return this.hand;

    }

    /**
     * Description: Set the current bet of the player. Useful for doubling player bet amount when player doubles
     * @author shuesman
     * @param playerBet amount to set bet to
     * @version 1.0
     */
    public void setPlayerBet(int playerBet) {
        this.playerBet = playerBet;
    }

    /**
     * Description: Get the current bet of the player
     * @author shuesman
     * @return player's current bet amount
     * @version 1.0
     */
    public int getPlayerBet() {
        return this.playerBet;
    }
}
