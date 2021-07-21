package Model.Cards.Spells;

import Model.Cards.Reals.Buildings.InfernoTower;

/**
 * class for fireball card of game
 * @author Alireza Jabbari Mahalle No
 * @author Mohammad Heydari Rad
 * @version 1.0.0
 * @since 7.8.2021
 */
public class Fireball extends Spell{
    private static final String fireBallCardImageAddress = "/Pictures/CardImages/FireballCard.png";
    private static final int[] areaDamageByLevelArray = {325, 357, 393, 532, 474};

    private int areaDamage;

    /**
     * constructor for card
     */
    public Fireball() {
        super(4 , fireBallCardImageAddress , null,
                2.5 , Ability.fire);
        areaDamage = areaDamageByLevelArray[0];
    }
    /**
     * constructor for leveled card
     * @param level is level of card
     */
    public Fireball(int level){
        super(4 , fireBallCardImageAddress , null,
                2.5 , Ability.fire);
        upgrade(level);
    }

    /**
     * upgrades the card ( area damage ) by level
     * @param level is the level of the player
     */
    @Override
    public void upgrade(int level) {
        areaDamage = areaDamageByLevelArray[level -1];
    }

    /**
     *
     * @return area damage of the card
     */
    public int getAreaDamage() {
        return areaDamage;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            return false;
        }
        if(! (obj instanceof Fireball)){
            return false;
        }
        return true;
    }
}
