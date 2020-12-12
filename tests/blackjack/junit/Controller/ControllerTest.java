package blackjack.junit.Controller;

import Model.Card;
import Controller.Controller;
import View.TestView;
import org.junit.Test;
import java.io.IOException;

import static org.junit.Assert.*;

public class ControllerTest {

    @Test
    public void playBlackjack() {

        TestView testView = new TestView(500, 30,'s');
        Controller controller = new Controller(testView);
        assertEquals(30, testView.getBetAmount());
        assertEquals(500, testView.getChipAmount());
        assertEquals('s', testView.getMove());
        assertEquals(5, controller.getShoe().numDecks());

        controller.playBlackjack();
    }

    @Test (expected = IllegalArgumentException.class)
    public void playBlackjackGreaterThanChipAmount() {
        TestView testView = new TestView(200, 500);
        Controller controller = new Controller(testView);
        controller.playBlackjack();
    }

    @Test (expected = IllegalArgumentException.class)
    public void playBlackjackBuyOver5000Chips() {
        TestView testView = new TestView(5001, 500);
        Controller controller = new Controller(testView);
        controller.playBlackjack();
    }

    @Test (expected = IllegalArgumentException.class)
    public void playBlackjackNegativeBet() {
        TestView testView = new TestView(200, -5);
        Controller controller = new Controller(testView);
        controller.playBlackjack();
    }

    @Test (expected = IllegalArgumentException.class)
    public void playBlackjackBetGreaterThan500() {
        TestView testView = new TestView(200, 600);
        Controller controller = new Controller(testView);
        controller.playBlackjack();
    }

    @Test (expected = IllegalArgumentException.class)
    public void playBlackjackLessThan10() {
        TestView testView = new TestView(200, 2);
        Controller controller = new Controller(testView);
        controller.playBlackjack();
    }

    @Test
    public void dealCards() {
        TestView testView = new TestView(200, 30);
        Controller controller = new Controller(testView);
        controller.dealCards();

        assertEquals(2, controller.getPlayer().getHand().getCards().size());
        assertEquals(2, controller.getDealer().getHand().getCards().size());
    }

    @Test
    public void showCards() {
    }

    @Test (expected = IllegalArgumentException.class)
    public void invalidWinner() {
        TestView testView = new TestView(500, 50);
        testView.decideWinner("NotAPersonOrPlayer");
    }

    // illegal move will cause system to keep asking for player move resulting in stack overflow
    @Test (expected = StackOverflowError.class)
    public void invalidMove() throws IOException {
        TestView testView = new TestView(500,50, 'p');
        Controller controller = new Controller(testView);
        testView.askPlayerToMakeMove(controller.getPlayer());
    }

    // trying to double without being eligible will cause system to keep asking for player move
    // resulting in stack overflow
    @Test (expected = StackOverflowError.class)
    public void doubleAttemptWithoutBeingEligibleBecauseOfChipAmount() throws IOException {
        TestView testView = new TestView(99,50, 'd');
        Controller controller = new Controller(testView);
        testView.askPlayerToMakeMove(controller.getPlayer());
    }

    // trying to double without being eligible will cause system to keep asking for player move
    // resulting in stack overflow
    @Test (expected = StackOverflowError.class)
    public void doubleAttemptWithoutBeingEligibleBecauseOfTooManyCards() throws IOException {
        TestView testView = new TestView(300,50, 'd');
        Controller controller = new Controller(testView);
        controller.getPlayer().getHand().addCard(new Card(0, "Spades"));
        controller.getPlayer().getHand().addCard(new Card(0, "Diamonds"));
        controller.getPlayer().getHand().addCard(new Card(0, "Clubs"));
        testView.askPlayerToMakeMove(controller.getPlayer());
    }

    // trying to double without being eligible will cause system to keep asking for player move
    // resulting in stack overflow
    @Test (expected = StackOverflowError.class)
    public void doubleAttemptWithoutBeingEligibleBecauseOfHighScore() throws IOException {
        TestView testView = new TestView(99,50, 'd');
        Controller controller = new Controller(testView);
        controller.getPlayer().getHand().addCard(new Card (10, "Spades"));
        controller.getPlayer().getHand().addCard(new Card (10, "Diamonds"));
        testView.askPlayerToMakeMove(controller.getPlayer());
    }

    // trying to double without being eligible will cause system to keep asking for player move
    // resulting in stack overflow
    @Test (expected = StackOverflowError.class)
    public void doubleAttemptWithoutBeingEligibleBecauseOfLowScore() throws IOException {
        TestView testView = new TestView(99,50, 'd');
        Controller controller = new Controller(testView);
        controller.getPlayer().getHand().addCard(new Card (1, "Spades"));
        controller.getPlayer().getHand().addCard(new Card (1, "Diamonds"));
        testView.askPlayerToMakeMove(controller.getPlayer());
    }

    @Test (expected = IOException.class)
    public void quitMove() throws IOException {
        TestView testView = new TestView(500,50, 'q');
        Controller controller = new Controller(testView);
        testView.askPlayerToMakeMove(controller.getPlayer());
    }

    @Test
    public void smallShoeSize() {
        TestView testView = new TestView(500,50, 's');
        Controller controller = new Controller(testView);

        // 52 cards * 5 decks in shoe = 260 cards in shoe.
        // Need to have 52 or less cards in shoe left to refill
        for (int i = 0; i < 250; i++) {
            controller.getShoe().pick();
        }
        assertEquals(1, controller.getShoe().numDecks());
        assertEquals(10, controller.getShoe().size());

        controller.playBlackjack();

        assertEquals(5, controller.getShoe().numDecks());
    }

