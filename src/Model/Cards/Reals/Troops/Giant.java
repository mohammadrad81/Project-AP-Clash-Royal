package Model.Cards.Reals.Troops;

import Model.Cards.Reals.Type;
import Model.Interfaces.GroundWarrior;


/**
 * giant card of the game
 * @since 7.8.2021
 * @author Alireza Jabbari Mahalle No
 * @author Mohammad Heydari Rad
 * @version 1.0.0
 */
public class Giant extends Troop implements GroundWarrior {
    private static final String cardImageAddress = "/Pictures/CardImages/GiantCard.png";
    private static final String imageAddress = "/Pictures/ElementImages/Giant/color/";
    private static final int[] healthByLevelArray = {2000, 2200, 2420, 2660, 2920};
    private static final int[] damageByLevel = {126, 138, 152, 167, 183};

    /**
     * constructor for a first-level giant card
     */
    public Giant() {
        super(5, cardImageAddress, imageAddress,
                1, 1.5, Type.ground,
                Type.ground, healthByLevelArray, damageByLevel,
                Speed.slow, false, 1);
    }

    /**
     * constructor for a new leveled giant
     * @param level is the level of the player
     */
    public Giant(int level) {
        super(5, cardImageAddress, imageAddress,
                1, 1.5, Type.ground,
                Type.ground, healthByLevelArray, damageByLevel,
                Speed.slow, false, 1);
        upgrade(level);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            return false;
        }
        if(! (obj instanceof Giant)){
            return false;
        }
        return true;
    }
}
