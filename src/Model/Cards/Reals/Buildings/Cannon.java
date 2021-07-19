package Model.Cards.Reals.Buildings;

import Model.Cards.Reals.Type;

/**
 * the cannon card of the game
 * @since 7.8.2021
 * @version 1.0.0
 */
public class Cannon extends Building{
    private static final String cannonCardImageAddress = "/Pictures/CardImages/CannonCard.png";
    private static final String cannonImageAddress = "/Pictures/ElementImages/Cannon/color/cannon.png";
    private static final int[] damageByLevelArray ={60, 66, 72, 79, 87};
    private static final int[] healthByLevelArray ={380, 418, 459, 505, 554};

    /**
     * constructor for a first-level cannon
     */
    public Cannon() {
        super(3, cannonCardImageAddress, cannonImageAddress,
                healthByLevelArray[0], damageByLevelArray[0],
                5.5, 0.8, Type.ground, Type.ground, 30);
    }

    /**
     * constructor for a new leveled cannon
     * @param level is the level of cannon
     */
    public Cannon(int level){
        super(3, cannonCardImageAddress, cannonImageAddress,
                healthByLevelArray[0], damageByLevelArray[0],
                5.5, 0.8, Type.ground, Type.ground, 30);
        upgrade(level);
    }

    /**
     * upgrades the cannon by the level of the player
     * @param level is the level of the player
     */
    @Override
    public void upgrade(int level) {
        setDamage(damageByLevelArray[level -1]);
        setHealth(healthByLevelArray[level -1]);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            return false;
        }
        if(! (obj instanceof Cannon)){
            return false;
        }
        return true;
    }
}
