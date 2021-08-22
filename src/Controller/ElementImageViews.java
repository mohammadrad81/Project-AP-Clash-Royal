package Controller;

import Model.Cards.Reals.Buildings.Cannon;
import Model.Cards.Reals.Buildings.InfernoTower;
import Model.Cards.Reals.Troops.*;
import Model.Cards.Spells.Arrows;
import Model.Cards.Spells.Fireball;
import Model.Game.Direction;
import Model.Game.GameElement;
import Model.GameEntity;
import Model.Towers.KingTower;
import Model.Towers.PrincessTower;
import Users.Player;
import javafx.scene.image.Image;

/**
 * a class that holds the images of the game
 * @author Alireza Jabbari Mahalle No
 * @author Mohammad Heydari Rad
 * @version 1.0.0
 */
public class ElementImageViews {
    public  Class cl = getClass();
    public  final Image archerBlueForward = new Image(cl.getResource("/Pictures/ElementImages/Archer/blue/forward.png").toString());
    public  final Image archerBlueBackward = new Image(cl.getResource("/Pictures/ElementImages/Archer/blue/backward.png").toString());
    public  final Image archerBlueLeft = new Image(cl.getResource("/Pictures/ElementImages/Archer/blue/left.png").toString());
    public  final Image archerBlueRight =  (new Image(cl.getResource("/Pictures/ElementImages/Archer/blue/right.png").toString()));
    
    public  final Image archerRedForward =  (new Image(cl.getResource("/Pictures/ElementImages/Archer/red/forward.png").toString()));
    public  final Image archerRedBackward =  (new Image(cl.getResource("/Pictures/ElementImages/Archer/red/backward.png").toString()));
    public  final Image archerRedLeft =  (new Image(cl.getResource("/Pictures/ElementImages/Archer/red/left.png").toString()));
    public  final Image archerRedRight =  (new Image(cl.getResource("/Pictures/ElementImages/Archer/red/right.png").toString()));
    
    public  final Image babyDragonBlueForward =  (new Image(cl.getResource("/Pictures/ElementImages/BabyDragon/blue/forward.png").toString()));
    public  final Image babyDragonBlueBackward =  (new Image(cl.getResource("/Pictures/ElementImages/BabyDragon/blue/backward.png").toString()));
    public  final Image babyDragonBlueLeft =  (new Image(cl.getResource("/Pictures/ElementImages/BabyDragon/blue/left.png").toString()));
    public  final Image babyDragonBlueRight =  (new Image(cl.getResource("/Pictures/ElementImages/BabyDragon/blue/right.png").toString()));
    
    public  final Image babyDragonRedForward =  (new Image(cl.getResource("/Pictures/ElementImages/BabyDragon/red/forward.png").toString()));
    public  final Image babyDragonRedBackward =  (new Image(cl.getResource("/Pictures/ElementImages/BabyDragon/red/backward.png").toString()));
    public  final Image babyDragonRedLeft =  (new Image(cl.getResource("/Pictures/ElementImages/BabyDragon/red/left.png").toString()));
    public  final Image babyDragonRedRight =  (new Image(cl.getResource("/Pictures/ElementImages/BabyDragon/red/right.png").toString()));
    
    public  final Image barbarianBlueForward =  (new Image(cl.getResource("/Pictures/ElementImages/Barbarian/blue/forward.png").toString()));
    public  final Image barbarianBlueBackward =  (new Image(cl.getResource("/Pictures/ElementImages/Barbarian/blue/backward.png").toString()));
    public  final Image barbarianBlueLeft =  (new Image(cl.getResource("/Pictures/ElementImages/Barbarian/blue/left.png").toString()));
    public  final Image barbarianBlueRight =  (new Image(cl.getResource("/Pictures/ElementImages/Barbarian/blue/right.png").toString()));
    
