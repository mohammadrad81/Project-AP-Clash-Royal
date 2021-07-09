package Model.Stats;

import java.io.Serializable;

public class Match implements Serializable {

    private static final String victoryImageAddress = "/Pictures/MatchImages/Victory.png";
    private static final String defeatImageAddress = "/Pictures/MatchImages/Defeat.png";


    private String winnerName;
    private String looserName;
    private int winnerCrown;
    private int looserCrown;

    public Match(String winnerName, String looserName, int winnerCrown, int looserCrown) {
        this.winnerName = winnerName;
        this.looserName = looserName;
        this.winnerCrown = winnerCrown;
        this.looserCrown = looserCrown;
    }

    public String getWinnerName() {
        return winnerName;
    }

    public String getLooserName() {
        return looserName;
    }

    public int getWinnerCrown() {
        return winnerCrown;
    }

    public int getLooserCrown() {
        return looserCrown;
    }


    public String getAsString(String username) {
//        return player1Username + " " + player1Crown + " - " + player2Crown + " " + player2Username;


        if(username.equals(winnerName)){
            return winnerName + " " + winnerCrown + " - " + looserCrown + " " + looserName;
        }

        else{
            return looserName + " " + looserCrown + " - " + winnerCrown + " " + winnerName;
        }

    }

    public String getImageAddress(String playerUsername){
        if(playerUsername.equals(winnerName)){
            return victoryImageAddress;
        }
        else{
            return defeatImageAddress;
        }
    }
}
