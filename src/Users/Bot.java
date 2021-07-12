package Users;

import Model.Cards.Card;
import Model.Game.Command;
import Model.Game.GameElement;

import java.util.ArrayList;

public abstract class Bot extends Player{
    protected Bot(String username, int level){
        super(username, level);
    }

    public abstract Command decision(GameElement[][][] mapArray, ArrayList<Card> cards, int elixir);
}
