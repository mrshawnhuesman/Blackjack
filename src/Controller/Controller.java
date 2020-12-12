package Controller;

import Model.Card;
import Model.Dealer;
import Model.Player;
import Model.Shoe;
import View.View;
import View.TestView;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Description: Contains all game logic. Must interact with the user
 * through the View.View object and data through the Models
 * @author csc439team3
 * @version 1.0
 */
public class Controller {
    private View view;
    private Player player = new Player();
    private Dealer dealer = new Dealer();
    private Shoe shoe = new Shoe(5);
    private String winner = null;
    private static final Logger logger = Logger.getLogger(Controller.class.getName());


    /**
     * Description: Controller.Controller constructor which requires a view object to be passed
     * to interact with controller methods
     * @author csc439team3
     * @param view View.View object (ej. View.CLIView, View.TestView)
     * @version 1.0
     */
    public Controller(View view) {
        setLoggingConfig("Controller");
        logger.entering(getClass().getName(), "Controller.Controller constructor with view");
        this.view = view;
        logger.exiting(getClass().getName(), "Controller.Controller constructor with view");
    }

    public static void setLoggingConfig(String logFileName) {
        try {
            FileHandler fileHandler = new FileHandler(System.getProperty("user.dir") + "/src/logs/" + logFileName + ".log");
            SimpleFormatter simple = new SimpleFormatter();
            fileHandler.setFormatter(simple);

            logger.addHandler(fileHandler);
            logger.setUseParentHandlers(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Description: Start blackjack game. Begin by asking player to buy chips then make a bet. Afterwards,
     * deal then show players' and dealers' card. Then ask the player for their move (hit, stand, or double)
     * and handle their move accordingly. Finally, decide the winner.
     * @author csc439team3
     * @version 1.0
     */
    public void playBlackjack() {
        logger.entering(getClass().getName(), "playBlackjack");
        // Check for cut card. If shoe becomes < 1/5 full, refill with new decks.
        // Because shoe is hard coded with 5 decks, a full shoe contains 260 cards. Once the shoe reaches
        // less than 52 cards (1/5 of the original size), refill the deck with 4 decks to reach a full 5 decks
        // worth of cards
        if(shoe.size() < 52){
            logger.info("shoe size less than 52, filling up shoe to contain 5 decks");
            shoe.addDecks(4);
        }

        try {
            // Step 1: Prompt player to buy chips
            logger.info("Prompting user to buy chips");
            int chipsBought = view.askPlayerToBuyChips();
            player.addChips(chipsBought);

            // Step 2: Prompt player to make a bet
            logger.info("Promping user to make bet");
            int playerBet = view.askPlayerToMakeBet(player);
            player.setPlayerBet(playerBet);
            player.decrementChips(playerBet);

            // Step 3: Deal initial two cards to player and dealer, show cards output in console
            logger.info("Dealing and showing cards");
            dealCards();
            showCards();

            // Step 4: Ask player to either hit, stand, or double
            logger.info("Asking player to hit, stand, or double");
            Character move = view.askPlayerToMakeMove(player);

            // Step 5: Handle user move (hit, stand, or double)
            logger.info("Handling user move");
            handleUserMove(player, move);

            // Step 6: Decide winner
            logger.info("Deciding winner");
            decideWinner(player, dealer);

            // Step 7: Start Another Round (if not testing)
            logger.info("Starting another blackjack round if not testing");
            if (!(view instanceof TestView)) {
                playBlackjack();
            }
        }
        catch (IOException ex){
            logger.warning("IOException error in playBlackjack");
            System.exit(0);
        }

        logger.exiting(getClass().getName(), "playBlackjack");
    }

    /**
     * Description: Create a Model.Shoe with 2 decks and then deal 2 cards to player then 2 cards to dealer
     * @author Babita Thapa
     * @version 1.0
     */
    public void dealCards(){
        logger.entering(getClass().getName(), "dealCards");
        player.getHand().addCard(shoe.pick());
        player.getHand().addCard(shoe.pick());
        dealer.getHand().addCard(shoe.pick());
        dealer.getHand().addCard(shoe.pick());
        logger.exiting(getClass().getName(), "dealCards");
    }

    /**
     * Description: Show the player's hand and then the dealer's hand. Ensure the dealer's first card
     * is not seen. This is the 'hole card'
     * @author Babita Thapa
     * @version 1.0
     */
    void showCards(){
        logger.entering(getClass().getName(), "showCards");
        System.out.println("Player's Hand: ");
        for (int i = 0; i < player.getHand().getCards().size(); i++){
            Card card = player.getHand().getCards().get(i);
            String suit = card.getSuit();
            String rank = card.getRank();
            System.out.print("| " + rank + " of "  + suit  + "| ");
            logger.info("player dealt card: " + "| " + rank + " of "  + suit  + "| ");
        }

        System.out.println("");

        System.out.println("Dealer's Hand: ");
        for (int i = 1; i < dealer.getHand().getCards().size(); i++) {
            Card card = dealer.getHand().getCards().get(i);
            String suit = card.getSuit();
            String rank = card.getRank();
            System.out.print("| " + rank + " of " + suit + "| ");
            logger.info("dealer dealt card: " + "| " + rank + " of "  + suit  + "| ");
        }
        logger.exiting(getClass().getName(), "showCards");
    }

    /**
     * Description: Performs logic using a player move ('h', 's', or 'd').
     * If the player doubles ('d') then the player's bet is doubled, the player draws a card, and then stands.
     * If the player hits ('h') then the player draws a card and is asked to make another move.
     * If the player stands ('s') then the player waits until the dealer's score is greater than or equal to 17.
     * If the player busts during a hit then the dealer is declared the winner
     * @author csc439team3
     * @param player current player
     * @param move player move in the form of 's', 'h', or 'd' as a Character object
     * @version 1.0
     */
    public void handleUserMove(Player player, Character move) {
        logger.entering(getClass().getName(), "handleUserMove");
        try {
            if (!(move.equals('d') || move.equals('s') || move.equals('h') || move.equals('q'))) {
                throw new IOException();
            }
            // Check if user doubled
            if (move.equals('d')) {
                logger.info("handling player double");
                // Double player bet
                player.setPlayerBet(player.getPlayerBet() * 2);
                logger.info("player's bet set to " + (player.getPlayerBet() * 2));
                // Deal one more card
                playerHit(player);
                // Stand (e.g. move = 's')
                move = 's';
                logger.info("player standing after double");
            }
            // or if they hit
            else if (move.equals('h')) {
                logger.info("handling player hit");
                // deal another card
                playerHit(player);

                if (player.getHand().score() > 21) {
                    winner = "dealer";
                    logger.info("player bust: dealer will win");
                } else {
                    // ask player to make another move
                    logger.info("player will be asked to make another move after hit");
                    view.askPlayerToMakeMove(player);
                }
            }

            // Check if move was or is now a stand. If so, deal cards to dealer once player stands
            if (move.equals('s')) {
                logger.info("handling player stand. dealer will now hit until score >= 17");
                while (dealer.getHand().score() < 17) {
                    dealerHit(dealer);
                }
            }
        } catch (IOException e) {
            logger.warning("Unexpected Behavior on Model.Player Move: " + move);
        }

        showCards();
        logger.entering(getClass().getName(), "handleUserMove");
    }

    /**
     * Description: Find out who won the Blackjack game and send the result to view.
     * If the winner is the player, give them chips in the amount of their bet * 2 and set their initial bet back to 0
     * @author csc439team3
     * @param player current player
     * @param dealer current dealer
     * @version 1.0
     */
    public void decideWinner(Player player, Dealer dealer) {
        logger.entering(getClass().getName(), "decideWinner");
        if (winner == null) {
            logger.info("calculating winner");
            if (dealer.getHand().score() > 21) {
                logger.info("winner is player due to dealer bust");
                winner = "player";
            }
            else if (player.getHand().score() > 21) {
                logger.info("winner is dealer due to player bust");
                winner = "dealer";
            }
            else {
                if (dealer.getHand().score() > player.getHand().score()) {
                    logger.info("winner is dealer due to higher score than player");
                    winner = "dealer";
                } else {
                    logger.info("winner is player due to higher score than dealer");
                    winner = "player";
                }
            }
        }

        if (winner.equals("player")) {
            player.addChips(player.getPlayerBet() * 2);
            logger.info("player received chip award for winning: " + (player.getPlayerBet() * 2));
            player.setPlayerBet(0);
            logger.info("player's bet reset to 0");
        }

        view.decideWinner(winner);

        player.getHand().getCards().clear();
        dealer.getHand().getCards().clear();

        logger.entering(getClass().getName(), "decideWinner");
    }


    /**
     * Description: Add a card to the player's hand
     * @author csc439team3
     * @param player current player
     * @version 1.0
     */
    public void playerHit(Player player) {
        logger.entering(getClass().getName(), "playerHit");
        player.getHand().addCard(shoe.pick());
        logger.exiting(getClass().getName(), "playerHit");
    }

    /**
     * Description: Add a card to the dealer's hand
     * @param dealer current dealer
     * @version 1.0
     */
    public void dealerHit(Dealer dealer) {
        logger.entering(getClass().getName(), "dealerHit");
        dealer.getHand().addCard(shoe.pick());
        logger.exiting(getClass().getName(), "dealerHit");
    }

    /**
     * Description: Get the player that is currently being used in the Controller.Controller
     * @author Babita Thapa
     * @version 1.0
     * @return player object
     */
    public Player getPlayer() {
        logger.entering(getClass().getName(), "getPlayer");
        logger.exiting(getClass().getName(), "getPlayer");
        return player;
    }

    /**
     * Description: Get the dealer that is currently being used in the Controller.Controller
     * @author Babita Thapa
     * @version 1.0
     * @return dealer object
     */
    public Dealer getDealer() {
        logger.entering(getClass().getName(), "getDealer");
        logger.exiting(getClass().getName(), "getDealer");
        return dealer;
    }

    /**
     * Description: Get the shoe that is currently being used in the Controller.Controller
     * @author shuesman
     * @version 1.0
     * @return shoe object
     */
    public Shoe getShoe() {
        logger.entering(getClass().getName(), "getShoe");
        logger.exiting(getClass().getName(), "getShoe");
        return shoe;
    }

    /**
     * Description: Get the winner
     * @author shuesman
     * @version 1.0
     * @return string name of winner (dealer or player)
     */
    public String getWinner() {
        return this.winner;
    }

}
