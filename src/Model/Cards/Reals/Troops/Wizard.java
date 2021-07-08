package Model.Cards.Reals.Troops;

import Model.Cards.Reals.Type;

/**
 * wizard card of the game
 */
public class Wizard extends Troop{
    private static final String cardImageAddress = "../../../../Pictures/CardImages/WizardCard.jpg";
    private static final String imageAddress = "";// will be written later
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
}
