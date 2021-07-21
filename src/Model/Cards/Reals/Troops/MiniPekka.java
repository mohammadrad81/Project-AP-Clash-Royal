package Model.Cards.Reals.Troops;

import Model.Cards.Reals.Buildings.InfernoTower;
import Model.Cards.Reals.Type;
import Model.Interfaces.GroundWarrior;

/**
 * mini pekka card of the game
 * @since 7.8.2021
 * @author Alireza Jabbari Mahalle No
 * @author Mohammad Heydari Rad
 * @version 1.0.0
 */
public class MiniPekka extends Troop implements GroundWarrior{
    private static final String cardImageAddress = "/Pictures/CardImages/MiniPEKKACard.png";
    private static final String imageAddress = "/Pictures/ElementImages/MiniPekka/color/";
    private static final int[] healthByLevelArray = {600, 660, 726, 798, 876};
    private static final int[] damageByLevel = {325, 357, 393, 432, 474};

    /**
     * constructor of a new first-level miniPekka card
     */
    public MiniPekka() {
        super(4, cardImageAddress, imageAddress,
                1, 1.8, Type.ground,
                Type.ground, healthByLevelArray, damageByLevel,
                Speed.fast, false, 1);
    }

    /**
     * constructor for a new leveled miniPekka
     * @param level is the level of the player
     */
    public MiniPekka(int level) {
        super(4, cardImageAddress, imageAddress,
                1, 1.8, Type.ground,
                Type.ground, healthByLevelArray, damageByLevel,
                Speed.fast, false, 1);
        upgrade(level);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            return false;
        }
        if(! (obj instanceof MiniPekka)){
            return false;
        }
        return true;
    }
}
