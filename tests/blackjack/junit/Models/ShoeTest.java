package blackjack.junit.Models;

import Model.Card;
import Model.Deck;
import Model.Shoe;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class ShoeTest {

    Shoe shoe = new Shoe(3);

    @Test(expected = IllegalArgumentException.class)
    public void testSetShoe(){
        Shoe shoe2 = new Shoe(-1);
    }

    @Test
    public void getShoe() {

    }

    @Test
    public void pick() {
        // check that deck size in shoe is 52
        assertEquals(52, shoe.getShoe().get(0).size());
        assertEquals(52, shoe.getShoe().get(1).size());
        assertEquals(52, shoe.getShoe().get(2).size());

        // pick a card, check if that card is found in two decks (out of the 3 in shoe) and not found in one
        Card pickedCard = shoe.pick();
        boolean foundPickedInDeck1 = false, foundPickedInDeck2 = false, foundPickedInDeck3 = false;

        for (int i = 0; i < shoe.getShoe().size(); i++) {
            Deck d = shoe.getShoe().get(i);
            for (Card c : d.getDeckCards()) {
                if (c.getNumber() == pickedCard.getNumber() && c.getSuit().equals(pickedCard.getSuit())) {
                    if (i == 0) {
                        foundPickedInDeck1 = true;
                    }
                    else if (i == 1) {
                        foundPickedInDeck2 = true;
                    }
                    else {
                        foundPickedInDeck3 = true;
                    }
                }
            }
        }

        boolean cardPickedFoundMissing = (!foundPickedInDeck1 && foundPickedInDeck2 && foundPickedInDeck3) ||
                (foundPickedInDeck1 && !foundPickedInDeck2 && foundPickedInDeck3) ||
                (foundPickedInDeck1 && foundPickedInDeck2 && !foundPickedInDeck3);

        assertTrue(cardPickedFoundMissing);

        // check that a deck is removed when empty and that the card picked is valid
        for(int i = 0; i < 155; i++){
            Card card = shoe.pick();
            boolean cardIsValidSuit = Arrays.asList("Hearts", "Spades", "Diamonds", "Clubs").contains(card.getSuit());
            assertTrue(cardIsValidSuit);

            boolean cardIsValidNumber = Arrays.asList(0,1,2,3,4,5,6,7,8,9,10,11,12).contains(card.getNumber());
            assertTrue(cardIsValidNumber);
        }

        assertEquals(0, shoe.numDecks());

    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void pickOnNoDecks() {
        Shoe shoe2 = new Shoe(0);
        shoe2.pick();
    }

    @Test
    public void numDecks() {
        assertEquals(3, shoe.numDecks());

        shoe.getShoe().remove(0);
        assertEquals(2, shoe.numDecks());

        shoe.getShoe().clear();
        assertEquals(0, shoe.numDecks());
    }

    @Test
    public void size() {
        assertEquals(156, shoe.size());

        for(int i = 0; i < 156; i++) {
            assertEquals(156 - i, shoe.size());
            shoe.pick();
        }

        assertEquals(0, shoe.size());
    }

    @Test
    public void addDecks() {
        assertEquals(3, shoe.numDecks());

        shoe.addDecks(1);
        assertEquals(4, shoe.numDecks());

        shoe.addDecks(2);
        assertEquals(6, shoe.numDecks());

        shoe.addDecks(3);
        assertEquals(9, shoe.numDecks());

        shoe.addDecks(10);
        assertEquals(19, shoe.numDecks());
    }
}