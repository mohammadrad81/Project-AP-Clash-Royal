package Users.Bots;

import Model.Cards.Card;
import Model.Cards.Reals.Buildings.Building;
import Model.Cards.Reals.Troops.Troop;
import Model.Cards.Spells.Arrows;
import Model.Cards.Spells.Fireball;
import Model.Cards.Spells.Rage;
import Model.Cards.Spells.Spell;
import Model.Game.Command;
import Model.Game.GameElement;
import Model.Game.GameManager;
import Model.Towers.Tower;
import Users.Player;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.*;
import java.util.List;

public class SmartBot extends Bot {
    protected SmartBot(String username, int level) {
        super(username, level);
    }

    @Override
    public Command decision(GameElement[][][] mapArray, List<Card> cards, int elixir) {
        Random random = new Random();
        GameManager gameManager = GameManager.getInstance();
        Card selectedCard = null;
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
            if(defensive){
                chosenLocation = searchForAllowedArea(building , ownMostHurtTowerElement.getLocation());
            }
            else {
                chosenLocation = searchForAllowedArea(building , enemyMostHurtTowerElement.getLocation());
            }
            if(chosenLocation != null){
                return new Command(this , building , chosenLocation);
            }
        }
        else if(card instanceof Spell){
            Spell spell = (Spell) card;
            if(spell instanceof Rage){
                List<GameElement> underAttackEnemyTowers = underAttackOnes(enemyTowers);
                if(underAttackEnemyTowers.size() > 0){
                    GameElement towerElement = getRandom(underAttackEnemyTowers);
                    if(towerElement != null){
                        chosenLocation = towerElement.getLocation();
                        if(chosenLocation != null){
                            return new Command(this , spell , chosenLocation);
                        }
                    }
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

    protected List<Card> buyAbleCards(List<Card> cards , int elixir){
        List<Card> buyAbleCards = new ArrayList<>();
        for(Card card : cards){
            if(card.getCost() <= elixir){
                buyAbleCards.add(card);
            }
        }
        return buyAbleCards;
    }

    protected List<Troop> troopCards(List<Card> cards){
        List<Troop> troopCards = new ArrayList<>();
        for(Card card : cards){
            if(card instanceof Troop){
                troopCards.add((Troop) card);
            }
        }
        return troopCards;
    }

    protected List<Building> buildingCards(List<Card> cards){
        List<Building> buildingCards = new ArrayList<>();
        for(Card card : cards ){
            if(card instanceof Building){
                buildingCards.add((Building) card);
            }
        }
        return buildingCards;
    }

    protected List<Spell> spellCards(List<Card> cards){
        List<Spell> spellCards = new ArrayList<>();
        for(Card card : cards){
            if(card instanceof Spell){
                spellCards.add((Spell)card);
            }
        }
        return spellCards;
    }

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

    protected boolean isUnderAttack(GameElement gameElement){
        GameManager gameManager = GameManager.getInstance();
        HashMap<GameElement , GameElement> elementTarget =gameManager.getElementToTargetHashMap();
        Set<GameElement> keySet = elementTarget.keySet();

        Iterator<GameElement> iterator = keySet.iterator();
        while (iterator.hasNext()){
            GameElement element = iterator.next();
            if(elementTarget.get(element).equals(gameElement)){
                return true;
            }
        }
        return false;
    }

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

                else if(gameManager.isCommandAreaAllowed(new Command(this , card , upPoint))){
                    return downPoint;
                }
            }
        }
        return null;
    }

    protected  <T> T getRandom (List<T> list){
        Random random = new Random();
        return list.get(random.nextInt(list.size()));
    }

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
}
