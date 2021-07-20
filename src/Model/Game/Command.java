package Model.Game;

import Model.Cards.Card;
import Users.Player;

import java.awt.geom.Point2D;
import java.io.Serializable;

/**
 * class for store user actions
 */
public class Command implements Serializable {
    private Player player;
    private Card card;
    private Point2D point2D;

    /**
     * create a command with given player, card and location to place
     * @param player the player
     * @param card the selected card
     * @param point2D the location
     */
    public Command(Player player, Card card, Point2D point2D) {
        this.player = player;
        this.card = card;
        this.point2D = point2D;
    }

    /**
     * get player of command
     * @return the owner
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * get selected card of player
     * @return card
     */
    public Card getCard() {
        return card;
    }

    /**
     * get location of command
     * @return point of command
     */
    public Point2D getPoint2D() {
        return point2D;
    }
}
