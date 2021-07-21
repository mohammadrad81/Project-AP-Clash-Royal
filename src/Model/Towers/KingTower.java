package Model.Towers;

/**
 * the king tower of the game
 * @since 7.8.2021
 * @author Alireza Jabbari Mahalle No
 * @author Mohammad Heydari Rad
 * @version 1.0.0
 */
public class KingTower extends Tower {
    private static final int[] healthByLevelArray = {2400, 2568, 2736, 2904, 3086};
    private static final int[] damageByLevelArray = {50, 53, 57, 60, 64};
    private static final String imageAddress = "/Pictures/ElementImages/Towers/KingTower/color/";// will be written later

    /**
     * the constructor for a new first-level king tower
     */
    public KingTower() {
        super(imageAddress, healthByLevelArray,
                damageByLevelArray, 7, 1,
                false);
    }

    /**
     * the constructor for a new leveled king tower
     * @param level is the level of the player
     */
    public KingTower(int level) {
        super(imageAddress, healthByLevelArray,
                damageByLevelArray, 7, 1,
                false);
        upgrade(level);
    }
}
