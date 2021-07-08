package Model.Cards.Reals.Buildings;

import Model.Cards.Reals.Real;
import Model.Cards.Reals.Type;
import Model.Property;

public abstract class Building extends Real implements Property {
    private int lifeTime;

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
