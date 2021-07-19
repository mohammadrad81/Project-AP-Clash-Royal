package Model.Game;

import Model.Cards.Card;
import Users.Player;

import java.awt.geom.Point2D;
import java.io.Serializable;

public class Command implements Serializable {
    private Player player;
    private Card card;
    private Point2D point2D;

    public Command(Player player, Card card, Point2D point2D) {
        this.player = player;
        this.card = card;
        this.point2D = point2D;
    }

    public Player getPlayer() {
        return player;
    }

    public Card getCard() {
        return card;
    }

    public Point2D getPoint2D() {
        return point2D;
    }
}
