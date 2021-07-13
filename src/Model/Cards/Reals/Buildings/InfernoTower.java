package Model.Cards.Reals.Buildings;

import Model.Cards.Reals.Type;

/**
 * inferno tower card of the game
 * @since 7.8.2021
 * @version 1.0.0
 */
public class InfernoTower extends Building{
    private static final String infernoCardImageAddress = "/Pictures/CardImages/InfernoTowerCard.png";
    private static final String infernoImageAddress = "";
    private static final int[] firstDamageByLevel = {20, 22, 24, 26, 29};
    private static final int[] lastDamageByLevel = {400, 440, 484, 532, 584};
    private static final int[] healthByLevel = {800, 880, 968, 1064, 1168};
    private int lastDamage;

    /**
     * constructor for a first-level inferno tower
     */
    public InfernoTower() {
        super(5, infernoCardImageAddress, infernoImageAddress,
                healthByLevel[0], firstDamageByLevel[0], 6,
                0.4, Type.ground, Type.airAndGround,
                40);
        this.lastDamage = lastDamageByLevel[0];
    }

    /**
     * constructor for a new leveled inferno tower
     * @param level is the level of inferno tower
     */
    public InfernoTower(int level){
        super(5, infernoCardImageAddress, infernoImageAddress,
                healthByLevel[0], firstDamageByLevel[0], 6,
                0.4, Type.ground, Type.airAndGround,
                40);
        upgrade(level);
    }

    /**
     * upgrades the inferno tower by the level of the player
     * @param level is the level of the player
     */
    @Override
    public void upgrade(int level) {
        setDamage(firstDamageByLevel[level -1]);
        setHealth(healthByLevel[level -1]);
        this.lastDamage = lastDamageByLevel[level -1];
    }

    /**
     *
     * @return the last damage of the inferno tower
     */
    public int getLastDamage() {
        return lastDamage;
    }
}
