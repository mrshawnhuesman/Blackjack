package Model;

/**
 * Description: Blackjack dealer which holds a hand of cards
 * @author csc439team3
 * @version 1.0
 */
public class Dealer {
    private Hand hand = new Hand();

    /**
     * Description: Model.Dealer constructor to make a Model.Dealer object with no cards
     * @author Shawn Huesman
     * @version 1.0
     */
    public Dealer() {

    }

    /**
     * Description: Get the current hand of the dealer
     * @author Babita Thapa
     * @version 1.0
     * @return Arraylist of players hand
     */
    public Hand getHand(){
        return this.hand;
    }
}
