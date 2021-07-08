package Model.Cards.Reals;

import Model.Cards.Card;

public abstract class Real extends Card {
    private int health , damage;
    private double hitSpeed , range;
    private Type myType;
    private Type targetType;

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

    public int getHealth() {
        return health;
    }

    public int getDamage() {
        return damage;
    }

    public double getHitSpeed() {
        return hitSpeed;
    }

    public double getRange() {
        return range;
    }

    public Type getMyType() {
        return myType;
    }

    public Type getTargetType() {
        return targetType;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}
