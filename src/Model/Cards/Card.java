package Model.Cards;

import Model.GameEntity;

import java.io.Serializable;

/**
 * class for cards of the game
 * @version 1.0.0
 * @since 7.8.2021
 */
public abstract class Card implements GameEntity, Serializable {
    private int cost;
    private String cardImageAddress; // the image of the card
    private String imageAddress; // the image of the element in the game board

    /**
     * constructor for card
     * @param cost is the cost of the card
     * @param cardImageAddress is the address of the image of the card
     * @param imageAddress is the address of the image for showing the element in game
     */
    public Card(int cost, String cardImageAddress, String imageAddress) {
        this.cost = cost;
        this.cardImageAddress = cardImageAddress;
        this.imageAddress = imageAddress;
    }

    /**
     *
     * @return the cost of the card
     */
    public int getCost() {
        return cost;
    }

    /**
     *
     * @return the card image of the card
     */
    public String getCardImageAddress() {
        return cardImageAddress;
    }

    /**
     *
     * @return the image of the card
     */
    public String getImageAddress() {
        return imageAddress;
    }
}
