package Model.Cards.Reals.Buildings;

import Model.Cards.Reals.Type;

public class Cannon extends Building{
    private static final String cannonCardImageAddress = "../../../../Pictures/CardImages/CannonCard.jpg";
    private static final String cannonImageAddress = "";
    private static final int[] damageByLevelArray ={380, 418, 459, 505, 554};
    private static final int[] healthByLevelArray ={60, 66, 72, 79, 87};

    public Cannon() {
        super(6, cannonCardImageAddress, cannonImageAddress,
                healthByLevelArray[0], damageByLevelArray[0],
                5.5, 0.8, Type.ground, Type.ground, 30);
    }


    @Override
    public void upgrade(int level) {
        setDamage(damageByLevelArray[level -1]);
        setHealth(healthByLevelArray[level -1]);
    }
}