    public  final Image barbarianRedForward =  (new Image(cl.getResource("/Pictures/ElementImages/Barbarian/red/forward.png").toString()));
    public  final Image barbarianRedBackward =  (new Image(cl.getResource("/Pictures/ElementImages/Barbarian/red/backward.png").toString()));
    public  final Image barbarianRedLeft =  ( new Image(cl.getResource("/Pictures/ElementImages/Barbarian/red/left.png").toString()));
    public  final Image barbarianRedRight =  (new Image(cl.getResource("/Pictures/ElementImages/Barbarian/red/right.png").toString()));
    
    public  final Image giantBlueForward =  (new Image(cl.getResource("/Pictures/ElementImages/Giant/blue/forward.png").toString()));
    public  final Image giantBlueBackward =  (new Image(cl.getResource("/Pictures/ElementImages/Giant/blue/backward.png").toString()));
    public  final Image giantBlueLeft =  (new Image(cl.getResource("/Pictures/ElementImages/Giant/blue/left.png").toString()));
    public  final Image giantBlueRight =  (new Image(cl.getResource("/Pictures/ElementImages/Giant/blue/right.png").toString()));

    public  final Image giantRedForward =  (new Image(cl.getResource("/Pictures/ElementImages/Giant/red/forward.png").toString()));
    public  final Image giantRedBackward =  (new Image(cl.getResource("/Pictures/ElementImages/Giant/red/backward.png").toString()));
    public  final Image giantRedLeft =  (new Image(cl.getResource("/Pictures/ElementImages/Giant/red/left.png").toString()));
    public  final Image giantRedRight =  (new Image(cl.getResource("/Pictures/ElementImages/Giant/red/right.png").toString()));
    
    public  final Image cannonBlue =  (new Image(cl.getResource("/Pictures/ElementImages/Cannon/blue/cannon.png").toString()));
    public  final Image cannonRed =  (new Image(cl.getResource("/Pictures/ElementImages/Cannon/red/cannon.png").toString()));
    
    public  final Image inferno =  (new Image(cl.getResource("/Pictures/ElementImages/InfernoTower/InfernoTower.png").toString()));
    
    public  final Image miniPekkaBlueForward =  (new Image(cl.getResource("/Pictures/ElementImages/MiniPekka/blue/forward.png").toString()));
    public  final Image miniPekkaBlueBackward =  (new Image(cl.getResource("/Pictures/ElementImages/MiniPekka/blue/backward.png").toString()));
    public  final Image miniPekkaBlueLeft =  (new Image(cl.getResource("/Pictures/ElementImages/MiniPekka/blue/left.png").toString()));
    public  final Image miniPekkaBlueRight =  (new Image("/Pictures/ElementImages/MiniPekka/blue/right.png"));

    public  final Image miniPekkaRedForward =  (new Image(cl.getResource("/Pictures/ElementImages/MiniPekka/red/forward.png").toString()));
    public  final Image miniPekkaRedBackward =  (new Image(cl.getResource("/Pictures/ElementImages/MiniPekka/red/backward.png").toString()));
    public  final Image miniPekkaRedLeft =  (new Image(cl.getResource("/Pictures/ElementImages/MiniPekka/red/left.png").toString()));
    public  final Image miniPekkaRedRight =  (new Image(cl.getResource("/Pictures/ElementImages/MiniPekka/red/right.png").toString()));
    
    public  final Image kingTowerBlueActive =  (new Image(cl.getResource("/Pictures/ElementImages/Towers/KingTower/blue/active.png").toString()));
    public  final Image kingTowerBlueIdle =  (new Image(cl.getResource("/Pictures/ElementImages/Towers/KingTower/blue/idle.png").toString()));

    public  final Image kingTowerRedActive =  (new Image(cl.getResource("/Pictures/ElementImages/Towers/KingTower/red/active.png").toString()));
    public  final Image kingTowerRedIdle =  (new Image("/Pictures/ElementImages/Towers/KingTower/red/idle.png"));

