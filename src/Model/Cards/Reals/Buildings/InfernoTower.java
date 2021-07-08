package Model.Cards.Reals.Buildings;

import Model.Cards.Reals.Type;

public class InfernoTower extends Building{
    private static final String infernoCardImageAddress = "../../../../Pictures/CardImages/InfernoTowerCard.jpg";
    private static final String infernoImageAddress = "";
    private static final int[] firstDamageByLevel = {20, 22, 24, 26, 29};
    private static final int[] lastDamageByLevel = {400, 440, 484, 532, 584};
    private static final int[] healthByLevel = {800, 880, 968, 1064, 1168};
    private int lastDamage;

    public InfernoTower() {
        super(5, infernoCardImageAddress, infernoImageAddress,
                healthByLevel[0], firstDamageByLevel[0], 6,
                0.4, Type.ground, Type.airAndGround,
                40);
        this.lastDamage = lastDamageByLevel[0];
    }

    @Override
    public void upgrade(int level) {
        setDamage(firstDamageByLevel[level -1]);
        setHealth(healthByLevel[level -1]);
        this.lastDamage = lastDamageByLevel[level -1];
    }

    public int getLastDamage() {
        return lastDamage;
    }
}
