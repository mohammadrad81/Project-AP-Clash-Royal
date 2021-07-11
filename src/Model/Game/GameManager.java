package Model.Game;

import Model.Cards.Card;
import Model.Cards.Reals.Troops.BabyDragon;
import Model.Cards.Spells.Arrows;
import Model.Cards.Spells.Fireball;
import Model.Cards.Spells.Rage;
import Model.Cards.Spells.Spell;
import Model.Interfaces.Damager;
import Model.Interfaces.HealthHaver;
import Model.Towers.KingTower;
import Model.Towers.PrincessTower;
import Model.Towers.Tower;
import Users.Player;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.*;
import java.util.List;


public abstract class GameManager {
//    private static final String team1Name = "team 1";
//    private static final String team2Name = "team 2";

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

    private List<String> commands;

    public GameManager(Player firstPlayer, Player secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;

        this.playerRandomCardsHashMap = new HashMap<>();
        this.playerRandomCardsHashMap.put(firstPlayer, new ArrayList<>());
        this.playerRandomCardsHashMap.put(secondPlayer, new ArrayList<>());

        this.playerElixir = new HashMap<>();


        this.activeSpells = new ArrayList<>();
        this.playerToElementHashMap = new HashMap<>();
        this.playerToElementHashMap.put(firstPlayer, new ArrayList<>());
        this.playerToElementHashMap.put(secondPlayer, new ArrayList<>());
        this.mapArray = new GameElement[19][33][2];
        this.elementToTargetHashMap = new HashMap<>();
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
    }

    public void increaseSecondPlayerCrown(){
        secondPlayerCrown++;
    }

    public void addElement(Player player , GameElement gameElement ){
        List<GameElement> playerElements = playerToElementHashMap.get(player);
        playerElements.add(gameElement);
        playerToElementHashMap.put(player , playerElements);
        if(gameElement.getGameEntity() instanceof BabyDragon){
            mapArray[(int) gameElement.getLocation().getX()]
                    [(int) gameElement.getLocation().getY()]
                    [1] = gameElement;
        }
        else{
            mapArray[(int) gameElement.getLocation().getX()]
                    [(int) gameElement.getLocation().getY()]
                    [0] = gameElement;
        }
    }

    public void removeElement(GameElement gameElement){
        removeElement(gameElement.getOwner() , gameElement);
    }

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
            if(player == firstPlayer){
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

    }

    public void buyCard(Player player , Card card , Point2D point2D){
        GameElement gameElement = null;
        playerRandomCardsHashMap.get(player).remove(card);

        if(player == secondPlayer){
            gameElement =
                    new GameElement(card , point2D ,
                    player , frameCounter,
                    Direction.backward);
        }
        else if(player == firstPlayer){
            gameElement =
                    new GameElement(card , point2D ,
                            player , frameCounter,
                            Direction.forward);
        }
        addElement(player , gameElement);
        giveRandomCardToPlayer(player);
    }

    public void giveRandomCardToPlayer(Player player , int count){
        for(int i = 0; i < 4; i++){
            giveRandomCardToPlayer(player);
        }
    }

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
    }

    public void givePlayerTowers(Player player){
        List<GameElement> gameElements = playerToElementHashMap.get(player);
        GameElement kingTowerElement = null;
        GameElement leftPrincessTower = null;
        GameElement rightPrincessTower = null;
        if(player == firstPlayer){
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
            leftPrincessTower =
                    new GameElement(new PrincessTower(player.getLevel()),
                            new Point(3, 4),
                            player,
                            frameCounter,
                            Direction.backward);
            rightPrincessTower =
                    new GameElement(new PrincessTower(player.getLevel()),
                            new Point(15, 4),
                            player,
                            frameCounter,
                            Direction.backward);
        }
        gameElements.add(kingTowerElement);
        gameElements.add(leftPrincessTower);
        gameElements.add(rightPrincessTower);
    }

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
    }

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
    }

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
    }

    private void rageArea(GameElement rageElement){
        Rage rage = (Rage) rageElement.getGameEntity();
        Point2D ragePoint = rageElement.getLocation();
        Point2D gameElementLocation = null;

        for(int i = 0 ; i < 19; i++){
            for(int j = 0; j < 33; j ++){
                for(int z = 0; z < 2; z++){
                    gameElementLocation = new Point(i , j);
                    if(mapArray[i][j][z] != null){
                        if(mapArray[i][j][z].getOwner() == rageElement.getOwner()){
                            if(ragePoint.distance(gameElementLocation) <= rage.getRadius()){
                                mapArray[i][j][z].boost();
                            }
                        }
                    }
                }
            }
        }
    }

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
    }

    private boolean isGameOver(){
        if(frameCounter == 1800 ||
                !hasKingTowers(firstPlayer) ||
                !hasKingTowers(secondPlayer)){
            return true;
        }
        return false;
    }

    private boolean hasKingTowers(Player player){
        for(GameElement gameElement : playerToElementHashMap.get(player)){
            if(gameElement.getGameEntity() instanceof KingTower){
                return true;
            }
        }
        return false;
    }

    private void doCommands(){
        for(String command : commands){
            doTheCommand(command);
        }
    }

    private void doTheCommand(String command){
        //implement later
    }

    private void activeKingTower(Player player){
        for(GameElement gameElement : playerToElementHashMap.get(player)){
            if(gameElement.getGameEntity() instanceof  KingTower){
                ((KingTower)gameElement.getGameEntity()).setActiveToShoot(true);
            }
        }
    }

    public void update(){
        increaseElixirs();

        doCommands();

        removeDead();
        findTarget();
        moveTroops();

        doSpells();

        damageTargets();

        unRage();

        frameCounter++;
    }

    private void moveTroops(){
        //implement later
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
                            removeElement(gameElement.getOwner() , gameElement);
                        }
                    }
                }
            }
        }
    } // done

    private void findTarget(){
        //implement later
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
                                    }
                                }
                                else {
                                    damageAnimation(gameElement.getLocation() , targetElement.getLocation());
                                    targetElement.hurt(gameElement.getDamage());
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
}

