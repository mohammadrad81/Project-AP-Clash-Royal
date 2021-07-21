package Users;

import Model.Cards.Card;
import Model.Cards.Reals.Buildings.Cannon;
import Model.Cards.Reals.Buildings.InfernoTower;
import Model.Cards.Reals.Troops.*;
import Model.Cards.Spells.Arrows;
import Model.Cards.Spells.Fireball;
import Model.Cards.Spells.Rage;
import Model.Stats.Match;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * player class for login and play the game
 * @author Alireza Jabbari Mahalle No
 * @author Mohammad Heydari Rad
 * @version 1.0.0
 */
public class Player implements Serializable {
    private String username;
    private String password;
    private int level;
    private int xp;
    private ArrayList<Match> history;
    private ArrayList<Card> cards;
    private ArrayList<Card> hand;
    private final int[] needXpForLevelUp = {300, 500, 900, 1700, 2500};

    /**
     * create new player by username and password (this constructor used in signUp page)
     * @param username the username of player
     * @param password the password of player
     * @see Controller.SignupPageController
     */
    public Player(String username, String password){
        this.username = username;
        this.password = password;
        level = 1;
        xp = 0;
        history = new ArrayList<>();
        cards = new ArrayList<>();
        hand = new ArrayList<>();

        cards.add(new Barbarian());
        cards.add(new Archer());
        cards.add(new BabyDragon());
        cards.add(new Wizard());
        cards.add(new MiniPekka());
        cards.add(new Giant());
        cards.add(new Valkyrie());
        cards.add(new Rage());
        cards.add(new Fireball());
        cards.add(new Arrows());
        cards.add(new Cannon());
        cards.add(new InfernoTower());
    }

    /**
     * create new player by username and level (this constructor used for create bots)
     * @param username the username of player
     * @param level the level of player
     */
    public Player(String username, int level){ // used for create bots
        this.username = username;
        password = "";
        this.level = level;
        history = new ArrayList<>();
        cards = new ArrayList<>();

        cards.add(new Barbarian(level));
        cards.add(new Archer(level));
        cards.add(new BabyDragon(level));
        cards.add(new Wizard(level));
        cards.add(new MiniPekka(level));
        cards.add(new Giant(level));
        cards.add(new Valkyrie(level));
        cards.add(new Rage(level));
        cards.add(new Fireball(level));
        cards.add(new Arrows(level));
        cards.add(new Cannon(level));
        cards.add(new InfernoTower(level));

        Collections.shuffle(cards);

        hand = new ArrayList<>();
        for (int i = 0; i < 8; i++){
            hand.add(cards.get(0));
            cards.remove(0);
        }
    }

    /**
     * get username of player
     * @return username
     */
    public String getUsername() {
        return username;
    }
    /**
     * get password of player
     * @return password
     */
    public String getPassword() {
        return password;
    }
    /**
     * get card list of player
     * @return list of cards
     */
    public ArrayList<Card> getCards() {
        return cards;
    }
    /**
     * get hand list of player
     * @return cards of hand
     */
    public ArrayList<Card> getHand() {
        return hand;
    }
    /**
     * get level of player
     * @return level
     */
    public int getLevel() {
        return level;
    }
    /**
     * get XP of player
     * @return XP
     */
    public int getXp() {
        return xp;
    }

    /**
     * get recent matches of player
     * @return list of matches
     */
    public ArrayList<Match> getHistory() {
        return history;
    }

    /**
     * set username of player
     * @param username username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * set level of player
     * @param level level
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * set xp of player, if have enough xp, level up
     * @param xp xp
     */
    public void setXp(int xp) {
        this.xp = xp;
        if (this.xp > needXpForLevelUp[level - 1]){
            this.xp -= needXpForLevelUp[level - 1];
            setLevel(level + 1);
            for (Card card : cards){
                card.upgrade(level);
            }
            for (Card card: hand){
                card.upgrade(level);
            }
        }
    }

    /**
     * add a match to recent matches list
     * @param match the match to be added
     */
    public void addMatchToHistory(Match match){
       history.add(match);
    }

    /**
     * get list of cards of player
     * @param cards card list
     */
    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    /**
     * set hand cards of player
     * @param hand
     */
    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }

    /**
     * number of matches that player won
     * @return number of victory
     */
    public int getCountOfWonMatches(){
        int counter = 0;
        for(Match match : history){
            if(match.getWinnerName().equals(username)){
                counter++;
            }
        }
        return counter;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return username.equals(player.username) && password.equals(player.password);
    }
    @Override
    public int hashCode(){
        return (username.hashCode() + password.hashCode());
    }

}