    public  final Image princesTowerBlue =  (new Image(cl.getResource("/Pictures/ElementImages/Towers/PrincesTower/blue/PrincesTower.png").toString()));
    public  final Image princesTowerRed =  (new Image(cl.getResource("/Pictures/ElementImages/Towers/PrincesTower/red/PrincesTower.png").toString()));
    
    public  final Image valkyrieBlueForward =  (new Image(cl.getResource("/Pictures/ElementImages/Valkyrie/blue/forward.png").toString()));
    public  final Image valkyrieBlueBackward  =  (new Image(cl.getResource("/Pictures/ElementImages/Valkyrie/blue/backward.png").toString()));
    public  final Image valkyrieBlueLeft =  (new Image(cl.getResource("/Pictures/ElementImages/Valkyrie/blue/left.png").toString()));
    public  final Image valkyrieBlueRight =  (new Image(cl.getResource("/Pictures/ElementImages/Valkyrie/blue/right.png").toString()));

    public  final Image valkyrieRedForward =  (new Image(cl.getResource("/Pictures/ElementImages/Valkyrie/red/forward.png").toString()));
    public  final Image valkyrieRedBackward  =  (new Image(cl.getResource("/Pictures/ElementImages/Valkyrie/red/backward.png").toString()));
    public  final Image valkyrieRedLeft =  (new Image(cl.getResource("/Pictures/ElementImages/Valkyrie/red/left.png").toString()));
    public  final Image valkyrieRedRight =  (new Image(cl.getResource("/Pictures/ElementImages/Valkyrie/red/right.png").toString()));

    public  final Image wizardBlueForward =  (new Image(cl.getResource("/Pictures/ElementImages/Wizard/blue/forward.png").toString()));
    public  final Image wizardBlueBackward =  (new Image(cl.getResource("/Pictures/ElementImages/Wizard/blue/backward.png").toString()));
    public  final Image wizardBlueLeft =  (new Image(cl.getResource("/Pictures/ElementImages/Wizard/blue/left.png").toString()));
    public  final Image wizardBlueRight =  (new Image(cl.getResource("/Pictures/ElementImages/Wizard/blue/right.png").toString()));

    public  final Image wizardRedForward =  (new Image(cl.getResource("/Pictures/ElementImages/Wizard/red/forward.png").toString()));
    public  final Image wizardRedBackward =  (new Image(cl.getResource("/Pictures/ElementImages/Wizard/red/backward.png").toString()));
    public  final Image wizardRedLeft =  (new Image(cl.getResource("/Pictures/ElementImages/Wizard/red/left.png").toString()));
    public  final Image wizardRedRight =  (new Image(cl.getResource("/Pictures/ElementImages/Wizard/red/right.png").toString()));

    public  final Image arrows =  (new Image(cl.getResource("/Pictures/SpellEffects/Arrows.png").toString()));
    public  final Image fireBall =  (new Image(cl.getResource("/Pictures/SpellEffects/Fireball.png").toString()));
    
    public  final Image[] tiles =
            { (new Image("/Pictures/Tiles/1.png")),
                     (new Image(cl.getResource("/Pictures/Tiles/2.png").toString())),
                     (new Image(cl.getResource("/Pictures/Tiles/3.png").toString())),
                     (new Image(cl.getResource("/Pictures/Tiles/4.png").toString())),
                     (new Image(cl.getResource("/Pictures/Tiles/5.png").toString())),
                     (new Image(cl.getResource("/Pictures/Tiles/6.png").toString())),
                     (new Image(cl.getResource("/Pictures/Tiles/7.png").toString())),
                     (new Image(cl.getResource("/Pictures/Tiles/8.png").toString()))};

