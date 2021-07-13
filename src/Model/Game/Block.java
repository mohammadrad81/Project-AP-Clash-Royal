package Model.Game;

import java.awt.geom.Point2D;

public class Block extends GameElement {
    public static final String blockImageAddress = "" ;// implement later;
    public Block(Point2D location) {
        super(null, location, null, 0, null);
    }
    @Override
    public String getImageAddress(){
        return blockImageAddress;
    }
}
