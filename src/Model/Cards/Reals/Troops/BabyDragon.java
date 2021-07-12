package Model.Cards.Reals.Troops;

import Model.Cards.Reals.Type;
import Model.Interfaces.AirWarrior;

/**
 * baby dragon card of the game
 * @since 7.8.2021
 * @version 1.0.0
 */
public class BabyDragon extends Troop implements AirWarrior {
    private static final String cardImageAddress = "/Pictures/CardImages/BabyDragonCard.jpg";
    private static final String imageAddress = "";// will be written later
    private static final int[] healthByLevelArray = {800, 880, 968, 1064, 1168};
    private static final int[] damageByLevel = {100, 110, 121, 133, 146};

    /**
     * constructor for a new first-level babyDragon card
     */
    public BabyDragon() {
        super(4, cardImageAddress, imageAddress,
                3, 1.8, Type.air,
                Type.airAndGround, healthByLevelArray, damageByLevel,
                Speed.fast, true, 1);
    }

    /**
     * constructor for a new leveled babyDragon
     * @param level is the level of the player
     */
    public BabyDragon(int level) {
        super(4, cardImageAddress, imageAddress,
                3, 1.8, Type.air,
                Type.airAndGround, healthByLevelArray, damageByLevel,
                Speed.fast, true, 1);
        upgrade(level);
    }
}
