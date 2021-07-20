package Model.Stats;

import java.io.Serializable;

/**
 * class for store result of a game
 */
public class Match implements Serializable {

    private static final String victoryImageAddress = "/Pictures/MatchImages/Victory.png";
    private static final String defeatImageAddress = "/Pictures/MatchImages/Defeat.png";


    private String winnerName;
    private String looserName;
    private int winnerCrown;
    private int looserCrown;

    /**
     * create an instance of match
     * @param winnerName username of winner
     * @param looserName username of looser
     * @param winnerCrown crown number of winner
     * @param looserCrown crown number of looser
     */
    public Match(String winnerName, String looserName, int winnerCrown, int looserCrown) {
        this.winnerName = winnerName;
        this.looserName = looserName;
        this.winnerCrown = winnerCrown;
        this.looserCrown = looserCrown;
    }

    /**
     * get winner username
     * @return username of winner
     */
    public String getWinnerName() {
        return winnerName;
    }

    /**
     * get looser username
     * @return username of looser
     */
    public String getLooserName() {
        return looserName;
    }

    /**
     * get number of crowns of winner
     * @return winner crowns
     */
    public int getWinnerCrown() {
        return winnerCrown;
    }

    /**
     * get number of crowns of looser
     * @return looser crowns
     */
    public int getLooserCrown() {
        return looserCrown;
    }

    /**
     * get match result in string
     * @param username username of player
     * @return result as a string
     */
    public String getAsString(String username) {
//        return player1Username + " " + player1Crown + " - " + player2Crown + " " + player2Username;


        if(username.equals(winnerName)){
            return winnerName + " " + winnerCrown + " - " + looserCrown + " " + looserName;
        }

        else{
            return looserName + " " + looserCrown + " - " + winnerCrown + " " + winnerName;
        }

    }

    /**
     * get match image address by given username
     * @param playerUsername username of player
     * @return victory image address if given player is winner, otherwise defeat image address
     */
    public String getImageAddress(String playerUsername){
        if(playerUsername.equals(winnerName)){
            return victoryImageAddress;
        }
        else{
            return defeatImageAddress;
        }
    }
}
