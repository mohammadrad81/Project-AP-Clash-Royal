package Model.Game;

import java.awt.geom.Point2D;

public class Shoot {
    private Point2D src;
    private Point2D dest;
    private long madeAtFrame;

    public Shoot(Point2D src, Point2D dest, long madeAtFrame) {
        this.src = src;
        this.dest = dest;
        this.madeAtFrame = madeAtFrame;
    }

    public Point2D getSrc() {
        return src;
    }

    public Point2D getDest() {
        return dest;
    }

    public long getMadeAtFrame(){
        return madeAtFrame;
    }
}
