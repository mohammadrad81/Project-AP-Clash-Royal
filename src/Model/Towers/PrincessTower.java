package Model.Towers;

/**
 * the princess tower of the game
 * @since 7.8.2021
 * @version 1.0.0
 */
public class PrincessTower extends Tower{
    private static final int[] healthByLevelArray = {1400, 1512, 1624, 1750, 1890};
    private static final int[] damageByLevelArray = {50, 54, 58, 62, 69};
    private static final String imageAddress = "/Pictures/ElementImages/Towers/PrincesTower/color/";

    /**
     * the constructor for a first-level princess tower
     */
    public PrincessTower() {
        super(imageAddress, healthByLevelArray,
                damageByLevelArray, 7.5, 0.8,
                true);
    }

    /**
     * constructor for a new up leveled princess tower
     * @param level is the level of the player
     */
    public PrincessTower(int level) {
        super(imageAddress, healthByLevelArray,
                damageByLevelArray, 7.5, 0.8,
                true);
        upgrade(level);
    }
}
