package Users.Bots;

import Model.Cards.Card;
import Model.Cards.Reals.Buildings.Building;
import Model.Cards.Reals.Troops.Troop;
import Model.Cards.Spells.Arrows;
import Model.Cards.Spells.Fireball;
import Model.Cards.Spells.Rage;
import Model.Cards.Spells.Spell;
import Model.Game.Block;
import Model.Game.Command;
import Model.Game.GameElement;
import Model.Game.GameManager;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.List;

/**
 * class for intelligent bot of the game ( very smart )
 * @since 7.21.2021
 * @author Alireza Jabbari Mahalle No
 * @author Mohammad Heydari Rad
 * @version 1.0.0
 */
public class IntelligentBot extends SmartBot {
    /**
     * constructor for the bot
     * @param level is the level of the bot
     */
    public  IntelligentBot(int level) {
        super("Intelligent bot" , level);
    }

    /**
     * the intelligent decision of the bot
     * @param mapArray is the map of the game
     * @param cards is the cards the bot owns
     * @param elixir is the amount of elixir the bot can spend
     * @return the command as the decision of the bot
     */
    @Override
    public Command decision(GameElement[][][] mapArray, List<Card> cards, int elixir) {
        List<Card> buyableCards = buyAbleCards(cards , elixir);
        List<Spell> spellCards = spellCards(buyableCards);
        List<Troop> troopCards = troopCards(buyableCards);
        List<Building> buildingCards = buildingCards(buyableCards);
        boolean defensive = shouldBeDefensive();
        Card chosenCard = findFireBall(buyableCards);
        if(chosenCard != null){
            Point2D enemyArmyMass = findArmyMass(mapArray , ((Fireball)chosenCard).getRadius() , false);
            if(enemyArmyMass != null){
                return new Command(this , chosenCard , enemyArmyMass);
            }
            Point2D mostHurtEnemyTowerLocation = (getMostHurtTowerElement(getEnemyTowers()).getLocation());
            if(mostHurtEnemyTowerLocation != null){
                return new Command(this , chosenCard , mostHurtEnemyTowerLocation);
            }
        }
        chosenCard = findArrows(buyableCards);
        if(chosenCard != null){
            Point2D enemyArmyMass = findArmyMass(mapArray , ((Arrows)chosenCard).getRadius() , false);
            if(enemyArmyMass != null){
                return new Command(this , chosenCard , enemyArmyMass);
            }
            Point2D mostHurtEnemyTowerLocation = (getMostHurtTowerElement(getEnemyTowers()).getLocation());
            if(mostHurtEnemyTowerLocation != null){
                return new Command(this , chosenCard , mostHurtEnemyTowerLocation);
            }
        }
        chosenCard = findRage(buyableCards);
        if(chosenCard != null){
            Point2D botArmyMass = findArmyMass(mapArray , ((Rage)chosenCard).getRadius() , true);
            if(botArmyMass != null){
                return new Command(this , chosenCard , botArmyMass);
            }
        }

        chosenCard = findBuilding(buyableCards);
        if(chosenCard != null){
            if(defensive){
                Point2D chosenPoint = searchForAllowedArea(chosenCard , new Point(9 , 4));
                if(chosenPoint != null){
                    return new Command(this , chosenCard , chosenPoint);
                }
            }
            else {
                Point2D chosenPoint = searchForAllowedArea(chosenCard , new Point(9 , 24));
                if(chosenPoint != null){
                    return new Command(this , chosenCard , chosenPoint);
                }
            }
        }
        chosenCard = findTroop(buyableCards);
        if(chosenCard != null){
            if(defensive){
                Point2D chosenPoint = searchForAllowedArea(chosenCard , new Point(9 , 4));
                if(chosenPoint != null){
                    return new Command(this , chosenCard , chosenPoint);
                }
            }
            else{
                Point2D chosenPoint = searchForAllowedArea(chosenCard , new Point(9 , 24));
                if(chosenPoint != null){
                    return new Command(this , chosenCard , chosenPoint);
                }
            }
        }
        return null;// just in case
    }

    /**
     * finds fireball card in the input card list
     * @param buyableCards is the list of cards , the bot can buy
     * @return the fireball card in this list , ( can be null )
     */
    protected Card findFireBall(List<Card> buyableCards){
        for (Card card : buyableCards){
            if (card instanceof Fireball){
                return card;
            }
        }
        return null;
    }

    /**
     * finds arrows card in the input card list
     * @param buyableCards is the list of cards , the bot can buy
     * @return the arrows card in this list , ( can be null )
     */
    protected Card findArrows (List<Card> buyableCards){
        for (Card card : buyableCards){
            if(card instanceof Arrows){
                return card;
            }
        }
        return null;
    }
    /**
     * finds rage card in the input card list
     * @param buyableCards is the list of cards , the bot can buy
     * @return the rage card in this list , ( can be null )
     */
    protected Card findRage(List<Card> buyableCards){
        for(Card card : buyableCards){
            if(card instanceof Rage){
                return card;
            }
        }
        return null;
    }
    /**
     * finds building card in the input card list
     * @param buyableCards is the list of cards , the bot can buy
     * @return the building card in this list , ( can be null )
     */
    protected Card findBuilding(List<Card> buyableCards){
        for(Card card : buyableCards){
            if(card instanceof Building){
                return card;
            }
        }
        return null;
    }
    /**
     * finds troop card in the input card list
     * @param buyableCards is the list of cards , the bot can buy
     * @return the troop card in this list , ( can be null )
     */
    protected Card findTroop(List<Card> buyableCards){
        for(Card card : buyableCards){
            if(card instanceof Troop){
                return card;
            }
        }
        return null;
    }

    /**
     * checks if the bot should play defensive or not
     * @return true if should play defensive , else false
     */
    private boolean shouldBeDefensive(){
        GameManager gameManager = GameManager.getInstance();
        if(gameManager.getFirstPlayerCrown() > gameManager.getSecondPlayerCrown()){
            return true;
        }
        if(howManyEnemyInBotField(gameManager.getMapArray()) >= 2){
            return true;
        }
        if(isKingTowerInDanger()){
            return true;
        }
        return false;

    }

    /**
     * counts the enemy elements
     * @param mapArray
     * @return
     */
    private int howManyEnemyInBotField(GameElement[][][] mapArray){
        GameElement gameElement = null;
        int count = 0;
        for (int i = 0; i < 19; i++) {
            for (int j = 0; j < 16; j++) {
                for (int k = 0; k < 2; k++) {
                    gameElement = mapArray[i][j][k];
                    if(gameElement != null && !(gameElement instanceof Block)){
                        if(! gameElement.getOwner().equals(this)){
                            count++;
                        }
                    }
                }
            }
        }
        return count;
    }

    /**
     * checks if the king tower of the bot is under attack
     * @return true if it is under attack , else false
     */
    private boolean isKingTowerInDanger(){
        GameManager gameManager = GameManager.getInstance();
        GameElement kingTowerElement = gameManager.getMapArray()[9][1][0];
        if(isUnderAttack(kingTowerElement)){
            return true;
        }
        return false;
    }
}
