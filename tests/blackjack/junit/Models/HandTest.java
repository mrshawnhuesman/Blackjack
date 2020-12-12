package blackjack.junit.Models;

import Model.Card;
import Model.Hand;
import org.junit.Test;
import java.util.Arrays;
import static org.junit.Assert.*;

public class HandTest {

    Hand hand = new Hand();

    @Test
    public void addCard() {
        hand.addCard(new Card(2, "Spades"));
        assertEquals("Spades", hand.getCards().get(0).getSuit());
        assertEquals(2, hand.getCards().get(0).getNumber());
    }

    @Test (expected = IllegalArgumentException.class)
    public void addInvalidCard(){
        hand.addCard(new Card(-1, "Clover"));
    }

    @Test
    public void getCards() {
        hand.addCard(new Card(3, "Diamonds"));
        hand.addCard(new Card(5, "Hearts"));
        hand.addCard(new Card(8, "Spades"));
        hand.addCard(new Card(10, "Clubs"));

        for (Card card : hand.getCards()) {
            boolean cardIsValidSuit = Arrays.asList("Hearts", "Spades", "Diamonds", "Clubs").contains(card.getSuit());
            assertTrue(cardIsValidSuit);

            boolean cardIsValidNumber = Arrays.asList(0,1,2,3,4,5,6,7,8,9,10,11,12).contains(card.getNumber());
            assertTrue(cardIsValidNumber);
        }
    }

    @Test
    public void size() {
        assertEquals(0, hand.size());

        hand.addCard(new Card(3, "Diamonds"));
        hand.addCard(new Card(5, "Hearts"));
        hand.addCard(new Card(8, "Spades"));
        hand.addCard(new Card(10, "Clubs"));

        assertEquals(4, hand.size());
    }

    @Test
    public void scoreFaceCards() {
        assertEquals(0, hand.score());

        hand.addCard(new Card(1, "Spades"));
        assertEquals(2, hand.score());

        hand.addCard(new Card(2, "Spades"));
        assertEquals(5, hand.score());

        hand.addCard(new Card(3, "Spades"));
        assertEquals(9, hand.score());

        hand.addCard(new Card(4, "Spades"));
        assertEquals(14, hand.score());

        hand.addCard(new Card(5, "Spades"));
        assertEquals(20, hand.score());

        hand.addCard(new Card(6, "Spades"));
        assertEquals(27, hand.score());

        hand.addCard(new Card(7, "Spades"));
        assertEquals(35, hand.score());

        hand.addCard(new Card(8, "Spades"));
        assertEquals(44, hand.score());

        hand.addCard(new Card(9, "Spades"));
        assertEquals(54, hand.score());
    }

    @Test
    public void scoreHighCards() {
        assertEquals(0, hand.score());

        hand.addCard(new Card(10, "Spades")); // jack
        assertEquals(10, hand.score());

        hand.addCard(new Card(11, "Spades")); // queen
        assertEquals(20, hand.score());

        hand.addCard(new Card(12, "Spades")); // king
        assertEquals(30, hand.score());
    }

    @Test
    public void scoreAces() {
        assertEquals(0, hand.score());

        hand.addCard(new Card(0, "Clubs"));
        assertEquals(11, hand.score());

        hand.addCard(new Card(12, "Diamonds"));
        assertEquals(21, hand.score());

        hand.addCard(new Card(0, "Hearts"));
        assertEquals(12, hand.score());

        hand.addCard(new Card(0, "Hearts"));
        assertEquals(13, hand.score());

        hand.addCard(new Card(0, "Hearts"));
        assertEquals(14, hand.score());

        hand.addCard(new Card(0, "Hearts"));
        assertEquals(15, hand.score());

        hand.addCard(new Card(0, "Hearts"));
        assertEquals(16, hand.score());

        hand.addCard(new Card(0, "Hearts"));
        assertEquals(17, hand.score());

        hand.addCard(new Card(0, "Hearts"));
        assertEquals(18, hand.score());

        hand.addCard(new Card(0, "Hearts"));
        assertEquals(19, hand.score());

        hand.addCard(new Card(0, "Hearts"));
        assertEquals(20, hand.score());

        hand.addCard(new Card(0, "Hearts"));
        assertEquals(21, hand.score());

        hand.addCard(new Card(10, "Diamonds"));
        assertEquals(31, hand.score());
    }
}
