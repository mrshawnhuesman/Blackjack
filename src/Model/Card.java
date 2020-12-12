package Model;

/**
 * Description: The card class with methods for getting and setting a playing card.
 * @author cwardrop
 * @version 1.0
 */
public class Card {

    private int number;
    private String suit;

    /**
     * Description: The constructor for the card class.
     * @author cwardrop
     * @param number the number of the playing card (0 - 12)
     * @param suit the suit of the playing card (hearts, diamonds, clubs, spades)
     * @version 1.0
     */
    public Card(int number, String suit){
        setNumber(number);
        setSuit(suit);
    }
    /**
     * Description: The constructor for the card class.
     * @author cwardrop
     * @version 1.0
     */
    public Card(){
    }

    /**
     * Description: The get method for a card number. Checks to make sure number is between 0 - 12
     * @author cwardrop
     * @return the number of the card
     * @version 1.0
     */
    public int getNumber(){
        return number;
    }

    /**
     * Description: The get method for a card suit. Checks to make sure suit is valid (one of the four suits)
     * @author cwardrop
     * @return the suit of the card
     * @version 1.0
     */
    public String getSuit(){
        return suit;
    }

    /**
     * Description: The set method for a card number.
     * @author cwardrop
     * @param cardNum the number of the card to be created
     * @version 1.0
     */
    public void setNumber(int cardNum){
        if (cardNum >= 0 && cardNum <= 12) {
            this.number = cardNum;
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Description: The set method for a card suit.
     * @author cwardrop
     * @param cardSuit the suit of the card to be created
     * @version 1.0
     */
    public void setSuit(String cardSuit) {
        if (cardSuit.equals("Spades") || cardSuit.equals("Clubs")
             || cardSuit.equals("Diamonds") || cardSuit.equals("Hearts")) {
            this.suit = cardSuit;
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Description: The get card rank method which returns a string representation of the card's number
     * @author cwardrop, shawn huesman
     * @return the rank of the card (string representation of the card number)
     * @version 1.0
     */
    public String getRank(){

        String[] ranks = {"ACE", "TWO", "THREE", "FOUR", "FIVE",
        "SIX", "SEVEN", "EIGHT", "NINE", "TEN", "JACK", "QUEEN", "KING"};

        return ranks[number];

    }
}
