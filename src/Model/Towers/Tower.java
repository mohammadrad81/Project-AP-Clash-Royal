package Model.Towers;

import Model.Cards.Reals.Type;
import Model.GameEntity;

/**
 * the towers of the game
 * @since 7.8.2021
 * @version 1.0.0
 */
public abstract class Tower implements GameEntity {
    private Type myType , targetType;
    private String imageAddress;
    private int[] healthByLevelArray , damageByLevelArray;
    private int health , damage , level;
    private double range , hitSpeed;
    private boolean activeToShoot;

    /**
     * constructor for the tower
     * @param imageAddress is the image address for the tower
     * @param healthByLevelArray is the array of the health
     *                           the index is the level -1
     * @param damageByLevelArray is the array of the damage
     *                           the index is the level -1
     * @param range is the range of shooting of the tower
     * @param hitSpeed is the hit speed of the tower
     * @param activeToShoot is true if the tower is active to shoot
     *                      else , false ( the king tower is not active
     *                      till it is hurt or a princess tower is down)
     */
    public Tower(String imageAddress, int[] healthByLevelArray,
                 int[] damageByLevelArray, double range,
                 double hitSpeed, boolean activeToShoot) {
        this.myType = Type.ground;
        this.targetType = Type.airAndGround;
        this.imageAddress = imageAddress;
        this.healthByLevelArray = healthByLevelArray;
        this.damageByLevelArray = damageByLevelArray;
        this.health = healthByLevelArray[0];
        this.damage = damageByLevelArray[0];
        this.range = range;
        this.hitSpeed = hitSpeed;
        this.activeToShoot = activeToShoot;
        this.level = 1;
    }

    /**
     * upgrades the health and damage of the tower
     * by the level of the player
     * @param level is the level of the player
     */
    @Override
    public void upgrade(int level){
        this.level = level;
        this.health = healthByLevelArray[level -1];
        this.damage = damageByLevelArray[level -1];
    }

    /**
     * sets the activeToShoot
     * @param activeToShoot is the new active to shoot
     */
    public void setActiveToShoot(boolean activeToShoot){
        this.activeToShoot = activeToShoot;
    }

    /**
     *
     * @return the type of the tower ( ground basically )
     */
    public Type getMyType() {
        return myType;
    }

    /**
     *
     * @return the type of the targets of the tower
     * ( basically , airAndGround )
     */
    public Type getTargetType() {
        return targetType;
    }

    /**
     *
     * @return the image address of the tower
     */
    public String getImageAddress() {
        return imageAddress;
    }

    /**
     *
     * @return the health of the tower
     */
    public int getHealth() {
        return health;
    }

    /**
     *
     * @return the damage of the tower
     */
    public int getDamage() {
        return damage;
    }

    /**
     *
     * @return the range of the tower
     */
    public double getRange() {
        return range;
    }

    /**
     *
     * @return the hit speed of the tower
     */
    public double getHitSpeed() {
        return hitSpeed;
    }

    /**
     *
     * @return true if the tower is active to shoot
     * else , false
     */
    public boolean isActiveToShoot() {
        return activeToShoot;
    }
}
