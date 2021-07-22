package Model.Game;

import Model.Cards.Card;
import Model.Cards.Reals.Buildings.Building;
import Model.Cards.Reals.Buildings.InfernoTower;
import Model.Cards.Reals.Real;
import Model.Cards.Reals.Troops.BabyDragon;
import Model.Cards.Reals.Troops.Giant;
import Model.Cards.Reals.Troops.Troop;
import Model.Cards.Reals.Type;
import Model.Cards.Spells.Arrows;
import Model.Cards.Spells.Fireball;
import Model.Cards.Spells.Rage;
import Model.Cards.Spells.Spell;
import Model.GameEntity;
import Model.Interfaces.AirWarrior;
import Model.Interfaces.Damager;
import Model.Interfaces.GroundWarrior;
import Model.Interfaces.HealthHaver;
import Model.Property;
import Model.Stats.Match;
import Model.Towers.KingTower;
import Model.Towers.PrincessTower;
import Model.Towers.Tower;
import Users.Bots.Bot;
import Users.Player;

import java.awt.*;

import java.awt.geom.Point2D;

import java.io.Serializable;
import java.util.*;
import java.util.List;

/**
 * the class that manages the game
 * @since 7.8.2021
 * @author Alireza Jabbari Mahalle No
 * @author Mohammad Heydari Rad
 * @version 1.0.0
 */
public class GameManager implements Serializable {
//    private static final String team1Name = "team 1";
//    private static final String team2Name = "team 2";
    private static GameManager gameManager;
    private Player firstPlayer;
    private Player secondPlayer;

    private HashMap<Player , Integer> playerElixir;

    private int firstPlayerCrown;
    private int secondPlayerCrown;

    private HashMap<Player , List<GameElement>> playerToElementHashMap;
    private GameElement[][][] mapArray;
    private long frameCounter;
    private final int fps = 10;
    private HashMap<Player , List<Card>> playerRandomCardsHashMap;
    private HashMap<GameElement , GameElement> elementToTargetHashMap;
    private List<GameElement> activeSpells;
    private List<Shoot> shoots;
    private List<Command> commands;
    /**
     * static method to get the last game manager made
     * @return
     */
    public static GameManager getInstance(){
        return gameManager;
    }

    /**
     * constructor for the game manager
     * @param firstPlayer is the first player that plays
     * @param secondPlayer is the second player that plays
     */
    public GameManager(Player firstPlayer, Player secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;

        this.playerRandomCardsHashMap = new HashMap<>();
        this.playerRandomCardsHashMap.put(firstPlayer, new ArrayList<>());
        this.playerRandomCardsHashMap.put(secondPlayer, new ArrayList<>());

        this.playerElixir = new HashMap<>();
        this.commands = new ArrayList<>();

        this.activeSpells = new ArrayList<>();
        this.playerToElementHashMap = new HashMap<>();
        this.playerToElementHashMap.put(firstPlayer, new ArrayList<>());
        this.playerToElementHashMap.put(secondPlayer, new ArrayList<>());
        this.mapArray = new GameElement[19][33][2];
        this.elementToTargetHashMap = new HashMap<>();
        this.shoots = new ArrayList<>();
        gameManager = this;
        init();
    }

    /**
     * initializes the game
     */
    private void init(){
        givePlayerTowers(firstPlayer);
        givePlayerTowers(secondPlayer);
        giveRandomCardToPlayer(firstPlayer, 5);
        giveRandomCardToPlayer(secondPlayer, 5);
        playerElixir.put(firstPlayer , 4);
        playerElixir.put(secondPlayer , 4);
        putBlocks();

    }

    /**
     * puts the blocks in the map
     */
    private void putBlocks(){

        for(int i = 0 ; i < 19; i ++){
            if(i == 3 || i == 15){
                continue;
            }
            mapArray[i][16][0] = new Block(new Point(i , 16));
        }
    }

    /**
     * increases the count of crowns of the first player
     */
    private void increaseFirstPlayerCrown(){
        firstPlayerCrown++;
    }//done

    /**
     * increases the count of crowns of the second player
     */
    private void increaseSecondPlayerCrown(){
        secondPlayerCrown++;
    }//done

    /**
     * adds an element to the game
     * @param player is the player who owns the element
     * @param gameElement is the element , we are about to add
     */
    private void addElement(Player player , GameElement gameElement ){
        playerToElementHashMap.get(player).add(gameElement);
        if(gameElement.getGameEntity() instanceof Spell){
            activeSpells.add(gameElement);
        }
        else if(gameElement.getGameEntity() instanceof Building){
            placeElement(gameElement);
        }
        else if(gameElement.getGameEntity() instanceof Troop){
            Troop troop = (Troop) gameElement.getGameEntity();
            int count = troop.getCount();
            placeElement(gameElement , count);
        }
    } //done

    /**
     * places the element in the map
     * @param gameElement is the element we are about to place
     */
    private void placeElement(GameElement gameElement){
        placeElement(gameElement , gameElement.getLocation());
    }//done

    /**
     * places a count of same type of elements in the map ( the troops that have counts )
     * @param gameElement is the game element is about to get placed
     * @param count is the count of the game element ( for instance , barbarian has 4 count )
     */
    private void placeElement(GameElement gameElement , int count){
        Point2D[] point2DS = new Point2D[count];
        int x = (int) gameElement.getLocation().getX();
        int y = (int) gameElement.getLocation().getY();
        Point2D point = gameElement.getLocation();
        for(int i = 0; i < count ; i ++){
            point2DS[i] = new Point(x + i , y);
            placeElement(gameElement , point2DS[i]);
        }
    }//done

    /**
     * places an element in a particular point
     * @param gameElement is the element is about to get placed
     * @param point2D is the point , the game element is about to get placed
     */
    private void placeElement(GameElement gameElement , Point2D point2D){
        gameElement = gameElement.copy();
        gameElement.setLocation(point2D);

        int x = (int) gameElement.getLocation().getX();
        int y = (int) gameElement.getLocation().getY();
        if(gameElement.getGameEntity() instanceof BabyDragon){
            mapArray[x][y][1] = gameElement;
        }
        else {
            mapArray[x][y][0] = gameElement;
        }
    }//done

    /**
     * removes an element from the game
     * @param gameElement is the removing game element
     */
    private void removeElement(GameElement gameElement){
        removeElement(gameElement.getOwner() , gameElement);
    } // done

    /**
     * removes an element from the owned elements by a player
     * @param player is the player who owns the element
     * @param gameElement is the element is about to get removed
     */
    private void removeElement(Player player , GameElement gameElement){
        List<GameElement> playerElements = playerToElementHashMap.get(player);
        playerElements.remove(gameElement);
        if(!(gameElement.getGameEntity() instanceof Spell)){
            removeFromTargetMap(gameElement);
            if(gameElement.getGameEntity() instanceof BabyDragon){
                mapArray[(int) gameElement.getLocation().getX()]
                        [(int) gameElement.getLocation().getY()]
                        [1] = null;
            }
            else{
                mapArray[(int) gameElement.getLocation().getX()]
                        [(int) gameElement.getLocation().getY()]
                        [0] = null;
            }

            if(gameElement.getGameEntity() instanceof Tower){
                if(player.equals(firstPlayer)){
                    activeKingTower(firstPlayer);
                    if(gameElement.getGameEntity() instanceof KingTower){
                        secondPlayerCrown = 3;
                    }
                    else {
                        increaseSecondPlayerCrown();
                    }
                }
                else {
                    activeKingTower(secondPlayer);
                    if(gameElement.getGameEntity() instanceof KingTower){
                        firstPlayerCrown = 3;
                    }
                    else {
                        increaseFirstPlayerCrown();
                    }

                }
            }
        }

//        if(gameElement.getGameEntity() instanceof Spell){
////            activeSpells.remove(gameElement);
//        }
//        System.gc();
//        Runtime.getRuntime().gc();
    } //done

