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
import Model.Towers.KingTower;
import Model.Towers.PrincessTower;
import Model.Towers.Tower;
import Users.Player;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.io.DataInput;
import java.lang.annotation.Target;
import java.util.*;
import java.util.List;


public abstract class GameManager {
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

    public static GameManager getInstance(){
        return gameManager;
    }

    private List<Command> commands;

    private GameManager(Player firstPlayer, Player secondPlayer) {
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
        gameManager = this;
    }

    public void init(){
        givePlayerTowers(firstPlayer);
        givePlayerTowers(secondPlayer);
        giveRandomCardToPlayer(firstPlayer, 4);
        giveRandomCardToPlayer(secondPlayer, 4);
        playerElixir.put(firstPlayer , 4);
        playerElixir.put(secondPlayer , 4);
        // implement later :

    }

    public void increaseFirstPlayerCrown(){
        firstPlayerCrown++;
    }//done

    public void increaseSecondPlayerCrown(){
        secondPlayerCrown++;
    }//done

    public void addElement(Player player , GameElement gameElement ){
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

    private void placeElement(GameElement gameElement){
        placeElement(gameElement , gameElement.getLocation());
    }//done

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

    public void removeElement(GameElement gameElement){
        removeElement(gameElement.getOwner() , gameElement);
    } // done

    public void removeElement(Player player , GameElement gameElement){
        List<GameElement> playerElements = playerToElementHashMap.get(player);
        playerElements.remove(gameElement);
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
                increaseSecondPlayerCrown();
            }
            else {
                activeKingTower(secondPlayer);
                increaseFirstPlayerCrown();
            }
        }

