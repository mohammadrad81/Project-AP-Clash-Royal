package Model.Game;

import java.awt.geom.Point2D;
import java.io.Serializable;

public class Block extends GameElement implements Serializable {
    public static final String blockImageAddress = "" ;// implement later;
    public Block(Point2D location) {
        super(null, location, null, 0, null);
    }
    @Override
    public String getImageAddress(){
        return blockImageAddress;
    }
}
