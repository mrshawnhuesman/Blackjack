package Model;

import java.util.ArrayList;


/**
 * Description: Model.Shoe class which generates multiple decks of cards and add them to the shoe collection
 * @author cwardrop
 * @version 1.0
 */
public class Shoe {

    private ArrayList<Deck> shoe = new ArrayList<>();

    /**
     * Description: Constructor for the Model.Shoe class which generates multiple decks of cards
     *
     * @param decksInShoe the number of decks to add to the shoe
     * @author cwardrop
     * @version 1.0
     */
    public Shoe(int decksInShoe) {
        if (decksInShoe < 0) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < decksInShoe; i++) {
            Deck deck = new Deck();
            shoe.add(deck);
        }
    }

    /**
     * Description: getter method for the Model.Shoe class
     *
     * @return an array of Decks
     * @author cwardrop
     * @version 1.0
     */
    public ArrayList<Deck> getShoe() {
        return shoe;
    }

    /**
     * Description: method that chooses a deck at random and then picks a card from the deck using the deck's pick method.
     * Once a deck is empty, it should be removed from the shoe.
     *
     * @return a random card from a random deck in the shoe
     * @author cwardrop
     * @version 1.0
     */
    public Card pick() {

        if (numDecks() == 0) {
            throw new IndexOutOfBoundsException();
        }

        //pick random deck from shoe
        Deck randomDeck = shoe.get((int) Math.random() * numDecks());

        //pick a random card from that deck
        Card randomCard = randomDeck.pick();

        // if deck is now empty, remove it from the shoe
        if (randomDeck.size() == 0) {
            shoe.remove(randomDeck);
        }

        return randomCard;
    }

    /**
     * Description: method that returns the number of decks currently in the shoe
     *
     * @return the number of decks in the shoe
     * @author cwardrop
     * @version 1.0
     */
    public int numDecks() {
        return shoe.size();
    }

    /**
     * Description: method that returns the number of cards currently in the shoe.
     *
     * @return the number of cards in the shoe (number of decks * 52)
     * @author cwardrop
     * @version 1.0
     */
    public int size() {
        int accumulator = 0;

        for (Deck deck : shoe) {
            accumulator = accumulator + deck.size();
        }

        return accumulator;
    }

    /**
     * Description: adds numDecks number of decks to shoe
     * @param numDecks amount of decks to add to shoe
     * @author cwardrop
     * @version 1.0
     */
    public void addDecks(int numDecks) {
        for (int i = 0; i < numDecks; i++) {
            Deck deck = new Deck();
            shoe.add(deck);
        }
    }
}