    /**
     * removes a dead element from the target hash map
     * @param deadElement is the element that its health is 0
     */
    private void removeFromTargetMap(GameElement deadElement){
        Iterator<GameElement> it = elementToTargetHashMap.keySet().iterator();
        GameElement gameElement = null;
        GameElement targetElement = null;
        while(it.hasNext()){
            gameElement = it.next();
            targetElement = elementToTargetHashMap.get(gameElement);
            if(targetElement.equals(deadElement)){
                it.remove();
            }
        }
    }

    /**
     * the player buys a card and plays it
     * @param player is the player who plays the card
     * @param card is the played card
     * @param point2D is the point to play the card
     */
    private void buyCard(Player player , Card card , Point2D point2D) {
        Command command = new Command(player, card, point2D);
        int x = (int) point2D.getX();
        int y = (int) point2D.getY();
//        if (card instanceof Troop) {
//            if (!isAreaAllowed(command, ((Troop) card).getCount())) {
//                return; // or throw exception
//            }
//        } else if (!isAreaAllowed(command)) {
//            return; // or throw exception
//        }
        if(! isCommandAreaAllowed(command)){
            return;
        }

        if (playerElixir.get(player) >= card.getCost()) {
            playerElixir.put(player, playerElixir.get(player) - card.getCost());


            GameElement gameElement = null;
            playerRandomCardsHashMap.get(player).remove(card);
            Direction direction = null;
            if (player.equals(secondPlayer)) {
                direction = Direction.backward;
            } else if (player.equals(firstPlayer)) {
                direction = Direction.forward;
            }

                    gameElement = new GameElement(card , new Point(x, y), player, frameCounter, direction );
                    addElement(player , gameElement);

            giveRandomCardToPlayer(player);
        }
    } // done

    /**
     * gives random cards to player
     * from his/her/its hand
     * @param player is the player we are about to give cards to
     * @param count is the count of cards to give to player
     */
    private void giveRandomCardToPlayer(Player player , int count){
        for(int i = 0; i < count; i++){
            giveRandomCardToPlayer(player);
        }
    } // done

    /**
     * gives a random card
     * from his/her/its hand
     * @param player is the player to give a random card to
     */
    private void giveRandomCardToPlayer(Player player){
        boolean done = false;
        List<Card> hand = player.getHand();
        Random random = new Random();
        Card randomCard = null;
        while (!done){
            randomCard = hand.get(random.nextInt(hand.size()));
            if(! playerRandomCardsHashMap.get(player).contains(randomCard)){
                playerRandomCardsHashMap.get(player).add(randomCard);
                done = true;
            }
        }
    } // done

    /**
     * gives the player their towers
     * @param player is the player this method gives towers to
     */
    private void givePlayerTowers(Player player){
        List<GameElement> gameElements = playerToElementHashMap.get(player);
        GameElement kingTowerElement = null;
        GameElement leftPrincessTower = null;
        GameElement rightPrincessTower = null;
        if(player.equals(firstPlayer)){
            kingTowerElement =
                    new GameElement(new KingTower(player.getLevel()) ,
                            new Point(9 , 31),
                            player , frameCounter ,
                            Direction.forward);
            mapArray[9][31][0] = kingTowerElement;
            leftPrincessTower =
                    new GameElement(new PrincessTower(player.getLevel()) ,
                            new Point(3, 28),
                            player,
                            frameCounter,
                            Direction.forward);
            mapArray[3][28][0] = leftPrincessTower;
            rightPrincessTower =
                    new GameElement(new PrincessTower(player.getLevel()),
                            new Point(15, 28),
                            player,
                            frameCounter,
                            Direction.forward);
            mapArray[15][28][0] = rightPrincessTower;
        }
        else {
            kingTowerElement =
                    new GameElement(new KingTower(player.getLevel()),
                            new Point(9, 1),
                            player,
                            frameCounter,
                            Direction.backward);
            mapArray[9][1][0] = kingTowerElement;
            rightPrincessTower =
                    new GameElement(new PrincessTower(player.getLevel()),
                            new Point(3, 4),
                            player,
                            frameCounter,
                            Direction.backward);
            mapArray[3][4][0] = rightPrincessTower;
            leftPrincessTower =
                    new GameElement(new PrincessTower(player.getLevel()),
                            new Point(15, 4),
                            player,
                            frameCounter,
                            Direction.backward);
            mapArray[15][4][0] = leftPrincessTower;
        }
        gameElements.add(kingTowerElement);
        gameElements.add(leftPrincessTower);
        gameElements.add(rightPrincessTower);
    } // done

    /**
     * spells the area
     * @param spellElement is the game element of the spell
     */
    private void spellArea(GameElement spellElement){
//        activeSpells.add(spellElement);
        if(spellElement.getGameEntity() instanceof Rage){
            rageArea(spellElement);
        }
        else if(spellElement.getGameEntity() instanceof Arrows){
            arrowArea(spellElement);
        }
        else if(spellElement.getGameEntity() instanceof Fireball){
            fireBallArea(spellElement);
        }
    } // done

    /**
     * damages the elements in the range of the arrow spell ( no self damage )
     * @param arrowElement is the game element of the arrow spell
     */
    private void arrowArea(GameElement arrowElement){
        Arrows arrows = (Arrows) arrowElement.getGameEntity();
        Point2D arrowPoint = arrowElement.getLocation();
        Point2D gameElementLocation = null;
        for(int i = 0 ; i < 19; i++){
            for(int j = 0; j < 33; j ++){
                for(int k = 0; k < 2; k++){
                    gameElementLocation = new Point(i , j);
                    if(mapArray[i][j][k] != null && !(mapArray[i][j][k] instanceof Block)){
                        if(mapArray[i][j][k].getOwner() != arrowElement.getOwner()){
                            if(arrowPoint.distance(gameElementLocation) <= arrows.getRadius()){
                                mapArray[i][j][k].hurt(arrows.getAreaDamage());
                            }
                        }
                    }
                }
            }
        }
    } // done

    /**
     * damages the elements in the range of the arrow spell ( no self damage )
     * @param fireBallElement
     */
    private void fireBallArea(GameElement fireBallElement){
        Fireball fireball = (Fireball) fireBallElement.getGameEntity();
        Point2D fireballPoint = fireBallElement.getLocation();
        Point2D gameElementLocation = null;

        for(int i = 0 ; i < 19; i++){
            for(int j = 0; j < 33; j ++){
                for(int k = 0; k < 2; k++){
                    gameElementLocation = new Point(i , j);
                    if(mapArray[i][j][k] != null && !(mapArray[i][j][k] instanceof Block)){
                        if(mapArray[i][j][k].getOwner() != fireBallElement.getOwner()){
                            if(fireballPoint.distance(gameElementLocation) <= fireball.getRadius()){
                                mapArray[i][j][k].hurt(fireball.getAreaDamage());
                            }
                        }
                    }
                }
            }
        }
    } // done

