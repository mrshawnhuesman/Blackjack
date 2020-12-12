package Model;

import java.util.ArrayList;

/**
 * Description: Model.Deck for a 52 set of cards (13 cards for each of 4 suits: diamonds, hearts, clubs, spades)
 * @author shuesman
 * @version 1.0
 */
public class Deck {

    private ArrayList<Card> deckCards = new ArrayList<>();

    /**
     * Description: Model.Deck constructor which crates a new deckCards
     * @author shuesman
     * @version 1.0
     */
    public Deck() {
        createDeck();
    }

    /**
     * Description: invoked by Model.Deck constructor to create 52-card deck
     * @author shuesman
     * @version 1.0
     */
    private void createDeck() {
        String[] suits = {"Spades", "Diamonds", "Hearts", "Clubs"};
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                deckCards.add(new Card(j, suits[i]));
            }
        }
    }

    /**
     * Description: getter for deck of cards
     * @author shuesman
     * @return deck of cards
     * @version 1.0
     */
    public ArrayList<Card> getDeckCards() {
        return deckCards;
    }

    /**
     * Description: picks a random card from deck and reduces size of deck by 1
     * @author shuesman
     * @return random picked card
     * @version 1.0
     */
    public Card pick() {
        if (size() == 0) {
            throw new IndexOutOfBoundsException();
        }

        int rand = (int) (Math.random() * size());
        return deckCards.remove(rand);
    }

    /**
     * Description: getter for size of deck
     * @author shuesman
     * @return number of cards in deck
     * @version 1.0
     */
    public int size() {
        return deckCards.size();
    }
}
