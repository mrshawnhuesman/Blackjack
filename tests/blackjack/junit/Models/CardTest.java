package blackjack.junit.Models;

import Model.Card;
import org.junit.Test;

import static org.junit.Assert.*;

public class CardTest {

    Card card1 = new Card(0, "Spades");
    Card card2 = new Card();

    @Test
    public void getNumber() {
        assertEquals(0, card1.getNumber(), 0);
    }

    @Test
    public void getSuit() {
        assertEquals("Spades", card1.getSuit());
    }

    @Test
    public void setNumber() {
        card2.setNumber(2);
        assertEquals(2, card2.getNumber(),0);
    }

    @Test (expected = IllegalArgumentException.class)
    public void setNumberExceptionLowerBound() {
        card2.setNumber(-1);
    }

    @Test (expected = IllegalArgumentException.class)
    public void setNumberExceptionUpperBound() {
        card2.setNumber(13);
    }

    @Test
    public void setSuit() {
        Card card3 = new Card(0,"Spades");
        assertEquals("Spades", card3.getSuit());

        Card card4 = new Card(0,"Clubs");
        assertEquals("Clubs", card4.getSuit());

        Card card5 = new Card(0, "Diamonds");
        assertEquals("Diamonds", card5.getSuit());

        Card card6 = new Card(0, "Hearts");
        assertEquals("Hearts", card6.getSuit());
    }

    @Test (expected = IllegalArgumentException.class)
    public void setSuitException() {
        card2.setSuit("NotASuit");
    }

    @Test
    public void getRank() {
        assertEquals("ACE", card1.getRank());
    }
}