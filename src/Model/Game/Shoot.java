package Model.Game;

import java.awt.geom.Point2D;

public class Shoot {
    private Point2D src;
    private Point2D dest;

    public Shoot(Point2D src, Point2D dest) {
        this.src = src;
        this.dest = dest;
    }

    public Point2D getSrc() {
        return src;
    }

    public Point2D getDest() {
        return dest;
    }
}
