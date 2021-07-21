package Users.Bots;

import Model.Cards.Card;
import Model.Game.Command;
import Model.Game.GameElement;
import Users.Player;

import java.util.List;

/**
 * an abstract class for bots of the game
 * @author Alireza Jabbari Mahalle No
 * @author Mohammad Heydari Rad
 * @version 1.0.0
 */
public abstract class Bot extends Player {
    protected Bot(String username, int level){
        super(username, level);
    }

    /**
     * the decision of the bot , in the game state sent to it
     * @param mapArray is the map of the game
     * @param cards is a list of cards , given to the bot
     * @param elixir is the amount of elixir the bot can spend
     * @return a command as decision ( could be null )
     */
    public abstract Command decision(GameElement[][][] mapArray, List<Card> cards, int elixir);
}
