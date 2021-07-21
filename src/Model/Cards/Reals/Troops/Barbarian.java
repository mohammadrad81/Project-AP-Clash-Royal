package Model.Cards.Reals.Troops;

import Model.Cards.Reals.Buildings.InfernoTower;
import Model.Cards.Reals.Type;
import Model.Interfaces.GroundWarrior;

/**
 * barbarian card of the game
 * @since 7.8.2021
 * @author Alireza Jabbari Mahalle No
 * @author Mohammad Heydari Rad
 * @version 1.0.0
 */
public class Barbarian extends Troop implements GroundWarrior {
    private static final String cardImageAddress = "/Pictures/CardImages/BarbariansCard.png";
    private static final String imageAddress = "/Pictures/ElementImages/Barbarian/color/";
    private static final int[] healthByLevelArray = {300, 330, 363, 438, 480};
    private static final int[] damageByLevel = {75, 82, 90, 99, 109};

    /**
     * constructor for a first-level barbarian card
     */
    public Barbarian() {
        super(5, cardImageAddress, imageAddress, 1,
                1.5, Type.ground, Type.ground,
                healthByLevelArray, damageByLevel,
                Speed.medium, false, 4);
    }

    /**
     * constructor for a new leveled barbarian
     * @param level is the level of the player
     */
    public Barbarian(int level) {
        super(5, cardImageAddress, imageAddress, 1,
                1.5, Type.ground, Type.ground,
                healthByLevelArray, damageByLevel,
                Speed.medium, false, 4);
        upgrade(level);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            return false;
        }
        if(! (obj instanceof Barbarian)){
            return false;
        }
        return true;
    }
}