    /**
     *
     * @param gameElement is the element , the client class wants its picture
     * @param firstPlayer is the playing player
     * @return the correct image of the element
     */
    public  Image getElementPicture(GameElement gameElement , Player firstPlayer){
        GameEntity gameEntity = gameElement.getGameEntity();

        if(gameEntity instanceof Troop){
            if(gameEntity instanceof Archer){
                if(gameElement.getOwner().equals(firstPlayer)){
                    if(gameElement.getDirection() == Direction.forward){
                        return archerBlueForward;
                    }
                    else if(gameElement.getDirection() == Direction.backward){
                        return archerBlueBackward;
                    }
                    else if(gameElement.getDirection() == Direction.left){
                        return archerBlueLeft;
                    }
                    else if(gameElement.getDirection() == Direction.right){
                        return archerBlueRight;
                    }
                }
                else{
                    if(gameElement.getDirection() == Direction.forward){
                        return archerRedForward;
                    }
                    else if(gameElement.getDirection() == Direction.backward){
                        return archerRedBackward;
                    }
                    else if(gameElement.getDirection() == Direction.left){
                        return archerRedLeft;
                    }
                    else if(gameElement.getDirection() == Direction.right){
                        return archerRedRight;
                    }
                }
            }
            else if(gameEntity instanceof BabyDragon){
                if(gameElement.getOwner().equals(firstPlayer)){
                    if(gameElement.getDirection() == Direction.forward){
                        return babyDragonBlueForward;
                    }
                    else if(gameElement.getDirection() == Direction.backward){
                        return babyDragonBlueBackward;
                    }
                    else if(gameElement.getDirection() == Direction.left){
                        return babyDragonBlueLeft;
                    }
                    else if(gameElement.getDirection() == Direction.right){
                        return babyDragonBlueRight;
                    }
                }
                else{
                    if(gameElement.getDirection() == Direction.forward){
                        return babyDragonRedForward;
                    }
                    else if(gameElement.getDirection() == Direction.backward){
                        return babyDragonRedBackward;
                    }
                    else if(gameElement.getDirection() == Direction.left){
                        return babyDragonRedLeft;
                    }
                    else if(gameElement.getDirection() == Direction.right){
                        return babyDragonRedRight;
                    }
                }
            }
            else if(gameEntity instanceof Barbarian){
                if(gameElement.getOwner().equals(firstPlayer)){
                    if(gameElement.getDirection() == Direction.forward){
                        return barbarianBlueForward;
                    }
                    else if(gameElement.getDirection() == Direction.backward){
                        return barbarianBlueBackward;
                    }
                    else if(gameElement.getDirection() == Direction.left){
                        return barbarianBlueLeft;
                    }
                    else if(gameElement.getDirection() == Direction.right){
                        return barbarianBlueRight;
                    }
                }
                else{
                    if(gameElement.getDirection() == Direction.forward){
                        return barbarianRedForward;
                    }
                    else if(gameElement.getDirection() == Direction.backward){
                        return barbarianRedBackward;
                    }
                    else if(gameElement.getDirection() == Direction.left){
                        return barbarianRedLeft;
                    }
                    else if(gameElement.getDirection() == Direction.right){
                        return barbarianRedRight;
                    }
                }
            }
            else if(gameEntity instanceof Giant){
                if(gameElement.getOwner().equals(firstPlayer)){
                    if(gameElement.getDirection() == Direction.forward){
                        return giantBlueForward;
                    }
                    else if(gameElement.getDirection() == Direction.backward){
                        return giantBlueBackward;
                    }
                    else if(gameElement.getDirection() == Direction.left){
                        return giantBlueLeft;
                    }
                    else if(gameElement.getDirection() == Direction.right){
                        return giantBlueRight;
                    }
                }
                else{
                    if(gameElement.getDirection() == Direction.forward){
                        return giantRedForward;
                    }
                    else if(gameElement.getDirection() == Direction.backward){
                        return giantRedBackward;
                    }
                    else if(gameElement.getDirection() == Direction.left){
                        return giantRedLeft;
                    }
                    else if(gameElement.getDirection() == Direction.right){
                        return giantRedRight;
                    }
                }
            }
            else if(gameEntity instanceof MiniPekka){
                if(gameElement.getOwner().equals(firstPlayer)){
                    if(gameElement.getDirection() == Direction.forward){
                        return miniPekkaBlueForward;
                    }
                    else if(gameElement.getDirection() == Direction.backward){
                        return miniPekkaBlueBackward;
                    }
                    else if(gameElement.getDirection() == Direction.left){
                        return miniPekkaBlueLeft;
                    }
                    else if(gameElement.getDirection() == Direction.right){
                        return miniPekkaBlueRight;
                    }
                }
                else{
                    if(gameElement.getDirection() == Direction.forward){
                        return miniPekkaRedForward;
                    }
                    else if(gameElement.getDirection() == Direction.backward){
                        return miniPekkaRedBackward;
                    }
                    else if(gameElement.getDirection() == Direction.left){
                        return miniPekkaRedLeft;
                    }
                    else if(gameElement.getDirection() == Direction.right){
                        return miniPekkaRedRight;
                    }
                }
            }
            else if(gameEntity instanceof Valkyrie){
                if(gameElement.getOwner().equals(firstPlayer)){
                    if(gameElement.getDirection() == Direction.forward){
                        return valkyrieBlueForward;
                    }
                    else if(gameElement.getDirection() == Direction.backward){
                        return valkyrieBlueBackward;
                    }
                    else if(gameElement.getDirection() == Direction.left){
                        return valkyrieBlueLeft;
                    }
                    else if(gameElement.getDirection() == Direction.right){
                        return valkyrieBlueRight;
                    }
                }
                else{
                    if(gameElement.getDirection() == Direction.forward){
                        return valkyrieRedForward;
                    }
                    else if(gameElement.getDirection() == Direction.backward){
                        return valkyrieRedBackward;
                    }
                    else if(gameElement.getDirection() == Direction.left){
                        return valkyrieRedLeft;
                    }
                    else if(gameElement.getDirection() == Direction.right){
                        return valkyrieRedRight;
                    }
                }
            }
            else if(gameEntity instanceof Wizard){
                if(gameElement.getOwner().equals(firstPlayer)){
                    if(gameElement.getDirection() == Direction.forward){
                        return wizardBlueForward;
                    }
                    else if(gameElement.getDirection() == Direction.backward){
                        return wizardBlueBackward;
                    }
                    else if(gameElement.getDirection() == Direction.left){
                        return wizardBlueLeft;
                    }
                    else if(gameElement.getDirection() == Direction.right){
                        return wizardBlueRight;
                    }
                }
                else{
                    if(gameElement.getDirection() == Direction.forward){
                        return wizardRedForward;
                    }
                    else if(gameElement.getDirection() == Direction.backward){
                        return wizardRedBackward;
                    }
                    else if(gameElement.getDirection() == Direction.left){
                        return wizardRedLeft;
                    }
                    else if(gameElement.getDirection() == Direction.right){
                        return wizardRedRight;
                    }
                }
            }
        }
        else if(gameEntity instanceof Cannon){
            if(gameElement.getOwner().equals(firstPlayer)){
                return cannonBlue;
            }
            else {
                return cannonRed;
            }
        }
        else if(gameEntity instanceof InfernoTower){
            return inferno;
        }
        else if(gameEntity instanceof KingTower){
            if(gameElement.getOwner().equals(firstPlayer)){
                if(((KingTower)gameEntity).isActiveToShoot()){
                    return kingTowerBlueActive;
                }
                else{
                    return kingTowerBlueIdle;
                }
            }
            else{
                if(((KingTower)gameEntity).isActiveToShoot()){
                    return kingTowerRedActive;
                }
                else{
                    return kingTowerRedIdle;
                }
            }
        }
        else if(gameEntity instanceof PrincessTower){
            if(gameElement.getOwner().equals(firstPlayer)){
                return princesTowerBlue;
            }
            else {
                return princesTowerRed;
            }
        }
        else if(gameEntity instanceof Arrows){
            return arrows;
        }
        else if(gameEntity instanceof Fireball){
            return fireBall;
        }
        return null;
    }



}
