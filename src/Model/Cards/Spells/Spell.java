package Model.Cards.Spells;

import Model.Cards.Card;

public abstract class Spell extends Card {
    private double radius;
    private Ability ability;

    public Spell(int cost, String cardImageAddress, String imageAddress,
                 double radius , Ability ability) {
        super(cost, cardImageAddress, imageAddress);

        this.radius = radius;
        this.ability = ability;
    }

    public double getRadius() {
        return radius;
    }

    public Ability getAbility() {
        return ability;
    }
}
