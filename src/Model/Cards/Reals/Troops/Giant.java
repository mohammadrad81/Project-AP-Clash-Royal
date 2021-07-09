package Model.Cards.Reals.Troops;

import Model.Cards.Reals.Type;

/**
 * giant card of the game
 * @since 7.8.2021
 * @version 1.0.0
 */
public class Giant extends Troop{
    private static final String cardImageAddress = "/Pictures/CardImages/GiantCard.jpg";
    private static final String imageAddress = "";// will be written later
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
}
