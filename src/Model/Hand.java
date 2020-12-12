package Model;

import java.util.ArrayList;

/**
 * Description: Model.Hand for player with current cards
 * @author bthapa
 * @version 1.0
 */
public class Hand {

    private ArrayList<Card> hand = new ArrayList<Card>();

    /**
     * Description: Constructor for hand object
     * @author bthapa
     * @version 1.0
     */
    public Hand() {
    }

    /**
     * Description: add a card to the hand
     * @author bthapa
     * @version 1.0
     */
    public void addCard(Card card) {
        hand.add(card);
    }

    /**
     * Description: Model.Hand for player with current cards
     * @author bthapa
     * @return array
     * @version 1.0
     */
    public ArrayList<Card> getCards() {
        return hand;
    }

    /**
     * Description: get amount of cards in hand
     * @author bthapa
     * @return amount of cards in hand
     * @version 1.0
     */
    public int size() {
        return hand.size();
    }

    /**
     * Description: Calculate the score of a hand. An Ace is worth 11 points unless it would cause the otherall hand
     * score to be over 21, which in that case it would be worth 1 point. Cards 2-10 are worth their face value. Jacks,
     * Queens, and Kings are worth 10 points
     * @author shuesman
     * @return score total score of the hand
     * @version 1.0
     */
    public int score() {
        int aceCount = 0;
        int score = 0;

        for (Card card : getCards()) {
            // Model.Card is an Ace
            if (card.getNumber() == 0) {
                aceCount++;
            }
            // Model.Card is Jack, Queen, or King
            else if (card.getNumber() == 10 || card.getNumber() == 11 ||
                    card.getNumber() == 12) {
                score = score + 10;
            }
            // Model.Card is 2-10
            else {
                score = score + card.getNumber() + 1;
            }
        }

        // Ace is worth 11 unless it would cause the score to be over 21. In that case, it would be worth 1 point
        if (aceCount > 0) {
            for (int i = 0; i < aceCount; i++) {
                score = score + 11;
            }
            for (int i = 0; i < aceCount; i++) {
                if (score > 21) {
                    score = score - 10;
                }
            }
        }

        return score;
    }
}
