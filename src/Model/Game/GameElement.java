package Model.Game;

import Model.Cards.Reals.Buildings.Building;
import Model.Cards.Reals.Troops.Speed;
import Model.Cards.Reals.Troops.Troop;
import Model.Cards.Spells.Spell;
import Model.GameEntity;
import Model.Interfaces.Damager;
import Model.Interfaces.HealthHaver;
import Model.Towers.Tower;
import Users.Player;

import java.awt.geom.Point2D;
import java.io.Serializable;

/**
 * class for elements of the game
 * @since 7.8.2021
 * @version 1.0.0
 */
public class GameElement implements Serializable {
    private GameEntity gameEntity;
    private boolean isHealthHaver;
    private boolean isDamager;
    private boolean isSpell;
    private boolean isTroop;
    private boolean isBuilding;
    private boolean isTower;
    private boolean boosted;
    private int health;
    private int damage;
    private double hitSpeed;
    private Point2D location;
    private Player owner;
    private long madeAtFrame;
    private Direction direction;
    private Speed speed;

    /**
     * the constructor for the game element
     * @param gameEntity is the entity of the element
     * @param location is the location of the element
     * @param owner is the player who owns the element
     * @param madeAtFrame is the frame that the element is made
     * @param direction is the direction of the element
     */
    public GameElement(GameEntity gameEntity,
                       Point2D location,
                       Player owner,
                       long madeAtFrame,
                       Direction direction) {
        this.gameEntity = gameEntity;
        this.location = location;
        this.owner = owner;
        this.madeAtFrame = madeAtFrame;
        this.direction = direction;
        if(gameEntity instanceof HealthHaver){
            this.isHealthHaver = true;
            health = ((HealthHaver) gameEntity).getHealth();
        }
        if(gameEntity instanceof Damager){
            this.isDamager = true;
            damage = ((Damager) gameEntity).getDamage();
            hitSpeed = ((Damager) gameEntity).getHitSpeed();
        }
        if(gameEntity instanceof Troop){
            this.isTroop = true;
            this.speed = ((Troop) gameEntity).getSpeed();
        }
        else if (gameEntity instanceof Building){
            this.isBuilding = true;
        }
        else if(gameEntity instanceof Spell){
            this.isSpell = true;
        }
        else if(gameEntity instanceof Tower){
            this.isTower = true;
        }
    }

    /**
     * boosts the element
     */
    public void boost(){
        if(gameEntity instanceof Damager){
            boosted = true;
            damage = (int) (damage * (140.0 / 100.0));
            hitSpeed = hitSpeed * (100.0/140.0);
        }
        if(gameEntity instanceof Troop){
            if(speed == Speed.slow){
                speed = Speed.medium;
            }
            else if(speed == Speed.medium){
                speed = Speed.fast;
            }
            else if(speed == Speed.fast){
                speed = Speed.superFast;
            }
        }
    }

    /**
     * if it is boosted , not any more :)
     */
    public void deBoost(){

        if(gameEntity instanceof Damager){
            boosted = false;
            this.damage = ((Damager) gameEntity).getDamage();
            this.hitSpeed = ((Damager) gameEntity).getHitSpeed();
        }
        if(gameEntity instanceof Troop){
            if(speed == Speed.medium){
                speed = Speed.slow;
            }
            else if(speed == Speed.fast){
                speed = Speed.medium;
            }
            else if(speed == Speed.superFast){
                speed = Speed.fast;
            }
        }
    }

    /**
     * this method decreases the health of an element
     * @param damage is the damage of the hunter who damages this game element
     */
    public void hurt(int damage){
        if(damage > health){
            health = 0;
        }
        else {
            health -= damage;
        }
    }

    /**
     * getter for entity of element
     * @return the entity of the element
     */
    public GameEntity getGameEntity() {
        return gameEntity;
    }

    /**
     *
     * @return true if the entity is instance of Spell , else false
     */
    public boolean isSpell() {
        return isSpell;
    }

    /**
     *
     * @return true if the entity is instance of Troop , else false
     */
    public boolean isTroop() {
        return isTroop;
    }

    /**
     *
     * @return true if the entity is instance of building
     */
    public boolean isBuilding() {
        return isBuilding;
    }

    /**
     *
     * @return the health of the element
     */
    public int getHealth() {
        return health;
    }

    /**
     *
     * @return the damage of this element
     */
    public int getDamage() {
        return damage;
    }

    /**
     *
     * @return the location of this element
     */
    public Point2D getLocation() {
        return location;
    }

    /**
     *
     * @return the owner of this element
     */
    public Player getOwner() {
        return owner;
    }

    /**
     *
     * @return the number of frame this element is made at
     */
    public long getMadeAtFrame() {
        return madeAtFrame;
    }

    /**
     *
     * @return direction of elemen
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     *
     * @return the speed of the element
     */
    public Speed getSpeed() {
        return speed;
    }

    public String getImageAddress(){
        if(gameEntity != null){
            return gameEntity.getImageAddress();
        }
        return null;
    }

    /**
     * sets the new location for element
     * @param location is the new location
     */
    public void setLocation(Point2D location) {
        this.location = location;
    }

    /**
     * sets the direction of the element
     * @param direction is the new direction
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * sets the damage of the element
     * @param damage is the new damage
     */
    public void setDamage(int damage) {
        this.damage = damage;
    }

    /**
     *
     * @return true if element is boosted , else false
     */
    public boolean isBoosted() {
        return boosted;
    }

    /**
     * makes a copy from this element
     * @return the copy
     */
    public GameElement copy(){
        return new GameElement(gameEntity , location , owner , madeAtFrame , direction);
    }

}
