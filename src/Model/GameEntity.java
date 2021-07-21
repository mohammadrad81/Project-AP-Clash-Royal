package Model;

/**
 * a class for all entities of the games
 * which their attributes are depended on the
 * level of the player
 *
 * @author Alireza Jabbari Mahalle No
 * @author Mohammad Heydari Rad
 * @version 1.0.0
 * @since 7.8.2021
 */

public interface GameEntity {
    /**
     * upgrades the entity by the level of the player
     * @param level is the level of the player
     */
    public void upgrade(int level);
    public String  getImageAddress();
}
