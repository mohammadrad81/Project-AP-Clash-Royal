package Model.Game;

import java.awt.geom.Point2D;
import java.io.Serializable;

/**
 * class for show attack of troops
 */
public class Shoot implements Serializable {
    private Point2D src;
    private Point2D dest;
    private long madeAtFrame;

    /**
     * create a shoot with given source and destination and frame that created
     * @param src source of shoot
     * @param dest destination of shoot
     * @param madeAtFrame frame of creation of shoot
     */
    public Shoot(Point2D src, Point2D dest, long madeAtFrame) {
        this.src = src;
        this.dest = dest;
        this.madeAtFrame = madeAtFrame;
    }

    /**
     * get source of shoot
     * @return source location
     */
    public Point2D getSrc() {
        return src;
    }

    /**
     * get destination of shoot
     * @return destination location
     */
    public Point2D getDest() {
        return dest;
    }

    /**
     * get creation frame of shoot
     * @return frame
     */
    public long getMadeAtFrame(){
        return madeAtFrame;
    }

    /**
     * set source of shoot
     * @param src source location
     */
    public void setSrc(Point2D src) {
        this.src = src;
    }

    /**
     * set destination of shoot
     * @param dest destination location
     */
    public void setDest(Point2D dest) {
        this.dest = dest;
    }
}
