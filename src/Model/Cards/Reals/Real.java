package Model.Cards.Reals;

import Model.Cards.Card;

/**
 * the cards which are not spells
 * are Reals , right ? :-)
 * @version 1.0.0
 * @since 7.8.2021
 */
public abstract class Real extends Card {
    private int health , damage;
    private double hitSpeed , range;
    private Type myType;
    private Type targetType;

    /**
     * the constructor for Real Card
     * @param cost is the cost of the card
     * @param cardImageAddress is the card image of the card
     * @param imageAddress is the image of the card
     * @param health is the health of the card
     * @param damage is the damage of the card
     * @param range is the range of the card
     * @param hitSpeed is the hit speed of the card
     * @param myType is the type of the card
     * @param targetType is the type of the targets of the card
     */
    public Real(int cost, String cardImageAddress, String imageAddress,
                int health, int damage, double range,
                double hitSpeed, Type myType, Type targetType) {

        super(cost, cardImageAddress, imageAddress);
        this.health = health;
        this.damage = damage;
        this.range = range;
        this.hitSpeed = hitSpeed;
        this.myType = myType;
        this.targetType = targetType;
    }

    /**
     *
     * @return the health of the card
     */
    public int getHealth() {
        return health;
    }

    /**
     *
     * @return the damage of the card
     */
    public int getDamage() {
        return damage;
    }

    /**
     *
     * @return the hit speed of the card
     */
    public double getHitSpeed() {
        return hitSpeed;
    }

    /**
     *
     * @return the range of the card
     */
    public double getRange() {
        return range;
    }

    /**
     *
     * @return the type of the card
     */
    public Type getMyType() {
        return myType;
    }

    /**
     *
     * @return the type of the target of the card
     */
    public Type getTargetType() {
        return targetType;
    }

    /**
     * sets the health of the card
     * @param health is the new health for the card
     * the health changes by upgrading the card
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * sets the damage of the card
     * @param damage is the new damage for the card
     * the damage changes by upgrading the card
     */
    public void setDamage(int damage) {
        this.damage = damage;
    }
}
