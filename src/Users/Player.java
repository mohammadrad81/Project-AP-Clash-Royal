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
import java.util.Objects;

public class Player implements Serializable {
    private String username;
    private String password;
    private int level;
    private int xp;
    private ArrayList<Match> history;
    private ArrayList<Card> cards;
    private ArrayList<Card> hand;

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

    public Player(String username, int level){ // used for create bots
        this.username = username;
        password = "";
        this.level = level;
        history = null;
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

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public int getLevel() {
        return level;
    }

    public int getXp() {
        return xp;
    }

    public ArrayList<Match> getHistory() {
        return history;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public void addMatchToHistory(Match match){
       history.add(match);
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }

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
