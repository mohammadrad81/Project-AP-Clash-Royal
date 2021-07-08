package Model.Cards.Spells;

public class Arrows extends Spell{
    private static final String arrowsCardImageAddress = "../../../Pictures/CardImages.ArrowsCard.jpg";
    private static final int[] areaDamageByLevelArray = {144, 156, 174, 189, 210};
    private int areaDamage;

    public Arrows() {
        super(3 , arrowsCardImageAddress , null,
                4 , Ability.arrow);
        areaDamage = areaDamageByLevelArray[0];
    }


    @Override
    public void upgrade(int level) {
        areaDamage = areaDamageByLevelArray[level-1];
    }

    public int getAreaDamage() {
        return areaDamage;
    }
}
