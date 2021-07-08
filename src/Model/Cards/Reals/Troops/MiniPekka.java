package Model.Cards.Reals.Troops;

import Model.Cards.Reals.Type;

/**
 * mini pekka card of the game
 * @since 7.8.2021
 * @version 1.0.0
 */
public class MiniPekka extends Troop{
    private static final String cardImageAddress = "../../../../Pictures/CardImages/MiniPEKKACard.jpg";
    private static final String imageAddress = "";// will be written later
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
}