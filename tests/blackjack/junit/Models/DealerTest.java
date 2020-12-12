package blackjack.junit.Models;

import Model.Card;
import Model.Dealer;
import org.junit.Test;

import static org.junit.Assert.*;

public class DealerTest {

    Dealer dealer = new Dealer();

    @Test
    public void getHand() {
        Card card1 = new Card(2,"Spades");
        dealer.getHand().addCard(card1);

        for(int i = 0; i < dealer.getHand().size(); i++) {
            assertEquals(card1, dealer.getHand().getCards().get(i));
        }
    }

}