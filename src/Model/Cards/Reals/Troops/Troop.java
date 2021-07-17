package Model.Cards.Reals.Troops;

import Model.Cards.Reals.Real;
import Model.Cards.Reals.Type;

/**
 * Troops of the game
 * @version 1.0.0
 * @since 7.8.2021
 */
public abstract class Troop extends Real {
    private Speed speed;
    private boolean areaSplash;
    private int count;
    private final int[] healthByLevelArray;
    private final int[] damageByLevel;

    /**
     * constructor for the Troop
     * @param cost is the cost of the card
     * @param cardImageAddress is the card image of the card
     * @param imageAddress is the image of the card
     * @param range is the range of the card
     * @param hitSpeed is the hit speed of the card
     * @param myType is the type of the card
     * @param targetType is the type of the targets of the card
     * @param healthByLevelArray is the array of the health of the card ,
     *                          the index is the level of the card - 1
     * @param damageByLevel is the array of the damage of the card,
     *                      the index is the level of the card - 1
     * @param speed is the speed of the card
     * @param areaSplash is the area splash of the card
     * @param count is the count of the elements a card makes ( how many by a card )
     */
    public Troop(int cost, String cardImageAddress, String imageAddress,
                  double range, double hitSpeed, Type myType,
                 Type targetType , int[] healthByLevelArray, int[] damageByLevel,
                 Speed speed , boolean areaSplash , int count) {

        super(cost, cardImageAddress, imageAddress,
                healthByLevelArray[0], damageByLevel[0], range,
                hitSpeed, myType, targetType);
        this.healthByLevelArray = healthByLevelArray;
        this.damageByLevel = damageByLevel;
        this.speed = speed;
        this.areaSplash = areaSplash;
        this.count = count;
    }

    /**
     * changes the health and damage of the card , by the level of the player
     * @param level is the level of the player
     */
    @Override
    public void upgrade(int level){
        setHealth(healthByLevelArray[level -1]);
        setDamage(damageByLevel[level -1]);
    }

    /**
     *
     * @return the speed of the Troop
     */
    public Speed getSpeed() {
        return speed;
    }

    /**
     *
     * @return the area splash of the card
     */
    public boolean isAreaSplash() {
        return areaSplash;
    }

    /**
     *
     * @return the count of the card ( count of the elements it makes )
     */
    public int getCount() {
        return count;
    }
}
