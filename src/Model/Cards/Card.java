package Model.Cards;

import Model.GameEntity;

public abstract class Card implements GameEntity {
    private int cost;
    private String cardImageAddress; // the image of the card
    private String imageAddress; // the image of the element in the game board

    public Card(int cost, String cardImageAddress, String imageAddress) {
        this.cost = cost;
        this.cardImageAddress = cardImageAddress;
        this.imageAddress = imageAddress;
    }


    public int getCost() {
        return cost;
    }

    public String getCardImageAddress() {
        return cardImageAddress;
    }

    public String getImageAddress() {
        return imageAddress;
    }
}
