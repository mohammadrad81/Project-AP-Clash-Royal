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
import Model.Interfaces.Damager;
import Model.Towers.Tower;
import Users.Player;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.*;
import java.util.List;

/**
 * class for smart bot of the game
 * @since 7.21.2021
 * @author Alireza Jabbari Mahalle No
 * @author Mohammad Heydari Rad
 * @version 1.0.0
 */
public class SmartBot extends Bot {
    public SmartBot(int level) {
        super("SMART BOT", level);
    }

    public SmartBot(String username, int level) {
        super(username, level);
    }

    @Override
    public Command decision(GameElement[][][] mapArray, List<Card> cards, int elixir) {
        Random random = new Random();
        List<Card> buyAbleCards = buyAbleCards(cards , elixir);
        if(buyAbleCards.size() == 0){
            return null;
        }

        List<GameElement> ownTowers = getOwnTowers();
        List<GameElement> enemyTowers = getEnemyTowers();
        GameElement ownMostHurtTowerElement = getMostHurtTowerElement(ownTowers);
        GameElement enemyMostHurtTowerElement = getMostHurtTowerElement(enemyTowers);
        boolean defensive = random.nextBoolean();
        Point2D chosenLocation = null;

        Card card = getRandom(buyAbleCards);


        if(card instanceof Building){
            Building building = (Building) card;

                chosenLocation = searchForAllowedArea(building , new Point(9 , 7));

            if(chosenLocation != null){
                return new Command(this , building , chosenLocation);
            }
        }
        else if(card instanceof Spell){
            Spell spell = (Spell) card;
            if(spell instanceof Rage){
//                List<GameElement> underAttackEnemyTowers = underAttackOnes(enemyTowers);
//                if(underAttackEnemyTowers.size() > 0){
//                    GameElement towerElement = getRandom(underAttackEnemyTowers);
//                    if(towerElement != null){
//                        chosenLocation = towerElement.getLocation();
//                        if(chosenLocation != null){
//                            return new Command(this , spell , chosenLocation);
//                        }
//                    }
//                }

                Point2D chosenPoint = findArmyMass(mapArray , ((Rage)spell).getRadius() , true);
                if(chosenPoint != null){
                    return new Command(this , spell , chosenPoint);
                }
            }

            else if(spell instanceof Fireball || spell instanceof Arrows){
                    return new Command(this , spell , enemyMostHurtTowerElement.getLocation());
            }

        }
        if(card instanceof Troop){
            Troop troop = (Troop) card;
            if(defensive){
                chosenLocation = searchForAllowedArea(troop , ownMostHurtTowerElement.getLocation());
            }
            else{
                chosenLocation = searchForAllowedArea(troop , enemyMostHurtTowerElement.getLocation());
            }
            if(chosenLocation != null){
                return new Command(this , troop , chosenLocation);
            }
        }
        return null;
    }

    /**
     * @param cards is the list of cards , the game gives to the bot
     * @param elixir the amount of elixir the bot can spend
     * @return a list of cards , the bot can buy and use for game ( could be empty )
     */
    protected List<Card> buyAbleCards(List<Card> cards , int elixir){
        List<Card> buyAbleCards = new ArrayList<>();
        for(Card card : cards){
            if(card.getCost() <= elixir){
                buyAbleCards.add(card);
            }
        }
        return buyAbleCards;
    }

    /**
     *
     * @param cards is the list of cards
     * @return a list of the troop cards of the card list
     */
    protected List<Troop> troopCards(List<Card> cards){
        List<Troop> troopCards = new ArrayList<>();
        for(Card card : cards){
            if(card instanceof Troop){
                troopCards.add((Troop) card);
            }
        }
        return troopCards;
    }

    /**
     *
     * @param cards is the list of cards
     * @return a list of the building cards of the card list
     */
    protected List<Building> buildingCards(List<Card> cards){
        List<Building> buildingCards = new ArrayList<>();
        for(Card card : cards ){
            if(card instanceof Building){
                buildingCards.add((Building) card);
            }
        }
        return buildingCards;
    }

    /**
     *
     * @param cards is the list of cards
     * @return a list of the spell cards of the card list
     */
    protected List<Spell> spellCards(List<Card> cards){
        List<Spell> spellCards = new ArrayList<>();
        for(Card card : cards){
            if(card instanceof Spell){
                spellCards.add((Spell)card);
            }
        }
        return spellCards;
    }

    /**
     *
     * @return a list of the towers of this bot
     */
    protected List<GameElement> getOwnTowers(){
        GameManager gameManager = GameManager.getInstance();
        List<GameElement> ownTowers = new ArrayList<>();
        Iterator<GameElement> iterator = gameManager.getPlayerToElementHashMap().get(this).iterator();
        GameElement gameElement = null;
        while (iterator.hasNext()){
            gameElement = iterator.next();
            if (gameElement.getGameEntity() instanceof Tower){
                if(gameElement.getOwner().equals(this)){
                    ownTowers.add(gameElement);
                }
            }
        }
        return ownTowers;
    }

    /**
     *
     * @return a list of the towers of the enemy of this bot ( the player )
     */
    protected List<GameElement> getEnemyTowers(){
        GameManager gameManager = GameManager.getInstance();
        List<GameElement> enemyTowers = new ArrayList<>();
        Set<Player> keySet = gameManager.getPlayerToElementHashMap().keySet();
        Iterator<Player> iterator = keySet.iterator();
        while (iterator.hasNext()){
            Player player = iterator.next();
            if(! player.equals(this)){
                Iterator<GameElement> elementIterator = gameManager.getPlayerToElementHashMap().get(player).iterator();
                while (elementIterator.hasNext()){
                    GameElement gameElement = elementIterator.next();
                    if (gameElement.getGameEntity() instanceof Tower){
                        enemyTowers.add(gameElement);
                    }
                }
            }
        }
        return enemyTowers;
    }

