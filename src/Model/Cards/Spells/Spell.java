package Model.Cards.Spells;

import Model.Cards.Card;

/**
 * class for spell cards of the game
 * @author Alireza Jabbari Mahalle No
 * @author Mohammad Heydari Rad
 * @version 1.0.0
 * @since 7.8.2021
 */
public abstract class Spell extends Card {
    private double radius;
    private Ability ability;

    /**
     * constructor of the spell
     * @param cost is the cost of the spell
     * @param cardImageAddress the card image of the spell
     * @param imageAddress the image of the spell ( null )
     * @param radius the radius of the spell ability
     * @param ability the ability of the spell
     */
    public Spell(int cost, String cardImageAddress, String imageAddress,
                 double radius , Ability ability) {
        super(cost, cardImageAddress, imageAddress);

        this.radius = radius;
        this.ability = ability;
    }

    /**
     *
     * @return the radius of the spell ability
     */
    public double getRadius() {
        return radius;
    }

    /**
     *
     * @return the ability of the spell
     */
    public Ability getAbility() {
        return ability;
    }
}
