package Model.Cards.Reals.Troops;

import Model.Cards.Reals.Buildings.InfernoTower;
import Model.Cards.Reals.Type;
import Model.Interfaces.GroundWarrior;

/**
 * wizard card of the game
 * @since 7.8.2021
 * @author Alireza Jabbari Mahalle No
 * @author Mohammad Heydari Rad
 * @version 1.0.0
 */
public class Wizard extends Troop implements GroundWarrior{
    private static final String cardImageAddress = "/Pictures/CardImages/WizardCard.png";
    private static final String imageAddress = "/Pictures/ElementImages/Wizard/color/";
    private static final int[] healthByLevelArray = {340, 374, 411, 452, 496};
    private static final int[] damageByLevel = {130, 143, 157, 172, 189};

    /**
     * constructor of a new first-level wizard card
     */
    public Wizard() {
        super(5, cardImageAddress, imageAddress,
                5, 1.7, Type.ground,
                Type.airAndGround, healthByLevelArray, damageByLevel,
                Speed.medium, true, 1);
    }
    /**
     * constructor for a new leveled wizard
     * @param level is the level of the player
     */
    public Wizard(int level) {
        super(5, cardImageAddress, imageAddress,
                5, 1.7, Type.ground,
                Type.airAndGround, healthByLevelArray, damageByLevel,
                Speed.medium, true, 1);
        upgrade(level);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            return false;
        }
        if(! (obj instanceof Wizard)){
            return false;
        }
        return true;
    }
}
