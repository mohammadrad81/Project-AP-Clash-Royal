package Controller;

import Model.Cards.Reals.Buildings.Building;
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
import javafx.scene.image.ImageView;

/**
 * a class that holds the images of the game
 */
public class ElementImageViews {
    public static final Image archerBlueForward = new Image("/Pictures/ElementImages/Archer/blue/forward.png");
    public static final Image archerBlueBackward = new Image("/Pictures/ElementImages/Archer/blue/backward.png");
    public static final Image archerBlueLeft = new Image("/Pictures/ElementImages/Archer/blue/left.png" );
    public static final Image archerBlueRight =  (new Image("/Pictures/ElementImages/Archer/blue/right.png" ));
    
    public static final Image archerRedForward =  (new Image("/Pictures/ElementImages/Archer/red/forward.png" ));
    public static final Image archerRedBackward =  (new Image("/Pictures/ElementImages/Archer/red/backward.png" ));
    public static final Image archerRedLeft =  (new Image("/Pictures/ElementImages/Archer/red/left.png" ));
    public static final Image archerRedRight =  (new Image("/Pictures/ElementImages/Archer/red/right.png" ));
    
    public static final Image babyDragonBlueForward =  (new Image("/Pictures/ElementImages/BabyDragon/blue/forward.png" ));
    public static final Image babyDragonBlueBackward =  (new Image("/Pictures/ElementImages/BabyDragon/blue/backward.png" ));
    public static final Image babyDragonBlueLeft =  (new Image("/Pictures/ElementImages/BabyDragon/blue/left.png" ));
    public static final Image babyDragonBlueRight =  (new Image("/Pictures/ElementImages/BabyDragon/blue/right.png" ));
    
    public static final Image babyDragonRedForward =  (new Image("/Pictures/ElementImages/BabyDragon/red/forward.png" ));
    public static final Image babyDragonRedBackward =  (new Image("/Pictures/ElementImages/BabyDragon/red/backward.png" ));
    public static final Image babyDragonRedLeft =  (new Image("/Pictures/ElementImages/BabyDragon/red/left.png" ));
    public static final Image babyDragonRedRight =  (new Image("/Pictures/ElementImages/BabyDragon/red/right.png" ));
    
    public static final Image barbarianBlueForward =  (new Image("/Pictures/ElementImages/Barbarian/blue/forward.png" ));
    public static final Image barbarianBlueBackward =  (new Image("/Pictures/ElementImages/Barbarian/blue/backward.png" ));
    public static final Image barbarianBlueLeft =  (new Image("/Pictures/ElementImages/Barbarian/blue/left.png" ));
    public static final Image barbarianBlueRight =  (new Image("/Pictures/ElementImages/Barbarian/blue/right.png" ));
    
    public static final Image barbarianRedForward =  (new Image("/Pictures/ElementImages/Barbarian/red/forward.png" ));
    public static final Image barbarianRedBackward =  (new Image("/Pictures/ElementImages/Barbarian/red/backward.png" ));
    public static final Image barbarianRedLeft =  ( new Image("/Pictures/ElementImages/Barbarian/red/left.png" ));
    public static final Image barbarianRedRight =  (new Image("/Pictures/ElementImages/Barbarian/red/right.png" ));
    
    public static final Image giantBlueForward =  (new Image("/Pictures/ElementImages/Giant/blue/forward.png" ));
    public static final Image giantBlueBackward =  (new Image("/Pictures/ElementImages/Giant/blue/backward.png" ));
    public static final Image giantBlueLeft =  (new Image("/Pictures/ElementImages/Giant/blue/left.png" ));
    public static final Image giantBlueRight =  (new Image("/Pictures/ElementImages/Giant/blue/right.png" ));

    public static final Image giantRedForward =  (new Image("/Pictures/ElementImages/Giant/red/forward.png" ));
    public static final Image giantRedBackward =  (new Image("/Pictures/ElementImages/Giant/red/backward.png" ));
    public static final Image giantRedLeft =  (new Image("/Pictures/ElementImages/Giant/red/left.png" ));
    public static final Image giantRedRight =  (new Image("/Pictures/ElementImages/Giant/red/right.png" ));
    
    public static final Image cannonBlue =  (new Image("/Pictures/ElementImages/Cannon/blue/cannon.png" ));
    public static final Image cannonRed =  (new Image("/Pictures/ElementImages/Cannon/red/cannon.png" ));
    
    public static final Image inferno =  (new Image("/Pictures/ElementImages/InfernoTower/InfernoTower.png" ));
    
