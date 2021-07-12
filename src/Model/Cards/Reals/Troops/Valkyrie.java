package Model.Cards.Reals.Troops;

import Model.Cards.Reals.Type;
import Model.Interfaces.GroundWarrior;

/**
 * the valkyrie card of the game
 * @since 7.8.2021
 * @version 1.0.0
 */
public class Valkyrie extends Troop implements GroundWarrior {
    private static final String cardImageAddress = "/Pictures/CardImages/ValkyrieCard.jpg";
    private static final String imageAddress = "";// will be written later
    private static final int[] healthByLevelArray = {880, 968, 1064, 1170, 1280};
    private static final int[] damageByLevel = {120, 132, 145, 159, 175};

    /**
     * constructor for a new first-level valkyrie card
     */
    public Valkyrie() {
        super(4, cardImageAddress, imageAddress,
                1, 1.5, Type.ground,
                Type.ground, healthByLevelArray, damageByLevel,
                Speed.medium, true, 1);
    }

    /**
     * constructor for a new leveled valkyrie
     * @param level is the level of the player
     */
    public Valkyrie(int level) {
        super(4, cardImageAddress, imageAddress,
                1, 1.5, Type.ground,
                Type.ground, healthByLevelArray, damageByLevel,
                Speed.medium, true, 1);
        upgrade(level);
    }

}
