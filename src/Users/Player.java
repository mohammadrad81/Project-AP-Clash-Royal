package Users;

import Model.Cards.Card;
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
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return username.equals(player.username) && password.equals(player.password);
    }

}
