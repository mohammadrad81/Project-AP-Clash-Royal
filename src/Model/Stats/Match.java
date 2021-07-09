package Model.Stats;

import java.io.Serializable;

public class Match implements Serializable {

    private static final String victoryImageAddress = "/Pictures/MatchImages/Victory.png";
    private static final String defeatImageAddress = "/Pictures/MatchImages/Defeat.png";

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

    @Override
    public String toString() {
        return player1Username + " " + player1Crown + " - " + player2Crown + " " + player2Username;
    }

    public String getImageAddress(){
        if (player1Crown > player2Crown)
            return victoryImageAddress;
        else
            return defeatImageAddress;
    }
}
