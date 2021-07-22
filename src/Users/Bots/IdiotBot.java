package Users.Bots;

import Model.Cards.Card;
import Model.Cards.Spells.Spell;
import Model.Game.Command;
import Model.Game.GameElement;
import Model.Game.GameManager;

import java.awt.*;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;

/**
 * the dumbest bot of the game ( do not underestimate it )
 * @author Alireza Jabbari Mahalle No
 * @author Mohammad Heydari Rad
 * @version 1.0.0
 */
public class IdiotBot extends Bot {
    private GameManager gameManager = null;

    /**
     * constructor of it
     * @param level
     */
    public IdiotBot(int level){
        super("IDIOT BOT", level);
    }

    /**
     * the decision of the bot
     * @param mapArray is the map of the game
     * @param cards is a list of cards , given to the bot
     * @param elixir is the amount of elixir the bot can spend
     * @return the command that is the decision of the bot
     */
    @Override
    public Command decision(GameElement[][][] mapArray, List<Card> cards, int elixir) {
        if (gameManager == null)
            gameManager = GameManager.getInstance();

        if (elixir < 4)
            return null;

        Card selectedCard = null;

        for (Card card: cards){
            if (card.getCost() <= elixir) {
                selectedCard = card;
                break;
            }
        }
        if (selectedCard == null)
            return null;

        Random random = new Random();

        Command command = null;
        long startMilliSec = ZonedDateTime.now().toInstant().toEpochMilli();
        long nowMilliSec = 0;
        if (selectedCard instanceof Spell) {
            do {
                nowMilliSec = ZonedDateTime.now().toInstant().toEpochMilli();
                int x = random.nextInt(19);  // between 0 and 18
                int y = random.nextInt(33);  // between 0 and 32
                command = new Command(this, selectedCard, new Point(x, y));
            } while ((! gameManager.isCommandAreaAllowed(command)) && (nowMilliSec < (startMilliSec + 20)));
        }
        else{
            do {
                nowMilliSec = ZonedDateTime.now().toInstant().toEpochMilli();
                int x = random.nextInt(19);  // between 0 and 18
                int y = random.nextInt(21);  // between 0 and 20 (enemy towers may be destroyed)
                command = new Command(this, selectedCard, new Point(x, y));
            } while ((! gameManager.isCommandAreaAllowed(command)) && (nowMilliSec < startMilliSec + 20));
        }

        return command;
    }
}
