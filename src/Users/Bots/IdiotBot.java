package Users.Bots;

import Model.Cards.Card;
import Model.Cards.Spells.Spell;
import Model.Game.Command;
import Model.Game.GameElement;
import Model.Game.GameManager;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class IdiotBot extends Bot {
    private GameManager gameManager = null;

    public IdiotBot(int level){
        super("IDIOT BOT", level);
    }

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

        if (selectedCard instanceof Spell) {
            do {
                int x = random.nextInt(19);  // between 0 and 18
                int y = random.nextInt(33);  // between 0 and 32
                command = new Command(this, selectedCard, new Point(x, y));
            } while (! gameManager.isCommandAreaAllowed(command));
        }
        else{
            do {
                int x = random.nextInt(19);  // between 0 and 18
                int y = random.nextInt(21);  // between 0 and 20 (enemy towers may be destroyed)
                command = new Command(this, selectedCard, new Point(x, y));
            } while (! gameManager.isCommandAreaAllowed(command));
        }

        return command;
    }
}
