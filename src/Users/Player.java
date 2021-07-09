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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return username.equals(player.username) && password.equals(player.password);
    }

}
