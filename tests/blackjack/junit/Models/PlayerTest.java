package blackjack.junit.Models;

import Model.Card;
import Model.Player;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {

    Player player = new Player();

    @Test
    public void getHand() {
        Card card1 = new Card(4,"Diamonds");
        player.getHand().addCard(card1);

        for(int i = 0; i < player.getHand().size(); i++) {
            assertEquals(card1, player.getHand().getCards().get(i));
        }
    }

    @Test
    public void getPlayerBet() {
        player.setPlayerBet(500);
        assertEquals(500, player.getPlayerBet());
    }

    @Test
    public void decrementChips() {
        player.addChips(500);
        assertEquals(500, player.getChips());
        player.decrementChips(500);
        assertEquals(0, player.getChips());
    }

    @Test (expected = IllegalArgumentException.class)
    public void decrementChipsNegativeValue() {
        player.addChips(500);
        assertEquals(500, player.getChips());
        player.decrementChips(5000);
    }

    @Test
    public void getChips() {
        player.addChips(50);
        assertEquals(50, player.getChips());
    }

    @Test
    public void addChips() {
        player.addChips(1);
        assertEquals(1, player.getChips());

        player.addChips(2);
        assertEquals(3, player.getChips());

        player.addChips(100);
        assertEquals(103, player.getChips());
    }

}