    /**
     * checks if a game element is under attack or not
     * @param gameElement is the game element , is about to check
     * @return true if the game element is under attack , else , false
     */
    protected boolean isUnderAttack(GameElement gameElement){
        GameManager gameManager = GameManager.getInstance();
        HashMap<GameElement , GameElement> elementTarget =gameManager.getElementToTargetHashMap();
        Set<GameElement> keySet = elementTarget.keySet();

        Iterator<GameElement> iterator = keySet.iterator();
        while (iterator.hasNext()){
            GameElement element = iterator.next();
            if(elementTarget.get(element).equals(gameElement)){
                if(((Damager)element.getGameEntity()).getRange() >=
                        element.getLocation().distance(gameElement.getLocation())){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     *
     * @param towers is a list of towers
     * @return the tower that is hurt the most
     */
    protected GameElement getMostHurtTowerElement(List<GameElement> towers){
        Iterator<GameElement> it = towers.iterator();
        GameElement mostHurtTowerElement = null;
        Tower tower = null;
        GameElement towerElement = null;
        int maxHurt = 0;
        while (it.hasNext()){
            towerElement = it.next();
            tower = (Tower) towerElement.getGameEntity();
            if(tower.getHealth() - towerElement.getHealth() >= maxHurt){
                mostHurtTowerElement = towerElement;
                maxHurt = tower.getHealth() - towerElement.getHealth();
            }
        }
        return mostHurtTowerElement;
    }

    /**
     *
     * @param card is the card , the bot is about to play
     * @param point2D is the point that the bot wants to play the card near it ( not necessarily in that point )
     * @return a point that is allowed for card to be played
     */
    protected Point2D searchForAllowedArea(Card card , Point2D point2D){
        int x = (int) point2D.getX();
        int y = (int) point2D.getY();
        GameManager gameManager = GameManager.getInstance();
        Point2D upPoint = null;
        Point2D downPoint = null;
        for(int radius = 0; radius <= 33; radius++){
            for(int i = -radius ; i <= radius; i++){
                int j = radius - Math.abs(i);
                upPoint =  new Point(x + i , y + j);
                downPoint = new Point(x + i , y - j);
                if(gameManager.isCommandAreaAllowed(new Command(this , card ,upPoint))){
                    return upPoint;
                }

                else if(gameManager.isCommandAreaAllowed(new Command(this , card , downPoint))){
                    return downPoint;
                }
            }
        }
        return null;
    }

    /**
     *
     * @param list is a list of some things ( can be anything )
     * @param <T> is the generic type of the objects in the list
     * @return a random one from the list
     */
    protected  <T> T getRandom (List<T> list){
        Random random = new Random();
        return list.get(random.nextInt(list.size()));
    }

    /**
     *
     * @param elements is a list of elements
     * @return a list of elements that are under attack
     */
    protected List<GameElement> underAttackOnes(List<GameElement> elements){
        Iterator<GameElement> iterator = elements.iterator();
        List<GameElement> underAttackOnes = new ArrayList<>();
        GameElement gameElement = null;
        while (iterator.hasNext()){
            gameElement = iterator.next();
            if(isUnderAttack(gameElement)){
                underAttackOnes.add(gameElement);
            }
        }
        return underAttackOnes;
    }

    /**
     * army mass is a point of map that there are more than 2 troops of a side is in it
     * @param mapArray is the map of the game
     * @param radius is the radius of the searching
     * @param forThisPlayer used to determine to search for a mass of the bot or the player ( true if for the bot )
     * @return a point that is an army mass ( could be null )
     */
    protected Point2D findArmyMass(GameElement[][][] mapArray , double radius , boolean forThisPlayer){
        int mass = 0;
        int count = 0;
        Point2D massLocation = null;
        for (int x = 0; x < 19; x++) {
            for (int y = 0; y < 33; y++) {
                count = howManyInRadius(mapArray , x , y , radius , forThisPlayer);
                if(count >= 3){
                    if(count > mass){
                        mass = count;
                        massLocation = new Point(x , y);
                    }
                }
            }
        }
        return massLocation;
    }

    /**
     * counts the elements of a side , in a radius from a point
     * @param mapArray is the map of the game
     * @param x is the x of the point
     * @param y is the y of the point
     * @param radius is the radius
     * @param forThisPlayer used to determine to count of elements of this bot or the player ( true if for this bot )
     * @return the count of the elements of the determined side in the radius from the point
     */
    private int howManyInRadius(GameElement[][][] mapArray , int x , int y , double radius, boolean forThisPlayer){
        GameElement gameElement = null;
        Point2D location = new Point(x , y);
        int count = 0;
        for (int i = 0; i < 19; i++) {
            for (int j = 0; j < 33; j++) {
                for (int k = 0; k < 2; k++) {
                    gameElement = mapArray[i][j][k];
                    if(gameElement != null && !(gameElement instanceof Block)){
                        if(gameElement.getLocation().distance(location) <= radius){
                            if(forThisPlayer){
                                if(gameElement.getOwner().equals(this)){
                                    count++;
                                }
                            }
                            else{
                                if(! gameElement.getOwner().equals(this)){
                                    count++;
                                }
                            }
                        }

                    }
                }
            }
        }
        return count;
    }
}
