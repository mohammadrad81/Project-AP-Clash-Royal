package Model.Towers;

public class PrincessTower extends Tower{
    private static final int[] healthByLevelArray = {1400, 1512, 1624, 1750, 1890};
    private static final int[] damageByLevelArray = {50, 54, 58, 62, 69};
    private static final String imageAddress = "";// will be written later

    public PrincessTower() {
        super(imageAddress, healthByLevelArray,
                damageByLevelArray, 7.5, 0.8,
                true);
    }
}