    @Test
    public void handleUserMoveDouble() {
        TestView testView = new TestView(500,50, 'd');
        assertEquals(500, testView.getChipAmount());
        assertEquals(50, testView.getBetAmount());

        Controller controller = new Controller(testView);
        controller.getPlayer().setPlayerBet(testView.getBetAmount());
        assertEquals(50, controller.getPlayer().getPlayerBet());
        controller.handleUserMove(controller.getPlayer(), 'd');

        assertEquals(100, controller.getPlayer().getPlayerBet());
    }

    @Test
    public void handleUserMoveHit() {
        TestView testView = new TestView(500,50, 's'); // stand after hit
        assertEquals(500, testView.getChipAmount());
        assertEquals(50, testView.getBetAmount());

        Controller controller = new Controller(testView);
        controller.getPlayer().setPlayerBet(testView.getBetAmount());
        assertEquals(50, controller.getPlayer().getPlayerBet());
        controller.handleUserMove(controller.getPlayer(), 'h');

        assertEquals(50, controller.getPlayer().getPlayerBet());
        assertEquals(1, controller.getPlayer().getHand().getCards().size());
    }

    @Test
    public void handleUserBadMove() {
        TestView testView = new TestView(500,50, 'l');
        assertEquals(500, testView.getChipAmount());
        assertEquals(50, testView.getBetAmount());

        Controller controller = new Controller(testView);
        controller.getPlayer().setPlayerBet(testView.getBetAmount());
        assertEquals(50, controller.getPlayer().getPlayerBet());
        controller.handleUserMove(controller.getPlayer(), 'l');
    }

    @Test
    public void decideWinnerDealerBust() {
        TestView testView = new TestView(500,50, 's');
        assertEquals(500, testView.getChipAmount());
        assertEquals(50, testView.getBetAmount());

        Controller controller = new Controller(testView);
        for(int i = 0; i <= 21; i++) {
            controller.dealerHit(controller.getDealer());
        }

        controller.decideWinner(controller.getPlayer(), controller.getDealer());
        assertEquals("player", controller.getWinner());
    }

    @Test
    public void decideWinnerPlayerBust() {
        TestView testView = new TestView(500,50, 's');
        assertEquals(500, testView.getChipAmount());
        assertEquals(50, testView.getBetAmount());

        Controller controller = new Controller(testView);
        for(int i = 0; i <= 21; i++) {
            controller.playerHit(controller.getPlayer());
        }

        controller.decideWinner(controller.getPlayer(), controller.getDealer());
        assertEquals("dealer", controller.getWinner());
    }

    @Test
    public void decideWinnerPlayerBetterScoreThanDealer() {
        TestView testView = new TestView(500,50, 's');
        assertEquals(500, testView.getChipAmount());
        assertEquals(50, testView.getBetAmount());

        Controller controller = new Controller(testView);
        // player blackjack
        controller.getPlayer().getHand().addCard(new Card(0, "Spades"));
        controller.getPlayer().getHand().addCard(new Card(10, "Spades"));
        System.out.println(controller.getPlayer().getHand().score());

        // dealer 18
        controller.getDealer().getHand().addCard(new Card(11, "Spades"));
        controller.getDealer().getHand().addCard(new Card(7, "Spades"));

        controller.decideWinner(controller.getPlayer(), controller.getDealer());
        assertEquals("player", controller.getWinner());
    }

    @Test
    public void decideWinnerDealerBetterScoreThanPlayer() {
        TestView testView = new TestView(500,50, 's');
        assertEquals(500, testView.getChipAmount());
        assertEquals(50, testView.getBetAmount());

        Controller controller = new Controller(testView);
        // dealer blackjack
        controller.getDealer().getHand().addCard(new Card(0, "Spades"));
        controller.getDealer().getHand().addCard(new Card(10, "Spades"));
        System.out.println(controller.getPlayer().getHand().score());

        // player 18
        controller.getPlayer().getHand().addCard(new Card(11, "Spades"));
        controller.getPlayer().getHand().addCard(new Card(7, "Spades"));

        controller.decideWinner(controller.getPlayer(), controller.getDealer());
        assertEquals("dealer", controller.getWinner());
    }

    @Test
    public void handleUserMovePlayerBust() {
        TestView testView = new TestView(500,50, 'h');
        assertEquals(500, testView.getChipAmount());
        assertEquals(50, testView.getBetAmount());

        Controller controller = new Controller(testView);
        controller.getPlayer().setPlayerBet(testView.getBetAmount());
        assertEquals(50, controller.getPlayer().getPlayerBet());

        // force player bust
        for(int i = 0; i <= 21; i++) {
            controller.playerHit(controller.getPlayer());
        }
        controller.handleUserMove(controller.getPlayer(), 'h');

        assertEquals("dealer", controller.getWinner());
    }

    @Test
    public void doubleAttemptWhileDoubleEligible() throws IOException {
        TestView testView = new TestView(200,50, 'd');
        Controller controller = new Controller(testView);
        controller.getPlayer().getHand().addCard(new Card(1, "Spades"));
        controller.getPlayer().getHand().addCard(new Card(8, "Spades"));
        assertEquals(2, controller.getPlayer().getHand().size());
        assertEquals(11, controller.getPlayer().getHand().score());
        testView.askPlayerToMakeMove(controller.getPlayer());
    }


}