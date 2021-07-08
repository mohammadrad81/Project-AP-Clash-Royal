package Model.Towers;

public class KingTower extends Tower {
    private static final int[] healthByLevelArray = {2400, 2568, 2736, 2904, 3086};
    private static final int[] damageByLevelArray = {50, 53, 57, 60, 64};
    private static final String imageAddress = "";// will be written later

    public KingTower() {
        super(imageAddress, healthByLevelArray,
                damageByLevelArray, 7, 1,
                false);
    }
}