    public static final Image miniPekkaBlueForward =  (new Image("/Pictures/ElementImages/MiniPekka/blue/forward.png" ));
    public static final Image miniPekkaBlueBackward =  (new Image("/Pictures/ElementImages/MiniPekka/blue/backward.png" ));
    public static final Image miniPekkaBlueLeft =  (new Image("/Pictures/ElementImages/MiniPekka/blue/left.png" ));
    public static final Image miniPekkaBlueRight =  (new Image("/Pictures/ElementImages/MiniPekka/blue/right.png" ));

    public static final Image miniPekkaRedForward =  (new Image("/Pictures/ElementImages/MiniPekka/red/forward.png" ));
    public static final Image miniPekkaRedBackward =  (new Image("/Pictures/ElementImages/MiniPekka/red/backward.png" ));
    public static final Image miniPekkaRedLeft =  (new Image("/Pictures/ElementImages/MiniPekka/red/left.png" ));
    public static final Image miniPekkaRedRight =  (new Image("/Pictures/ElementImages/MiniPekka/red/right.png" ));
    
    public static final Image kingTowerBlueActive =  (new Image("/Pictures/ElementImages/Towers/KingTower/blue/active.png" ));
    public static final Image kingTowerBlueIdle =  (new Image("/Pictures/ElementImages/Towers/KingTower/blue/idle.png" ));

    public static final Image kingTowerRedActive =  (new Image("/Pictures/ElementImages/Towers/KingTower/red/active.png" ));
    public static final Image kingTowerRedIdle =  (new Image("/Pictures/ElementImages/Towers/KingTower/red/idle.png" ));

    public static final Image princesTowerBlue =  (new Image("/Pictures/ElementImages/Towers/PrincesTower/blue/PrincesTower.png" ));
    public static final Image princesTowerRed =  (new Image("/Pictures/ElementImages/Towers/PrincesTower/red/PrincesTower.png" ));
    
    public static final Image valkyrieBlueForward =  (new Image("/Pictures/ElementImages/Valkyrie/blue/forward.png" ));
    public static final Image valkyrieBlueBackward  =  (new Image("/Pictures/ElementImages/Valkyrie/blue/backward.png" ));
    public static final Image valkyrieBlueLeft =  (new Image("/Pictures/ElementImages/Valkyrie/blue/left.png" ));
    public static final Image valkyrieBlueRight =  (new Image("/Pictures/ElementImages/Valkyrie/blue/right.png" ));

    public static final Image valkyrieRedForward =  (new Image("/Pictures/ElementImages/Valkyrie/red/forward.png" ));
    public static final Image valkyrieRedBackward  =  (new Image("/Pictures/ElementImages/Valkyrie/red/backward.png" ));
    public static final Image valkyrieRedLeft =  (new Image("/Pictures/ElementImages/Valkyrie/red/left.png" ));
    public static final Image valkyrieRedRight =  (new Image("/Pictures/ElementImages/Valkyrie/red/right.png" ));

    public static final Image wizardBlueForward =  (new Image("/Pictures/ElementImages/Wizard/blue/forward.png" ));
    public static final Image wizardBlueBackward =  (new Image("/Pictures/ElementImages/Wizard/blue/backward.png" ));
    public static final Image wizardBlueLeft =  (new Image("/Pictures/ElementImages/Wizard/blue/left.png" ));
    public static final Image wizardBlueRight =  (new Image("/Pictures/ElementImages/Wizard/blue/right.png" ));

    public static final Image wizardRedForward =  (new Image("/Pictures/ElementImages/Wizard/red/forward.png" ));
    public static final Image wizardRedBackward =  (new Image("/Pictures/ElementImages/Wizard/red/backward.png" ));
    public static final Image wizardRedLeft =  (new Image("/Pictures/ElementImages/Wizard/red/left.png" ));
    public static final Image wizardRedRight =  (new Image("/Pictures/ElementImages/Wizard/red/right.png" ));

    public static final Image arrows =  (new Image("/Pictures/SpellEffects/Arrows.png" ));
    public static final Image fireBall =  (new Image("/Pictures/SpellEffects/Fireball.png" ));
    
    public static final Image[] tiles =
            { (new Image("/Pictures/Tiles/1.png" )),
                     (new Image("/Pictures/Tiles/2.png" )),
                     (new Image("/Pictures/Tiles/3.png" )),
                     (new Image("/Pictures/Tiles/4.png" )),
                     (new Image("/Pictures/Tiles/5.png" )),
                     (new Image("/Pictures/Tiles/6.png" )),
                     (new Image("/Pictures/Tiles/7.png" )),
                     (new Image("/Pictures/Tiles/8.png" ))};

    /**
     *
     * @param gameElement is the element , the client class wants its picture
     * @param firstPlayer is the playing player
     * @return the correct image of the element
     */
    public static Image getElementPicture(GameElement gameElement , Player firstPlayer){
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
