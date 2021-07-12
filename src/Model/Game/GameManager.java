package Model.Game;

import Model.Cards.Card;
import Model.Cards.Reals.Buildings.Building;
import Model.Cards.Reals.Buildings.InfernoTower;
import Model.Cards.Reals.Troops.BabyDragon;
import Model.Cards.Reals.Troops.Troop;
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

    private List<Command> commands;

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
    }

    private void placeElement(GameElement gameElement){
        placeElement(gameElement , gameElement.getLocation());
    }

    private void placeElement(GameElement gameElement , int count){
        Point2D[] point2DS = new Point2D[count];
        int x = (int) gameElement.getLocation().getX();
        int y = (int) gameElement.getLocation().getY();
        Point2D point = gameElement.getLocation();
        for(int i = 0; i < count ; i ++){
            point2DS[i] = new Point(x + i , y);
            placeElement(gameElement , point2DS[i]);
        }
    }

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

    }

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
    }

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
    }

    private void doCommands(){
        for(Command command : commands){
            doTheCommand(command);
        }
    }

    private void doTheCommand(Command command){
        // implement later
    }

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

    public void addCommand(Command command){
        commands.add(command);
    }

    private boolean isAreaAllowed(Player player, Point2D point2D){
        return isAreaAllowed(new Command(player , null , point2D));
    }

    private boolean isAreaAllowed(Command command, int count){
        int x = (int) command.getPoint2D().getX();
        int y = (int) command.getPoint2D().getY();
        for(int i = 0; i < count ; i++){
            if(! isAreaAllowed(new Command(command.getPlayer() , command.getCard() , new Point(x + i , y)))){
                return false;
            }
        }
        return true;
    }

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
    }

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
    }
}

