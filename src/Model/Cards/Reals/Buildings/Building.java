package Model.Cards.Reals.Buildings;

import Model.Cards.Reals.Real;
import Model.Cards.Reals.Type;
import Model.Interfaces.GroundWarrior;
import Model.Property;

/**
 * the building cards of the game
 * @version 1.0.0
 * @since 7.8.2021
 */
public abstract class Building extends Real implements Property , GroundWarrior {
    private int lifeTime;

    /**
     * constructor for the building
     * @param cost is the cost of the card
     * @param cardImageAddress is the card image of the card
     * @param imageAddress is the image of the card
     * @param health is the health of the card
     * @param damage is the damage of the card
     * @param range is the range of the card
     * @param hitSpeed is the hit speed of the card
     * @param myType is the type of the card
     * @param targetType is the type of the targets of the card
     * @param lifeTime is the life time of the building
     */
    public Building(int cost, String cardImageAddress, String imageAddress,
                    int health, int damage, double range,
                    double hitSpeed, Type myType,
                    Type targetType , int lifeTime) {
        super(cost, cardImageAddress, imageAddress, health, damage, range, hitSpeed, myType, targetType);
        this.lifeTime = lifeTime;
    }

    public int getLifeTime() {
        return lifeTime;
    }
}
