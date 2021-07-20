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

    public void boost(){
        if(gameEntity instanceof Damager){
            boosted = true;
            damage = (int) (damage * (140.0 / 100.0));
            hitSpeed = hitSpeed * (140.0 / 100.0);
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

    public void hurt(int damage){
        if(damage > health){
            health = 0;
        }
        else {
            health -= damage;
        }
    }

    public GameEntity getGameEntity() {
        return gameEntity;
    }

    public boolean isHealthHaver() {
        return isHealthHaver;
    }

    public boolean isDamager() {
        return isDamager;
    }

    public boolean isSpell() {
        return isSpell;
    }

    public boolean isTroop() {
        return isTroop;
    }

    public boolean isBuilding() {
        return isBuilding;
    }

    public boolean isTower() {
        return isTower;
    }

    public int getHealth() {
        return health;
    }

    public int getDamage() {
        return damage;
    }

    public Point2D getLocation() {
        return location;
    }

    public Player getOwner() {
        return owner;
    }

    public long getMadeAtFrame() {
        return madeAtFrame;
    }

    public Direction getDirection() {
        return direction;
    }

    public Speed getSpeed() {
        return speed;
    }

    public String getImageAddress(){
        if(gameEntity != null){
            return gameEntity.getImageAddress();
        }
        return null;
    }

    public void setLocation(Point2D location) {
        this.location = location;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public boolean isBoosted() {
        return boosted;
    }

    public GameElement copy(){
        return new GameElement(gameEntity , location , owner , madeAtFrame , direction);
    }

}
