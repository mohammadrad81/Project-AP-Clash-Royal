package Model.Stats;

import java.io.Serializable;

public class Match implements Serializable {
    private String player1Username;
    private String player2Username;
    private int player1Crown;
    private int player2Crown;

    public Match(String player1Username, String player2Username,
                 int player1Crown, int player2Crown){
        this.player1Username = player1Username;
        this.player2Username = player2Username;
        this.player1Crown = player1Crown;
        this.player2Crown = player2Crown;
    }
}
