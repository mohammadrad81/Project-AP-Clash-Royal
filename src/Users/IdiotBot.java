package Users;

import Model.Cards.Card;
import Model.Game.Command;
import Model.Game.GameElement;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class IdiotBot extends Bot{

    public IdiotBot(int level){
        super("IDIOT BOT", level);
    }

    @Override
    public Command decision(GameElement[][][] mapArray, ArrayList<Card> cards, int elixir) {

        if (elixir < 2)
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
        int x = random.nextInt(19);
        int y = random.nextInt(16);


        return new Command(this,selectedCard, new Point(x,y));
    }
}