        if(gameElement.getGameEntity() instanceof Spell){
            activeSpells.remove(gameElement);
        }

    } //done

    public void buyCard(Player player , Card card , Point2D point2D) {
        Command command = new Command(player, card, point2D);
        if (card instanceof Troop) {
            if (!isAreaAllowed(command, ((Troop) card).getCount())) {
                return; // or throw exception
            }
        } else if (!isAreaAllowed(command)) {
            return; // or throw exception
        }

        if (playerElixir.get(player) >= card.getCost()) {
            playerElixir.put(player, playerElixir.get(player) - card.getCost());


            GameElement gameElement = null;
            playerRandomCardsHashMap.get(player).remove(card);

            if (player.equals(secondPlayer)) {
                gameElement =
                        new GameElement(card, point2D,
                                player, frameCounter,
                                Direction.backward);
            } else if (player.equals(firstPlayer)) {
                gameElement =
                        new GameElement(card, point2D,
                                player, frameCounter,
                                Direction.forward);
            }
            addElement(player, gameElement);
            giveRandomCardToPlayer(player);
        }
    } // done

    public void giveRandomCardToPlayer(Player player , int count){
        for(int i = 0; i < 4; i++){
            giveRandomCardToPlayer(player);
        }
    } // done

    public void giveRandomCardToPlayer(Player player){
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

    public void givePlayerTowers(Player player){
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
            leftPrincessTower =
                    new GameElement(new PrincessTower(player.getLevel()) ,
                            new Point(3, 28),
                            player,
                            frameCounter,
                            Direction.forward);
            rightPrincessTower =
                    new GameElement(new PrincessTower(player.getLevel()),
                            new Point(15, 28),
                            player,
                            frameCounter,
                            Direction.forward);
        }
        else {
            kingTowerElement =
                    new GameElement(new KingTower(player.getLevel()),
                            new Point(9, 1),
                            player,
                            frameCounter,
                            Direction.backward);
            rightPrincessTower =
                    new GameElement(new PrincessTower(player.getLevel()),
                            new Point(3, 4),
                            player,
                            frameCounter,
                            Direction.backward);
            leftPrincessTower =
                    new GameElement(new PrincessTower(player.getLevel()),
                            new Point(15, 4),
                            player,
                            frameCounter,
                            Direction.backward);
        }
        gameElements.add(kingTowerElement);
        gameElements.add(leftPrincessTower);
        gameElements.add(rightPrincessTower);
    } // done

    public void spellArea(GameElement spellElement){
        activeSpells.add(spellElement);
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

    private void arrowArea(GameElement arrowElement){
        Arrows arrows = (Arrows) arrowElement.getGameEntity();
        Point2D arrowPoint = arrowElement.getLocation();
        Point2D gameElementLocation = null;
        for(int i = 0 ; i < 19; i++){
            for(int j = 0; j < 33; j ++){
                for(int z = 0; z < 2; z++){
                    gameElementLocation = new Point(i , j);
                    if(mapArray[i][j][z] != null){
                        if(mapArray[i][j][z].getOwner() != arrowElement.getOwner()){
                            if(arrowPoint.distance(gameElementLocation) <= arrows.getRadius()){
                                mapArray[i][j][z].hurt(arrows.getAreaDamage());
                            }
                        }
                    }
                }
            }
        }
    } // done

    private void fireBallArea(GameElement fireBallElement){
        Fireball fireball = (Fireball) fireBallElement.getGameEntity();
        Point2D fireballPoint = fireBallElement.getLocation();
        Point2D gameElementLocation = null;

        for(int i = 0 ; i < 19; i++){
            for(int j = 0; j < 33; j ++){
                for(int z = 0; z < 2; z++){
                    gameElementLocation = new Point(i , j);
                    if(mapArray[i][j][z] != null){
                        if(mapArray[i][j][z].getOwner() != fireBallElement.getOwner()){
                            if(fireballPoint.distance(gameElementLocation) <= fireball.getRadius()){
                                mapArray[i][j][z].hurt(fireball.getAreaDamage());
                            }
                        }
                    }
                }
            }
        }
    } // done

    private void rageArea(GameElement rageElement){
        Rage rage = (Rage) rageElement.getGameEntity();
        Point2D ragePoint = rageElement.getLocation();
        Point2D gameElementLocation = null;

        for(int i = 0 ; i < 19; i++){
            for(int j = 0; j < 33; j ++){
                for(int z = 0; z < 2; z++){
                    gameElementLocation = new Point(i , j);
                    if(mapArray[i][j][z] != null){
                        if(mapArray[i][j][z].getOwner().equals(rageElement.getOwner())){
                            if(ragePoint.distance(gameElementLocation) <= rage.getRadius()){
                                mapArray[i][j][z].boost();
                            }
                        }
                    }
                }
            }
        }
    } // done

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

    public boolean isGameOver(){
        if(frameCounter == 1800 ||
                !hasKingTowers(firstPlayer) ||
                !hasKingTowers(secondPlayer)){
            return true;
        }
        return false;
    } // done

    private boolean hasKingTowers(Player player){
        for(GameElement gameElement : playerToElementHashMap.get(player)){
            if(gameElement.getGameEntity() instanceof KingTower){
                return true;
            }
        }
        return false;
    } // done

    private boolean hasLeftTower(Player player){
        if(player.equals(firstPlayer)){
            if(mapArray[3][28][0] != null){
                return true;
            }
            else {
                return false;
            }
        }
        else {
            if(mapArray[15][4][0] != null){
                return true;
            }
            else{
                return false;
            }
        }
    } // done

    private boolean hasRightTower(Player player){
        if(player.equals(firstPlayer)){
            if(mapArray[15][28][0] != null){
                return true;
            }
            else {
                return false;
            }
        }
        else{
            if(mapArray[3][4][0] != null){
                return true;
            }
            else {
                return false;
            }
        }
    } // done

    private void doCommands(){
        for(Command command : commands){
            doTheCommand(command);
        }
    } // done

    private void doTheCommand(Command command){
        buyCard(command.getPlayer() , command.getCard() , command.getPoint2D());
    }// done

    private void activeKingTower(Player player){
        for(GameElement gameElement : playerToElementHashMap.get(player)){
            if(gameElement.getGameEntity() instanceof  KingTower){
                ((KingTower)gameElement.getGameEntity()).setActiveToShoot(true);
            }
        }
    } // done

    public void update(){
        increaseElixirs();

        doCommands();

        removeDead();
        findTarget();
        moveTroops();

        doSpells();
        configureDamages();
        damageTargets();

        unRage();

        frameCounter++;
    }

    private void moveTroops(){
        GameElement[][][] newMap = new GameElement[19][33][2];
        placeNoneMoveElements(newMap);
        placeInRangeHunters(newMap);
        placeOutRangeHunters(newMap);
        mapArray = newMap;
    }

    private void placeNoneMoveElements(GameElement[][][] newMap){
        for (int i = 0; i < 19; i++) {
            for (int j = 0; j < 33; j++) {

                if(mapArray[i][j][0] instanceof Property) {
                    newMap[i][j][0] = mapArray[i][j][0];
                    mapArray[i][j][0] = null;
                }

                for (int k = 0; k < 2; k++) {

                }
            }
        }
    }

    private void placeInRangeHunters(GameElement[][][] newMap){
        GameElement gameElement = null;
        GameElement targetElement = null;
        for (int i = 0; i < 19; i++) {
            for (int j = 0; j < 33; j++) {
                for (int k = 0; k < 2; k++) {
                    gameElement = mapArray[i][j][k];
                    targetElement = elementToTargetHashMap.get(gameElement);
                    if(targetElement != null){
                        Damager damager = (Damager) gameElement.getGameEntity();
                        if(gameElement.getLocation().distance(gameElement.getLocation()) <= damager.getRange()){
                            newMap[i][j][k] = mapArray[i][j][k];
                            mapArray[i][j][k] = null;
                        }
                    }
                }
            }
        }
    }

    private void placeOutRangeHunters(GameElement[][][] newMap){
        GameElement gameElement = null;
        GameElement targetElement = null;
        int elementX , elementY , targetX , targetY;
        for (int i = 0; i < 19; i++) {
            for (int j = 0; j < 33; j++) {
                for (int k = 0; k < 2; k++) {
                    gameElement = mapArray[i][j][k];
                    mapArray[i][j][k] = null;
                    targetElement = elementToTargetHashMap.get(gameElement);
                    if(targetElement != null){
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
                                        moveBackward(gameElement , newMap);
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
                                    else if(mapArray[i-1][j][k] == null && newMap[i-1][j][k] == null){
                                        moveLeft(gameElement , newMap);
                                        continue;
                                    }
                                    else if(mapArray[i][j+1][k] == null && newMap[i][j+1][k] == null){
                                        moveBackward(gameElement , newMap);
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
                                        stay(gameElement , newMap);
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
                                        if(mapArray[i +1][j][k] == null && newMap [i +1][j][k] == null){
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
                            if(mapArray[i][j + 1][k] != null && newMap[i][j + 1][k] != null){
                                backward = new Point(i , j+1);
                            }
                        }
                        if(isPointInArea(i , j - 1 , k)){
                            if(mapArray[i][j-1][k] != null && newMap[i][j-1][k] != null){
                                forward = new Point(i , j - 1);
                            }
                        }
                        if(isPointInArea(i + 1 , j , k)){
                            if(mapArray[i + 1][j][k] != null && newMap[i + 1][j][k] != null){
                                right = new Point(i + 1 , j);
                            }
                        }
                        if(isPointInArea(i - 1 , j , k)){
                            if(mapArray[i - 1][j][k] != null && newMap[i - 1][j][k] != null){
                                left = new Point(i - 1 , j);
                            }
                        }
                        Direction direction = whichWayCloserToTarget(gameElement.getLocation(),
                                targetElement.getLocation(), forward , backward , left , right );
                        if(direction == null){
                            stay(gameElement , newMap);
                        }
                        else if(direction == Direction.backward){
                            moveBackward(gameElement , newMap);
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

    private void stay(GameElement gameElement , GameElement [][][] newMap){
        int x = (int) gameElement.getLocation().getX();
        int y = (int) gameElement.getLocation().getY();

        if(gameElement.getGameEntity() instanceof AirWarrior){
            newMap[x][y][1] = gameElement;
        }
        else{
            newMap[x][y][0] = gameElement;
        }
    }

    private void moveRight(GameElement gameElement , GameElement[][][] newMap){
        int x = (int) gameElement.getLocation().getX() + 1;
        int y = (int) gameElement.getLocation().getY();

        if(gameElement.getGameEntity() instanceof AirWarrior){
            newMap[x][y][1] = gameElement;
        }
        else{
            newMap[x][y][0] = gameElement;
        }

        gameElement.setLocation(new Point(x , y));
        gameElement.setDirection(Direction.right);
    }

    private void moveLeft(GameElement gameElement , GameElement[][][] newMap){
        int x = (int) gameElement.getLocation().getX() - 1;
        int y = (int) gameElement.getLocation().getY();

        if(gameElement.getGameEntity() instanceof AirWarrior){
            newMap[x][y][1] = gameElement;
        }
        else{
            newMap[x][y][0] = gameElement;
        }

        gameElement.setLocation(new Point(x , y));
        gameElement.setDirection(Direction.left);
    }

    private void moveForward(GameElement gameElement , GameElement[][][] newMap){
        int x = (int) gameElement.getLocation().getX();
        int y = (int) gameElement.getLocation().getY() - 1;

        if(gameElement.getGameEntity() instanceof AirWarrior){
            newMap[x][y][1] = gameElement;
        }
        else{
            newMap[x][y][0] = gameElement;
        }

        gameElement.setLocation(new Point(x , y ));
        gameElement.setDirection(Direction.forward);
    }

    private void moveBackward(GameElement gameElement , GameElement[][][] newMap){
        int x = (int) gameElement.getLocation().getX();
        int y = (int) gameElement.getLocation().getY() + 1;

        if(gameElement.getGameEntity() instanceof AirWarrior){
            newMap[x][y][1] = gameElement;
        }
        else{
            newMap[x][y][0] = gameElement;
        }

        gameElement.setLocation(new Point(x , y ));
        gameElement.setDirection(Direction.backward);
    }

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

    private boolean isPointInArea(int i , int j , int k){
        if(i >= 0 && i <= 18 && j >= 0 && j <= 32 && k >= 0 && k <= 1){
            return true;
        }
        return false;
    }

    private void removeDead(){
        GameElement gameElement = null;
        HealthHaver healthHaver = null;
        for(int i = 0; i < 19; i ++){
            for(int j = 0; j < 33; j++){
                for(int k = 0; k < 2; k ++){
                    gameElement = mapArray[i][j][k];
                    if(gameElement.getGameEntity() instanceof HealthHaver){
                        healthHaver = (HealthHaver) gameElement.getGameEntity();
                        if(healthHaver.getHealth() <= 0){
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
    } // done

    private void findTarget(){
        GameElement gameElement = null;
        for (int i = 0; i < 19; i++) {
            for (int j = 0; j < 33; j++) {
                for (int k = 0; k < 2; k++) {
                    gameElement = mapArray[i][j][k];

                    if(!(gameElement instanceof Damager)){
                            continue;
                    }
                    else {
                        findTargetForElement(gameElement);
                    }
                }
            }
        }
    }

    private void findTargetForElement(GameElement hunterElement){
        GameElement targetElement = null;
        GameElement currentTargetElement = elementToTargetHashMap.get(hunterElement);
        for (int l = 0; l < 19; l++) {
            for (int m = 0; m < 33; m++) {
                for (int n = 0; n < 2; n++) {
                    targetElement = mapArray[l][m][n];
                    if(! hunterElement.getOwner().equals(targetElement.getOwner())){
                        if(checkHunterTargetType(hunterElement ,targetElement)){
                            if(currentTargetElement == null){
                                elementToTargetHashMap.put(hunterElement , targetElement);
                            }
                            else {
                                if(hunterElement.getLocation().distance(currentTargetElement.getLocation()) >
                                        hunterElement.getLocation().distance(targetElement.getLocation())){
                                    elementToTargetHashMap.put(hunterElement , targetElement);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void damageTargets(){
        GameElement gameElement = null;
        GameElement targetElement = null;
        for (int i = 0; i < 19; i++) {
            for (int j = 0; j < 33; j++) {
                for (int k = 0; k < 2; k++) {
                    gameElement = mapArray[i][j][k];
                    if(gameElement instanceof Damager){
                        targetElement = elementToTargetHashMap.get(gameElement);
                        if(targetElement != null){
                            if(frameCounter % (int) (10.0 *  ((Damager) gameElement.getGameEntity()).getHitSpeed()) == 0){
                                if(gameElement.getGameEntity() instanceof KingTower){
                                    if(((KingTower)gameElement.getGameEntity()).isActiveToShoot()){
                                        damageAnimation(gameElement.getLocation() , targetElement.getLocation());
                                        targetElement.hurt(gameElement.getDamage());
                                        if(targetElement.getGameEntity() instanceof KingTower){
                                            activeKingTower(targetElement.getOwner());
                                        }
                                    }
                                }
                                else {
                                    damageAnimation(gameElement.getLocation() , targetElement.getLocation());
                                    targetElement.hurt(gameElement.getDamage());
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
    } // done

    private void doSpells(){
        for(GameElement gameElement : activeSpells){

            spellArea(gameElement);

            if(gameElement.getGameEntity() instanceof Rage){
                Rage rage = (Rage) gameElement.getGameEntity();
                int decaSecDuration = (int) (10 * rage.getDuration());
                if(frameCounter - gameElement.getMadeAtFrame() > decaSecDuration){
                    removeElement(gameElement);
                }
            }
            else {
                removeElement(gameElement);
            }
        }
    } // done

    private void unRage(){
        GameElement gameElement = null;
        for (int i = 0; i < 19; i++) {
            for (int j = 0; j < 33; j++) {
                for (int k = 0; k < 2; k++) {
                    gameElement = mapArray[i][j][k];
                    if(gameElement.isBoosted()){
                        gameElement.deBoost();
                    }
                }
            }
        }
    } // done

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

    public void damageAnimation(Point2D src , Point2D dest){
        // implement later
    }

    public void addCommand(Command command){
        commands.add(command);
    }// done

    private boolean isAreaAllowed(Player player, Point2D point2D){
        return isAreaAllowed(new Command(player , null , point2D));
    } // done

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

    private boolean isAreaAllowed(Command command){
        int x = (int) command.getPoint2D().getX();
        int y = (int) command.getPoint2D().getY();

        if(y == 16){ // no spawn on the bridge
            return false;
        }

        if( !(x >= 0 && x <= 18) || !(y >= 0 && y <= 32)){
            return false;
        }

        if(command.getCard() instanceof Spell){
            return true;
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
                if(gameElement.getGameEntity() instanceof InfernoTower){
                    InfernoTower it = (InfernoTower) gameElement.getGameEntity();
                    firstDamage = it.getDamage();
                    lastDamage = it.getLastDamage();
                    lifeTimeFrameCount = (long) it.getLifeTime() * fps;
                    currentLifeFrame = frameCounter - gameElement.getMadeAtFrame();
                    currentDamage = (int) ((currentLifeFrame * lastDamage) + ((lifeTimeFrameCount - currentLifeFrame) * firstDamage));
                    gameElement.setDamage(currentDamage);
                }
            }
        }
    } // done

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

    public boolean isCommandAreaAllowed(Command command){
        GameEntity  gameEntity = command.getCard();
        if(gameEntity instanceof Troop){
            return isAreaAllowed(command , ((Troop)gameEntity).getCount());
        }
        else{
            return isAreaAllowed(command);
        }
    }
}

