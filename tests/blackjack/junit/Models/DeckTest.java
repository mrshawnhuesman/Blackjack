package blackjack.junit.Models;

import Model.Card;
import Model.Deck;
import org.junit.Test;
import java.util.Arrays;
import static org.junit.Assert.*;

public class DeckTest {
    Deck deck = new Deck();

    @Test
    public void createDeck() {
        // Test first 13 cards for suit = "Spades" and number = 0-12
        for (int i = 0; i < 13; i++) {
            assertEquals(i, deck.getDeckCards().get(i).getNumber());
            assertEquals("Spades", deck.getDeckCards().get(i).getSuit());
        }

        // Test next 13 cards for suit = "Diamonds" and number = 0-12
        for (int i = 13; i < 26; i++) {
            assertEquals(i - 13, deck.getDeckCards().get(i).getNumber());
            assertEquals("Diamonds", deck.getDeckCards().get(i).getSuit());
        }

        // Test next 13 cards for suit = "Hearts" and number = 0-12
        for (int i = 26; i < 39; i++) {
            assertEquals(i - 26, deck.getDeckCards().get(i).getNumber());
            assertEquals("Hearts", deck.getDeckCards().get(i).getSuit());
        }

        // Test next 13 cards for suit = "Clubs" and number = 0-12
        for (int i = 39; i < 52; i++) {
            assertEquals(i - 39, deck.getDeckCards().get(i).getNumber());
            assertEquals("Clubs", deck.getDeckCards().get(i).getSuit());
        }
    }

    @Test
    public void getDeckCards() {
        assertEquals(52, deck.getDeckCards().size());
    }

    @Test
    public void pick() {
        int initialSize = deck.size();

        // test that card picked has valid suit and number
        Card card = deck.pick();

        boolean cardIsValidSuit = Arrays.asList("Hearts", "Spades", "Diamonds", "Clubs").contains(card.getSuit());
        assertTrue(cardIsValidSuit);

        boolean cardIsValidNumber = Arrays.asList(0,1,2,3,4,5,6,7,8,9,10,11,12).contains(card.getNumber());
        assertTrue(cardIsValidNumber);

        // test that size has decreased by one
        assertEquals(initialSize - 1, deck.size());

        // test that picked card has been removed from the deck (no card has the same suit and number)
        String cardSuit = card.getSuit();
        int cardNumber = card.getNumber();
        boolean valid = false;

        for(Card c : deck.getDeckCards()){
            if(c.getSuit().equals(card.getSuit()) && c.getNumber() == cardNumber){
                valid = false;
            }
            else{
                valid = true;
            }

            assertTrue(valid);
        }
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void pickAfterEmpty() {
        for (int i = 0; i < 54; i++) {
            deck.pick();
        }
    }

    @Test
    public void size() {
        for (int i = 0; i < 52; i++) {
            assertEquals(52 - i, deck.size(), 0);
            deck.pick();
        }
    }
}
