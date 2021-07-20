package Model.Game;

import java.awt.geom.Point2D;
import java.io.Serializable;

/**
 * class for define forbidden area for ground warriors
 */
public class Block extends GameElement implements Serializable {
    public static final String blockImageAddress = "" ;// implement later;

    /**
     * create a block in given location
     * @param location
     */
    public Block(Point2D location) {
        super(null, location, null, 0, null);
    }
    @Override
    public String getImageAddress(){
        return blockImageAddress;
    }
}
