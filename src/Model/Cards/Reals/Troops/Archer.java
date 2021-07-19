package Model.Cards.Reals.Troops;

import Model.Cards.Reals.Buildings.InfernoTower;
import Model.Cards.Reals.Type;
import Model.Interfaces.GroundWarrior;

/**
 * the archer card of the game
 * @since 7.8.2021
 * @version 1.0.0
 */
public class Archer extends Troop implements  GroundWarrior{
    private static final String cardImageAddress = "/Pictures/CardImages/ArchersCard.png";
    private static final String imageAddress = "/Pictures/ElementImages/Archer/color/";
    private static final int[] healthByLevelArray = {125, 127, 151, 166, 182};
    private static final int[] damageByLevelArray = {33, 44, 48, 53, 58};

    /**
     * constructor for a new first-level archer
     */
    public Archer() {
        super(3, cardImageAddress, imageAddress,
                5, 1.2, Type.ground, Type.airAndGround,
                healthByLevelArray, damageByLevelArray, Speed.fast ,
                true , 2);
    }

    /**
     * constructor for a new leveled archer
     * @param level is the level of the player
     */
    public Archer(int level) {
        super(3, cardImageAddress, imageAddress,
                5, 1.2, Type.ground, Type.airAndGround,
                healthByLevelArray, damageByLevelArray, Speed.fast ,
                true , 2);

        upgrade(level);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            return false;
        }
        if(! (obj instanceof Archer)){
            return false;
        }
        return true;
    }

}