    /**
     * rages the elements in the range of the rage spell
     * @param rageElement
     */
    private void rageArea(GameElement rageElement){
        Rage rage = (Rage) rageElement.getGameEntity();
        Point2D ragePoint = rageElement.getLocation();
        Point2D gameElementLocation = null;

        for(int i = 0 ; i < 19; i++){
            for(int j = 0; j < 33; j ++){
                for(int k = 0; k < 2; k++){
                    gameElementLocation = new Point(i , j);
                    if(mapArray[i][j][k] != null && !(mapArray[i][j][k] instanceof Block) ){
                        if(mapArray[i][j][k].getOwner().equals(rageElement.getOwner())){
                            if(ragePoint.distance(gameElementLocation) <= rage.getRadius()){
                                mapArray[i][j][k].boost();
                            }
                        }
                    }
                }
            }
        }
    } // done

    /**
     * checks if the game element is in raged area or not
     * @param gameElement
     * @return
     */
    private boolean isInRagedArea(GameElement gameElement){
        Point2D elementPoint = gameElement.getLocation();
        Point2D spellPoint = null;
        Rage rage = null;
        for(GameElement spellElement : activeSpells){
            if(gameElement.getGameEntity() instanceof Rage){
                spellPoint = spellElement.getLocation();
                rage = (Rage) spellElement.getGameEntity();
                if(elementPoint.distance(spellPoint) <=  rage.getRadius()){
                    return true;
                }
            }
        }
        return false;
    } // done

    /**
     * checks if the game is over or not
     * @return true if the game is over , else false
     */
    public boolean isGameOver(){
        if(frameCounter == 1800 ||
                !hasKingTowers(firstPlayer) ||
                !hasKingTowers(secondPlayer)){
            return true;
        }
        return false;
    } // done

    /**
     * checks if the player has king tower or not
     * @param player is the checking player
     * @return true if the player has king tower, else false
     */
    private boolean hasKingTowers(Player player){
        for(GameElement gameElement : playerToElementHashMap.get(player)){
            if(gameElement.getGameEntity() instanceof KingTower){
                return true;
            }
        }
        return false;
    } // done
    /**
     * checks if the player has left princess tower or not
     * @param player is the checking player
     * @return true if the player has left princess tower tower, else false
     */
    private boolean hasLeftTower(Player player){
        if(player.equals(firstPlayer)){
            if(mapArray[3][28][0] != null){
                if(mapArray[3][28][0].getGameEntity() instanceof PrincessTower){
                    return true;
                }
            }
        }
        else {
            if(mapArray[15][4][0] != null){
                if(mapArray[15][4][0].getGameEntity() instanceof PrincessTower){
                    return true;
                }
            }
        }
        return false;
    } // done
    /**
     * checks if the player has right princess tower or not
     * @param player is the checking player
     * @return true if the player has right princess tower tower, else false
     */
    private boolean hasRightTower(Player player){
        if(player.equals(firstPlayer)){
            if(mapArray[15][28][0] != null){
                if(mapArray[15][28][0].getGameEntity() instanceof PrincessTower){
                    return true;
                }
            }
        }
        else{
            if(mapArray[3][4][0] != null) {
                if (mapArray[3][4][0].getGameEntity() instanceof PrincessTower) {
                    return true;
                }
            }
        }
        return false;
    } // done

    /**
     * influences the game state by the commands sent by players
     */
    private void doCommands(){
        Iterator<Command> commandIterator = commands.iterator();
        Command command = null;
        while(commandIterator.hasNext()){
            command = commandIterator.next();
            doTheCommand(command);
            commandIterator.remove();
        }
    } // done

    /**
     * influences the game state by the command
     * @param command is the command from a player
     */
    private void doTheCommand(Command command){
        if(command != null){
            buyCard(command.getPlayer() , command.getCard() , command.getPoint2D());
        }
    }// done

    /**
     * actives the king tower of the input player
     * @param player is the input player
     */
    private void activeKingTower(Player player){
        for(GameElement gameElement : playerToElementHashMap.get(player)){
            if(gameElement.getGameEntity() instanceof  KingTower){
                ((KingTower)gameElement.getGameEntity()).setActiveToShoot(true);
            }
        }
    } // done

    /**
     * updates the game to the next step ( frame )
     */
    public void update(){
        resetShoots();
        increaseElixirs();
        removeExpiredSpells();
        botsDecisions();
        doCommands();

        removeDead();
        findTarget();
        moveTroops();

        configureDamages();
        doSpells();

        damageTargets();

        unRage();

        frameCounter++;
//        System.gc();
//        Runtime.getRuntime().gc();
    }

    /**
     * moves the troops to their targets ( if they are in their range , they stop moving )
     */
    private void moveTroops(){
        GameElement[][][] newMap = new GameElement[19][33][2];

        placeProperties(newMap);
        placeInRangeHunters(newMap);
        placeOutRangeHunters(newMap);

        mapArray = newMap;

    }

    /**
     * removes the spells that their life time is over
     */
    private void removeExpiredSpells(){
       Iterator<GameElement> spellIterator = activeSpells.iterator();
       GameElement spellElement = null;
       while(spellIterator.hasNext()){
           spellElement = spellIterator.next();
           if(spellElement.getGameEntity() instanceof Rage){
               Rage rage = (Rage) spellElement.getGameEntity();
               int decaSecDuration = (int) (10 * rage.getDuration());
               if(frameCounter - spellElement.getMadeAtFrame() > decaSecDuration){
                   spellIterator.remove();
                   removeElement(spellElement);
               }
           }
           else{
               spellIterator.remove();
               removeElement(spellElement);
           }
       }
    }

    private int howManyHaveTargets(){
        int counter = 0;
        GameElement gameElement = null;
        for (int i = 0; i < 19; i++) {
            for (int j = 0; j < 33; j++) {
                for (int k = 0; k < 2; k++) {
                    gameElement = mapArray[i][j][k];
                    if(gameElement != null){
                        if(elementToTargetHashMap.get(gameElement) != null){
                            counter ++;
                        }
                    }
                }
            }
        }
        return counter;
    }

    /**
     * places the properties ( towers and buildings ) on the next map
     * @param newMap is the new map ( next map )
     */
    private void placeProperties(GameElement[][][] newMap){
        GameElement gameElement = null;
        for (int i = 0; i < 19; i++) {
            for (int j = 0; j < 33; j++) {
                for (int k = 0; k < 2; k++) {
                    gameElement = mapArray[i][j][k];
                    if(gameElement != null){
                        if(gameElement instanceof Block){
                            newMap[i][j][k] = gameElement;
                            mapArray[i][j][k] = null;
                        }
                        else if(gameElement.getGameEntity() instanceof Property){
                            newMap[i][j][k] = gameElement;
                            mapArray[i][j][k] = null;
                        }
                    }
                }
            }
        }
    }

