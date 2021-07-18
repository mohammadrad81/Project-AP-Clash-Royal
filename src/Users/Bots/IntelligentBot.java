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

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.List;

public class IntelligentBot extends SmartBot {

    public IntelligentBot(int level) {
        super("Intelligent bot" , level);
    }

    @Override
    public Command decision(GameElement[][][] mapArray, List<Card> cards, int elixir) {
        List<Card> buyableCards = buyAbleCards(cards , elixir);
        List<Spell> spellCards = spellCards(buyableCards);
        List<Troop> troopCards = troopCards(buyableCards);
        List<Building> buildingCards = buildingCards(buyableCards);

        Card chosenCard = findFireBall(buyableCards);
        if(chosenCard != null){
            Point2D enemyArmyMass = findArmyMass(mapArray , ((Fireball)chosenCard).getRadius() , false);
            if(enemyArmyMass != null){
                return new Command(this , chosenCard , enemyArmyMass);
            }
        }
        chosenCard = findArrows(buyableCards);
        if(chosenCard != null){
            Point2D enemyArmyMass = findArmyMass(mapArray , ((Arrows)chosenCard).getRadius() , false);
            if(enemyArmyMass != null){
                return new Command(this , chosenCard , enemyArmyMass);
            }
        }
        // implement later
        return null;// just in case
    }

    protected Card findFireBall(List<Card> buyableCards){
        for (Card card : buyableCards){
            if (card instanceof Fireball){
                return card;
            }
        }
        return null;
    }

    protected Card findArrows (List<Card> buyableCards){
        for (Card card : buyableCards){
            if(card instanceof Arrows){
                return card;
            }
        }
        return null;
    }

    protected Card findRage(List<Card> buyableCards){
        for(Card card : buyableCards){
            if(card instanceof Rage){
                return card;
            }
        }
        return null;
    }

    protected Card findBuilding(List<Card> buyableCards){
        for(Card card : buyableCards){
            if(card instanceof Building){
                return card;
            }
        }
        return null;
    }

    protected Card findTroop(List<Card> buyableCards){
        for(Card card : buyableCards){
            if(card instanceof Troop){
                return card;
            }
        }
        return null;
    }

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