    /**
     * places the troops that their targets are in range
     * @param newMap is the new map ( next map )
     */
    private void placeInRangeHunters(GameElement[][][] newMap){
        GameElement gameElement = null;
        GameElement targetElement = null;
        for (int i = 0; i < 19; i++) {
            for (int j = 0; j < 33; j++) {
                for (int k = 0; k < 2; k++) {
                    gameElement = mapArray[i][j][k];
                    if(gameElement!= null){
                        if(gameElement.getGameEntity() instanceof Troop){
                            targetElement = elementToTargetHashMap.get(gameElement);
                            if(gameElement.getLocation().distance(targetElement.getLocation()) <=
                                    ((Troop)gameElement.getGameEntity()).getRange() + 0.5){
                                newMap[i][j][k] = mapArray[i][j][k];
                                mapArray[i][j][k] = null;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * places the troops that their targets are out of range
     * @param newMap is the new map ( next map )
     */
    private void placeOutRangeHunters(GameElement[][][] newMap){
        GameElement gameElement = null;
        GameElement targetElement = null;
        int elementX , elementY , targetX , targetY;
        for (int i = 0; i < 19; i++) {
            for (int j = 0; j < 33; j++) {
                for (int k = 0; k < 2; k++) {
                    gameElement = mapArray[i][j][k];
                    targetElement = elementToTargetHashMap.get(gameElement);
                    if(gameElement != null){
                        if(gameElement.getGameEntity() instanceof Troop){

                            if(frameCounter % ((Troop)gameElement.getGameEntity()).getSpeed().getValue() != 0){
                                stay(gameElement , newMap);
                                continue;
                            }
                            elementX = (int) gameElement.getLocation().getX();
                            elementY = (int) gameElement.getLocation().getY();
                            targetX = (int) targetElement.getLocation().getX();
                            targetY = (int) targetElement.getLocation().getY();
                            if(k == 0){
                                if(targetY < 16 && elementY == 17){
                                    if(elementX <= 2){
                                        if(mapArray[i+1][j][k] == null && newMap[i+1][j][k] == null){
                                            moveRight(gameElement , newMap);
                                            continue;
                                        }
                                        else if(mapArray[i][j+1][k] == null && newMap[i][j+1][k] == null){
                                            moveBackward(gameElement , newMap);
                                            continue;
                                        }
                                        else if(i > 0){
                                            if(mapArray[i-1][j][k] == null && newMap[i-1][j][k] == null){
                                                moveLeft(gameElement , newMap);
                                                continue;
                                            }
                                        }
                                        else{
                                            stay(gameElement , newMap);
                                            continue;
                                        }
                                    }

                                    else if(elementX == 3){
                                        if(mapArray[i][j-1][k] == null && newMap[i][j-1][k] == null){
                                            moveForward(gameElement , newMap);
                                            continue;
                                        }
                                        else{
                                            stay(gameElement , newMap);
                                            continue;
                                        }
                                    }
                                    else if(elementX >= 4 && elementX <= 9){
                                        if(mapArray[i-1][j][k] == null && newMap[i-1][j][k] == null){
                                            moveLeft(gameElement , newMap);
                                            continue;
                                        }
                                        else if(mapArray[i+1][j][k] == null && newMap[i+1][j][k] == null){
                                            moveRight(gameElement , newMap);
                                            continue;
                                        }
                                        else if(mapArray[i][j+1][k] == null && newMap[i][j+1][k] == null){
                                            moveBackward(gameElement, newMap);
                                            continue;
                                        }
                                        else{
                                            stay(gameElement , newMap);
                                            continue;
                                        }
                                    }
                                    else if(elementX > 9 && elementX < 15){
                                        if(mapArray[i+1][j][k] == null && newMap[i+1][j][k] == null){
                                            moveRight(gameElement ,  newMap);
                                            continue;
                                        }
                                        else if(mapArray[i-1][j][k] == null && newMap[i-1][j][k] == null){
                                            moveLeft(gameElement,  newMap);
                                            continue;
                                        }
                                        else if(mapArray[i][j+1][k] == null && newMap[i][j+1][k] == null){
                                            moveBackward(gameElement,  newMap);
                                            continue;
                                        }
                                        else{
                                            stay(gameElement , newMap);
                                            continue;
                                        }
                                    }

                                    else if(elementX == 15){
                                        if(mapArray[i][j-1][k] == null && newMap[i][j-1][k] == null){
                                            moveForward(gameElement , newMap);
                                            continue;
                                        }
                                        else {
                                            stay(gameElement ,  newMap);
                                            continue;
                                        }
                                    }

                                    else if(elementX >= 16){
                                        if(mapArray[i-1][j][k] == null && newMap[i-1][j][k] == null){
                                            moveLeft(gameElement , newMap);
                                            continue;
                                        }
                                        else if(mapArray[i][j+1][k] == null && newMap[i][j+1][k] == null){
                                            moveBackward(gameElement , newMap);
                                            continue;
                                        }
                                        else if(i < 18){
                                            if(mapArray[i+1][j][k] == null && newMap[i+1][j][k] == null){
                                                moveRight(gameElement , newMap);
                                                continue;
                                            }
                                        }
                                        else{
                                            stay(gameElement , newMap);
                                            continue;
                                        }
                                    }
                                }

                                else if(targetY > 16 && elementY == 15){
                                    if(elementX <= 2){
                                        if(mapArray[i+1][j][k] == null && newMap[i+1][j][k] == null){
                                            moveRight(gameElement , newMap);
                                            continue;
                                        }
                                        else if(mapArray[i][j-1][k] == null && newMap[i][j-1][k] == null){
                                            moveForward(gameElement , newMap);
                                            continue;
                                        }
                                        else if(i > 0){
                                            if(mapArray[i-1][j][k] == null && newMap[i-1][j][k] == null){
                                                moveLeft(gameElement , newMap);
                                                continue;
                                            }
                                        }
                                        else{
                                            stay(gameElement , newMap);
                                            continue;
                                        }
                                    }

                                    else if(elementX == 3){
                                        if(mapArray[i][j+1][k] == null && newMap[i][j+1][k] == null){
                                            moveBackward(gameElement , newMap);
                                            continue;
                                        }
                                        else{
                                            stay(gameElement , newMap);
                                            continue;
                                        }
                                    }

                                    else if(elementX >= 4 && elementX <= 9){
                                        if(mapArray[i-1][j][k] == null && newMap[i-1][j][k] == null){
                                            moveLeft(gameElement , newMap);
                                            continue;
                                        }
                                        else if(mapArray[i][j-1][k] == null && newMap[i][j-1][k] == null){
                                            moveForward(gameElement , newMap);
                                            continue;
                                        }
                                        else if(mapArray[i+1][j][k] == null && newMap[i+1][j][k] == null){
                                            moveRight(gameElement , newMap);
                                            continue;
                                        }
                                        else{
                                            stay(gameElement , newMap);
                                            continue;
                                        }
                                    }
                                    else if(elementX > 9 && elementX < 15){
                                        if(mapArray[i+1][j][k] == null && newMap[i+1][j][k] == null){
                                            moveRight(gameElement , newMap);
                                            continue;
                                        }
                                        else if(mapArray[i][j-1][k] == null && newMap[i][j-1][k] == null){
                                            moveForward(gameElement , newMap);
                                            continue;
                                        }
                                        else if(mapArray[i-1][j][k] == null && newMap[i-1][j][k] == null){
                                            moveLeft(gameElement , newMap);
                                            continue;
                                        }
                                        else{
                                            stay(gameElement , newMap);
                                            continue;
                                        }
                                    }

                                    else if(elementX == 15){
                                        if(mapArray[i][j+1][k] == null && newMap[i][j+1][k] == null){
                                            moveBackward(gameElement , newMap);
                                            continue;
                                        }
                                        else{
                                            stay(gameElement , newMap);
                                            continue;
                                        }
                                    }

                                    else if(elementX >= 16){
                                        if(mapArray[i-1][j][k] == null && newMap[i-1][j][k] == null){
                                            moveLeft(gameElement , newMap);
                                            continue;
                                        }
                                        else if(mapArray[i][j-1][k] == null && newMap[i][j-1][k] == null){
                                            moveForward(gameElement , newMap);
                                            continue;
                                        }
                                        else if(i < 18){
                                            if(mapArray[i +1][j][k] == null && newMap[i +1][j][k] == null){
                                                moveRight(gameElement , newMap);
                                                continue;
                                            }
                                        }
                                        else{
                                            stay(gameElement , newMap);
                                            continue;
                                        }
                                    }
                                }
                            }

                            Point2D forward = null;
                            Point2D backward = null;
                            Point2D left = null;
                            Point2D right = null;

                            if(isPointInArea(i , j + 1 , k)){
                                if(mapArray[i][j + 1][k] == null && newMap[i][j + 1][k] == null){
                                    backward = new Point(i , j+1);
                                }
                            }
                            if(isPointInArea(i , j - 1 , k)){
                                if(mapArray[i][j-1][k] == null && newMap[i][j-1][k] == null ){
                                    forward = new Point(i , j - 1);
                                }
                            }
                            if(isPointInArea(i + 1 , j , k)){
                                if(mapArray[i + 1][j][k] == null && newMap[i + 1][j][k] == null){
                                    right = new Point(i + 1 , j);
                                }
                            }
                            if(isPointInArea(i - 1 , j , k)){
                                if(mapArray[i - 1][j][k] == null && newMap[i - 1][j][k] == null){
                                    left = new Point(i - 1 , j);
                                }
                            }
                            Direction direction = whichWayCloserToTarget(gameElement.getLocation(),
                                    targetElement.getLocation(), forward , backward , left , right );
                            if(direction == null){
                                stay(gameElement , newMap);
                            }
                            else if(direction == Direction.backward){
                                moveBackward(gameElement ,newMap);
                            }
                            else if(direction == Direction.forward){
                                moveForward(gameElement , newMap);
                            }
                            else if(direction == Direction.left){
                                moveLeft(gameElement , newMap);
                            }
                            else if(direction == Direction.right){
                                moveRight(gameElement , newMap);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * the element stays in it's own location
     * @param gameElement is the game element that stays
     * @param newMap is the new map of the game ( next map )
     */
    private void stay(GameElement gameElement , GameElement[][][] newMap){
        int x = (int) gameElement.getLocation().getX();
        int y = (int) gameElement.getLocation().getY();

        if(gameElement.getGameEntity() instanceof AirWarrior){
            newMap[x][y][1] = gameElement;
            mapArray[x][y][1] = null;

        }
        else{
            newMap[x][y][0] = gameElement;
            mapArray[x][y][0] = null;
        }
    }

    /**
     * the element moves to right direction
     * @param gameElement is the element that moves
     * @param newMap is the new map of the game ( next map )
     */
    private void moveRight(GameElement gameElement , GameElement[][][] newMap){
        int x = (int) gameElement.getLocation().getX();
        int y = (int) gameElement.getLocation().getY();
        gameElement.setLocation(new Point(x + 1 , y));
        gameElement.setDirection(Direction.right);
        if(gameElement.getGameEntity() instanceof AirWarrior){
            mapArray[x][y][1] = null;
            newMap[x + 1][y][1] = gameElement;
        }
        else{
            mapArray[x][y][0] = null;
            newMap[x + 1][y][0] = gameElement;
        }
    }

    /**
     * the element moves left
     * @param gameElement is the element that moves to left
     * @param newMap is the new map of the game ( next map )
     */
    private void moveLeft(GameElement gameElement , GameElement[][][] newMap){
        int x = (int) gameElement.getLocation().getX();
        int y = (int) gameElement.getLocation().getY();
        gameElement.setLocation(new Point(x - 1 , y));
        gameElement.setDirection(Direction.left);
        if(gameElement.getGameEntity() instanceof AirWarrior){
            mapArray[x][y][1] = null;
            newMap[x - 1][y][1] = gameElement;
        }
        else{
            mapArray[x][y][0] = null;
            newMap[x - 1][y][0] = gameElement;
        }
    }
    /**
     * the element moves forward
     * @param gameElement is the element that moves to forward
     * @param newMap is the new map of the game ( next map )
     */
    private void moveForward(GameElement gameElement , GameElement[][][] newMap){
        int x = (int) gameElement.getLocation().getX();
        int y = (int) gameElement.getLocation().getY();
        gameElement.setLocation(new Point(x , y - 1));
        gameElement.setDirection(Direction.forward);
        if(gameElement.getGameEntity() instanceof AirWarrior){
            mapArray[x][y][1] = null;
            newMap[x][y - 1][1] = gameElement;
        }
        else{
            mapArray[x][y][0] = null;
            newMap[x][y - 1][0] = gameElement;
        }
    }
    /**
     * the element moves backward
     * @param gameElement is the element that moves to backward
     * @param newMap is the new map of the game ( next map )
     */
    private void moveBackward(GameElement gameElement , GameElement[][][] newMap){
        int x = (int) gameElement.getLocation().getX();
        int y = (int) gameElement.getLocation().getY();
        gameElement.setLocation(new Point(x , y + 1));
        gameElement.setDirection(Direction.backward);

        if(gameElement.getGameEntity() instanceof AirWarrior){
            mapArray[x][y][1] = null;
            newMap[x][y + 1][1] = gameElement;
        }
        else{
            mapArray[x][y][0] = null;
            newMap[x][y + 1][0] = gameElement;
        }
    }

    /**
     * checks what direction is the best for hunter to get close to it's target
     * @param hunterPosition is the position of the hunter
     * @param targetPosition is the position of the target
     * @param points are the available points for hunter to move
     * @return the direction to move the hunter
     */
    private Direction whichWayCloserToTarget(Point2D hunterPosition , Point2D targetPosition , Point2D... points){
        double distance = 100000000;
        Point2D chosenPoint = null;
        for (Point2D point: points) {
            if(point == null){
                continue;
            }
            if(point.distance(targetPosition) < distance){
                chosenPoint = point;
                distance = targetPosition.distance(chosenPoint);
            }
        }

        if(chosenPoint == null){
            return null;
        }
        else if(chosenPoint.getX() > hunterPosition.getX()){
            return Direction.right;
        }
        else if(chosenPoint.getY() > hunterPosition.getY()){
            return Direction.backward;
        }
        else if(chosenPoint.getX() < hunterPosition.getX()){
            return Direction.left;
        }
        else if(chosenPoint.getY() < hunterPosition.getY()){
            return Direction.forward;
        }
        return null;
    }

    /**
     * checks if the point is in the map or not
     * @param i
     * @param j
     * @param k
     * @return true if the point is in the map , else false
     */
    private boolean isPointInArea(int i , int j , int k){
        if(i >= 0 && i <= 18 && j >= 0 && j <= 32 && k >= 0 && k <= 1){
            return true;
        }
        return false;
    }

    /**
     * removes the elements that their time or health is over
     */
    private void removeDead(){
        GameElement gameElement = null;
        HealthHaver healthHaver = null;
        for(int i = 0; i < 19; i ++){
            for(int j = 0; j < 33; j++){
                for(int k = 0; k < 2; k ++){
                    gameElement = mapArray[i][j][k];
                    if (gameElement == null)
                        continue;
                    if(gameElement.getGameEntity() instanceof HealthHaver){
                        if(gameElement.getHealth() <= 0){
                            removeElement(gameElement);
                        }
                    }
                    if(gameElement.getGameEntity() instanceof Building){
                        Building building = (Building) gameElement.getGameEntity();
                        if(frameCounter - gameElement.getMadeAtFrame() >= (10L * building.getLifeTime())){
                            removeElement(gameElement);
                        }
                    }
                }
            }
        }
//        System.gc();
    } // done

    /**
     * finds target for the game elements that their entity is damager
     */
    private void findTarget(){
        GameElement gameElement = null;
        for (int i = 0; i < 19; i++) {
            for (int j = 0; j < 33; j++) {
                for (int k = 0; k < 2; k++) {
                    gameElement = mapArray[i][j][k];
                    if(gameElement != null){
                        if(gameElement.getGameEntity() instanceof Damager){
                            findTargetForElement(gameElement);
                        }
                    }
                }
            }
        }
    }

    /**
     * finds target for a game element that it's entity is damager
     * @param hunterElement is the game element this method finds target for it
     */
    private void findTargetForElement(GameElement hunterElement){
        GameElement targetElement = null;
        GameElement currentTarget = elementToTargetHashMap.get(hunterElement);
        for (int i = 0; i < 19; i++) {
            for (int j = 0; j < 33; j++) {
                for (int k = 0; k < 2; k++) {
                    targetElement = mapArray[i][j][k];
                    if(targetElement != null){
                        if(targetElement.getGameEntity() instanceof HealthHaver){
                            if(! hunterElement.getOwner().equals(targetElement.getOwner())){
                                if(checkHunterTargetType(hunterElement , targetElement)){
                                    if(currentTarget == null){
                                        elementToTargetHashMap.put(hunterElement , targetElement);
                                    }
                                    else if(hunterElement.getLocation().distance(targetElement.getLocation()) <
                                            hunterElement.getLocation().distance(currentTarget.getLocation())){
                                        elementToTargetHashMap.put(hunterElement , targetElement);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * the hunters damage their targets
     */
    private void damageTargets(){
        GameElement gameElement = null;
        GameElement targetElement = null;
        for (int i = 0; i < 19; i++) {
            for (int j = 0; j < 33; j++) {
                for (int k = 0; k < 2; k++) {
                    gameElement = mapArray[i][j][k];
                    if(gameElement != null && !(gameElement instanceof Block)){
                        if(gameElement.getGameEntity() instanceof Damager){
                            if(frameCounter % (int)(((Damager)gameElement.getGameEntity()).getHitSpeed() * 10) == 0){
                                targetElement = elementToTargetHashMap.get(gameElement);
                                if(targetElement != null){
                                    if(gameElement.getLocation().distance(targetElement.getLocation()) <= ((Damager)gameElement.getGameEntity()).getRange() + 0.5){
                                        if(frameCounter % (int) (10.0 *  ((Damager) gameElement.getGameEntity()).getHitSpeed()) == 0){
                                            if(((Damager)gameElement.getGameEntity()).getRange() > 1){
                                                damageShoot(gameElement.getLocation() , targetElement.getLocation());
                                            }
                                            correctDirection(gameElement , targetElement);
                                            if(gameElement.getGameEntity() instanceof KingTower){
                                                if(((KingTower)gameElement.getGameEntity()).isActiveToShoot()){
                                                    targetElement.hurt(gameElement.getDamage());
                                                }
                                            }
                                            else {
                                                if(gameElement.getGameEntity() instanceof Troop){
                                                    if(((Troop)gameElement.getGameEntity()).isAreaSplash()){
                                                          damageAreaSplash(gameElement , targetElement);
                                                    }
                                                    else{
                                                        targetElement.hurt(gameElement.getDamage());
                                                    }
                                                }
                                                else {
                                                    targetElement.hurt(gameElement.getDamage());
                                                }
                                                if(targetElement.getGameEntity() instanceof KingTower){
                                                    activeKingTower(targetElement.getOwner());
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    } // done

    /**
     * by moving or damaging the targets , the hunter direction should be corrected
     * @param hunter is the game element of the hunter
     * @param target is the game is the game element of the target of the hunter
     */
    private void correctDirection(GameElement hunter, GameElement target){

        if(hunter.getLocation().getX() > target.getLocation().getX() &&
            hunter.getLocation().getY() == target.getLocation().getY()){
            hunter.setDirection(Direction.left);
        }
        else if(hunter.getLocation().getX() < target.getLocation().getX() &&
                hunter.getLocation().getY() == target.getLocation().getY()){
            hunter.setDirection(Direction.right);
        }
        else if (hunter.getLocation().getX() == target.getLocation().getX() &&
                hunter.getLocation().getY() > target.getLocation().getY() ){
            hunter.setDirection(Direction.forward);
        }
        else if(hunter.getLocation().getX() == target.getLocation().getX() &&
                hunter.getLocation().getY() < target.getLocation().getY() ){
            hunter.setDirection(Direction.backward);
        }
        else if(hunter.getLocation().getX() > target.getLocation().getX()){
            if(hunter.getDirection() == Direction.right){
                hunter.setDirection(Direction.left);
            }
        }
        else if(hunter.getLocation().getX() < target.getLocation().getX()){
            if(hunter.getDirection() == Direction.left){
                hunter.setDirection(Direction.right);
            }
        }
        else if(hunter.getLocation().getY() > target.getLocation().getY()){
            if(hunter.getDirection() == Direction.backward){
                hunter.setDirection(Direction.forward);
            }
        }
        else if(hunter.getLocation().getY() < target.getLocation().getY()){
            if(hunter.getDirection() == Direction.forward){
                hunter.setDirection(Direction.backward);
            }
        }
    }

    /**
     * by area splash , the area by radius 1 is hurt
     * @param hunter is the game element of the hunter
     * @param target is the game element of the target
     */
    private void damageAreaSplash(GameElement hunter , GameElement target){
        int x = (int) target.getLocation().getX();
        int y = (int) target.getLocation().getY();
        int k = 0;
        GameElement gameElement = null;
        int damage = hunter.getDamage();
        for (int i = -1; i <= 1 ; i++) {
            for (int j = -1; j <= 1 ; j++) {
                if(target.getGameEntity() instanceof AirWarrior){
                    k = 1;
                }
                if(isPointInArea(x + i , y + j , k)){
                    gameElement = mapArray[x+i][y+j][k];
                    if(gameElement != null && !(gameElement instanceof Block)){
                        if(gameElement.getOwner().equals(target.getOwner())){
                            gameElement.hurt(damage);
                        }
                    }
                }
            }
        }
    }

    /**
     * influences the game state by spells
     */
    private void doSpells(){
        Iterator<GameElement> iterator = activeSpells.iterator();
        GameElement gameElement = null;
        while(iterator.hasNext()){
            gameElement = iterator.next();
            spellArea(gameElement);
        }
    } // done

    /**
     * if the elements are raged , it brings them back to the previous stage
     */
    private void unRage(){
        GameElement gameElement = null;
        for (int i = 0; i < 19; i++) {
            for (int j = 0; j < 33; j++) {
                for (int k = 0; k < 2; k++) {
                    gameElement = mapArray[i][j][k];
                    if (gameElement == null)
                        continue;
                    if(gameElement.isBoosted()){
                        gameElement.deBoost();
                    }
                }
            }
        }
    } // done

    /**
     * increases the elixirs of players to
     */
    private void increaseElixirs(){
        int increaseAmount = 0;
        int currentElixir = 0;
        if(frameCounter % 20 == 0){

            if(frameCounter >= 1200){
                increaseAmount = 2;
            }
            else{
                increaseAmount = 1;
            }
        }
        for(Player player : playerElixir.keySet()){
            currentElixir = playerElixir.get(player);
            if(currentElixir + increaseAmount > 10 ){
                playerElixir.put(player , 10);
            }
            else {
                playerElixir.put(player , currentElixir + increaseAmount);
            }
        }
    } // done

    /**
     * adds a shooting
     * @param src is the source of the shoot
     * @param dest is the destination of the shoot
     */
    private void damageShoot(Point2D src , Point2D dest){
        shoots.add(new Shoot(src , dest, frameCounter));
    }

    /**
     * empties the shoots
     */
    private void resetShoots(){
        shoots = new ArrayList<>();
    }

    /**
     * adds a command to the command list
     * @param command is the command the player sent to game
     */
    public void addCommand(Command command){
        commands.add(command);
    }// done

    /**
     * checks if the command is allowed ( when the card of the command is a troop )
     * @param command is the command sent by player
     * @param count is the count of the troop
     * @return true if the command is allowed , else false
     */
    private boolean isAreaAllowed(Command command, int count){
        int x = (int) command.getPoint2D().getX();
        int y = (int) command.getPoint2D().getY();
        for(int i = 0; i < count ; i++){
            if(! isAreaAllowed(new Command(command.getPlayer() , command.getCard() , new Point(x + i , y)))){
                return false;
            }
        }
        return true;
    } // done

    /**
     * checks if the command is allowed
     * @param command is the command sent by the player
     * @return true if the command is allowed , else false
     */
    private boolean isAreaAllowed(Command command){
        int x = (int) command.getPoint2D().getX();
        int y = (int) command.getPoint2D().getY();

        if( !(x >= 0 && x <= 18) || !(y >= 0 && y <= 32)){
            return false;
        }

        if(command.getCard() instanceof Spell){
            return true;
        }

        if(y == 16){ // no spawn on the bridge
            return false;
        }

        if(mapArray[x][y][0] != null || mapArray[x][y][1]!= null ){ // no multiple spawn in one place
            return false;
        }

        if(command.getPlayer().equals(firstPlayer)){

            if(hasLeftTower(secondPlayer) && hasRightTower(secondPlayer)){
                if(y >= 17 && y <= 32){
                    return true;
                }
            }
            else if(hasRightTower(secondPlayer)){
                if(y <= 32 && ((x >= 9 && y >= 12) || y >= 17)){
                    return true;
                }
            }
            else if(hasLeftTower(secondPlayer)){
                if(y <= 32 && ((x <= 9 && y >= 12) || y >= 17)){
                    return true;
                }
            }
            else {
                if(y <= 32 && y >= 12){
                    return true;
                }
            }
        }

        else {
            if(hasLeftTower(firstPlayer) && hasRightTower(firstPlayer)){
                if(y >= 0 && y <= 15){
                    return true;
                }
            }
            else if(hasRightTower(firstPlayer)){
                if(y >= 0 && ((x <= 9 && y <= 20) || y <= 15)){
                    return true;
                }
            }
            else if(hasLeftTower(firstPlayer)){
                if(y >= 0 && ((x >= 9 && y <= 20) || y <= 15)){
                    return true;
                }
            }
            else{
                if(y >= 0 && y <= 20){
                    return true;
                }
            }
        }
        return false;
    } // done

    /**
     * configures the damages of the
     */
    private void configureDamages(){
        int firstDamage = 0;
        int lastDamage = 0;
        int currentDamage = 0;
        long lifeTimeFrameCount = 0;
        long currentLifeFrame = 0;
        GameElement gameElement = null;
        for (int i = 0; i < 19; i++) {
            for (int j = 0; j < 33; j++) {
                gameElement = mapArray[i][j][0];
                if (gameElement == null)
                    continue;
                if(gameElement.getGameEntity() instanceof InfernoTower){
                    InfernoTower it = (InfernoTower) gameElement.getGameEntity();
                    firstDamage = it.getDamage();
                    lastDamage = it.getLastDamage();
                    lifeTimeFrameCount = (long) it.getLifeTime() * fps;
                    currentLifeFrame = frameCounter - gameElement.getMadeAtFrame();
                    currentDamage = (int) (((currentLifeFrame * lastDamage) + ((lifeTimeFrameCount - currentLifeFrame) * firstDamage)) / lifeTimeFrameCount);
                    gameElement.setDamage(currentDamage);
                }
            }
        }
    } // done

    /**
     * checks if the hunter target type is same with the type of target
     * @param hunter is the element of hunter
     * @param target is the element of target
     * @return true if the types , match
     */
    private boolean checkHunterTargetType(GameElement hunter , GameElement target){
        GameEntity hunterEntity = hunter.getGameEntity();
        GameEntity targetEntity = target.getGameEntity();

        if(hunterEntity instanceof Tower){
            return true;
        }
        else if(hunterEntity instanceof Giant){
            if(targetEntity instanceof Property){
                return true;
            }
            else {
                return false;
            }
        }
        else if(hunterEntity instanceof Real){
            Real real = (Real) hunterEntity;
            Type hunterTargetType = real.getTargetType();
            if(hunterTargetType ==  Type.airAndGround){
                return true;
            }
            else {
                if(hunterTargetType == Type.air && targetEntity instanceof AirWarrior){
                    return true;
                }
                else if(hunterTargetType == Type.ground && targetEntity instanceof GroundWarrior){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * checks if the command sent from the player is allowed
     * @param command is the command sent from player
     * @return true if the command is allowed , else false
     */
    public boolean isCommandAreaAllowed(Command command){
        GameEntity  gameEntity = command.getCard();
        if(gameEntity instanceof Troop){
            return isAreaAllowed(command , ((Troop)gameEntity).getCount());
        }
        else{
            return isAreaAllowed(command);
        }
    }

    /**
     * at the end of the game , the game result is sent as an instance of match
     * @return the result of the game
     */
    public Match gameResult(){
        String winnerName = null;
        String looserName = null;
        int winnerCrown = 0;
        int looserCrown = 0;
        if(firstPlayerCrown > secondPlayerCrown){
            winnerName = firstPlayer.getUsername();
            looserName = secondPlayer.getUsername();
            winnerCrown = firstPlayerCrown;
            looserCrown = secondPlayerCrown;
        }
        else if(firstPlayerCrown < secondPlayerCrown){
            winnerName = secondPlayer.getUsername();
            looserName = firstPlayer.getUsername();
            winnerCrown = secondPlayerCrown;
            looserCrown = firstPlayerCrown;
        }
        else {
            if(sumTowersHealth(firstPlayer) >= sumTowersHealth(secondPlayer)){
                winnerName = firstPlayer.getUsername();
                looserName = secondPlayer.getUsername();
                winnerCrown = firstPlayerCrown;
                looserCrown = secondPlayerCrown;
            }
            else {
                winnerName = secondPlayer.getUsername();
                looserName = firstPlayer.getUsername();
                winnerCrown = secondPlayerCrown;
                looserCrown = firstPlayerCrown;
            }
        }
        return new Match(winnerName , looserName , winnerCrown , looserCrown);
    }

    /**
     * calculates the sum of health of towers of a player
     * @param player is the player
     * @return the sum of towers health of the player
     */
    private int sumTowersHealth(Player player){
        int sumTowersHealth = 0;
//        List<GameElement> elements = playerToElementHashMap.get(player);

//        for(GameElement gameElement : elements){
//            if(gameElement.getGameEntity() instanceof Tower){
//                Tower tower = (Tower) gameElement.getGameEntity();
//                sumTowersHealth += tower.getHealth();
//            }
//        }
        GameElement gameElement = null;
        for (int i = 0; i < 19; i++) {
            for (int j = 0; j < 33; j++) {
                gameElement = mapArray[i][j][0];
                if(gameElement != null && !(gameElement instanceof Block)){
                    if(gameElement.getGameEntity() instanceof Tower){
                        if(gameElement.getOwner().equals(player)){
                            sumTowersHealth += gameElement.getHealth();
                        }
                    }
                }
            }
        }
        return sumTowersHealth;
    }

    /**
     * if a player is bot , then it's decision will be sent to the game
     */
    private void botsDecisions(){
        if(firstPlayer instanceof Bot){
            Command botCommand = ((Bot)firstPlayer).decision(mapArray,
                    playerRandomCardsHashMap.get(firstPlayer),
                    playerElixir.get(firstPlayer));
            addCommand(botCommand);
        }
        if(secondPlayer instanceof Bot){
            Command botCommand = ((Bot) secondPlayer).decision(mapArray ,
                    playerRandomCardsHashMap.get(secondPlayer),
                    playerElixir.get(secondPlayer));
            addCommand(botCommand);
        }
    }

    /**
     * getter for map of the game
     * @return the mapArray ( the array of the map
     */
    public GameElement[][][] getMapArray() {
        return mapArray;
    }

    /**
     * getter for frame counter
     * @return the frameCounter
     */
    public long getFrameCounter() {
        return frameCounter;
    }

    /**
     * getter for fps of the
     * @return
     */
    public int getFps() {
        return fps;
    }

    /**
     * getter for hash map of the players cards
     * @return the hash map
     */
    public HashMap<Player, List<Card>> getPlayerRandomCardsHashMap() {
        return playerRandomCardsHashMap;
    }

    /**
     * getter for active spells list
     * @return the active spells list
     */
    public List<GameElement> getActiveSpells() {
        return activeSpells;
    }

    /**
     * getter for shoots list
     * @return the list of shoots
     */
    public List<Shoot> getShoots() {
        return shoots;
    }

    /**
     * getter for the count of crowns of first player
     * @return the count of crowns of first player
     */
    public int getFirstPlayerCrown(){ return firstPlayerCrown; }

    /**
     * getter for the count of crowns of second player
     * @return the count of crowns of second player
     */
    public int getSecondPlayerCrown(){ return secondPlayerCrown; }

    /**
     * getter for player to elixir hash map
     * @return the hash map of players elixir
     */
    public int getFirstPlayerElixir(){ return playerElixir.get(firstPlayer); }

    /**
     * getter for player to list of his/her/its elements hash map
     * @return the described hash map
     */
    public HashMap<Player, List<GameElement>> getPlayerToElementHashMap() {
        return playerToElementHashMap;
    }

    /**
     * getter for element to target hash map
     * @return a hash map of game element to its target
     */
    public HashMap<GameElement, GameElement> getElementToTargetHashMap() {
        return elementToTargetHashMap;
    }

    /**
     * getter for first player of the game
     * @return the first player
     */
    public Player getFirstPlayer() {
        return firstPlayer;
    }

    /**
     * getter for second player of the game
     * @return the second player of the game
     */
    public Player getSecondPlayer() {
        return secondPlayer;
    }

    /**
     * reverse method for game manager
     * reverses every thing for 1v1 game
     */
    public void reverse(){
        reversePlayers();
        reverseCrowns();
        reverseMap();
        reverseSpells();
        reverseShoots();
    }

    /**
     * reverses the map of the game
     */
    private void reverseMap(){
        GameElement[][][] newMap = new GameElement[19][33][2];
        GameElement gameElement = null;
        for (int i = 0; i < 19; i++) {
            for (int j = 0; j < 33; j++) {
                for (int k = 0; k < 2; k++) {
                    gameElement = mapArray[i][j][k];
                    if(gameElement != null){
                        reverseElementLocation(gameElement);
                        reverseElementDirection(gameElement);
                    }
                    newMap[18 - i][32 - j][k] = gameElement;
                }
            }
        }
        mapArray = newMap;
    }

    /**
     * reverses the location of spells
     */
    private void reverseSpells(){
        for(GameElement spellElement : activeSpells){
            reverseElementLocation(spellElement);
        }
    }

    /**
     * reverses the location of shoots
     */
    private void reverseShoots(){
        for(Shoot shoot : shoots){
            shoot.setSrc(reversePoint(shoot.getSrc()));
            shoot.setDest(reversePoint(shoot.getDest()));
        }
    }

    /**
     * reverses the location of the input game element
     * @param gameElement is the input game element
     */
    private void reverseElementLocation(GameElement gameElement){
        gameElement.setLocation(reversePoint(gameElement.getLocation()));
    }

    /**
     * reverses a point
     * @param point2D is the input point
     * @return the reversed point
     */
    public static Point2D reversePoint(Point2D point2D){
        int newX = (int) (18 - point2D.getX());
        int newY = (int) (32 - point2D.getY());
        return new Point(newX , newY);
    }

    /**
     * reverses a command ( the chosen location )
     * @param command is the input command
     * @return the reversed command
     */
    public static Command reverseCommand (Command command){
        return new Command(command.getPlayer(),
                command.getCard(),
                reversePoint(command.getPoint2D()));
    }

    /**
     * reverses the crown of players
     */
    private void reverseCrowns(){
        int tmp = firstPlayerCrown;
        firstPlayerCrown = secondPlayerCrown;
        secondPlayerCrown = tmp;
    }

    /**
     * reverses the players
     */
    private void reversePlayers(){
        Player tmp = firstPlayer;
        firstPlayer = secondPlayer;
        secondPlayer = tmp;
    }

    /**
     * reverses the direction of elements
     * @param gameElement
     */
    private void reverseElementDirection(GameElement gameElement){
        gameElement.setDirection(reverseDirection(gameElement.getDirection()));
    }

    /**
     * reverses the direction
     * @param direction input direction
     * @return the reversed direction
     */
    private Direction reverseDirection(Direction direction){
        if(direction == Direction.backward){
            return Direction.forward;
        }
        else if(direction == Direction.forward){
            return Direction.backward;
        }
        else if(direction == Direction.right){
            return Direction.left;
        }
        else if(direction == Direction.left){
            return Direction.right;
        }
        return null;
    }
}