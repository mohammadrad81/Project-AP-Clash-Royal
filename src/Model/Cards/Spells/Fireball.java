package Model.Cards.Spells;

public class Fireball extends Spell{
    private static final String fireBallCardImageAddress = "../../../Pictures/CardImages/";
    private static final int[] areaDamageByLevelArray = {325, 357, 393, 532, 474};

    private int areaDamage;

    public Fireball() {
        super(4 , fireBallCardImageAddress , null,
                2.5 , Ability.fire);
        areaDamage = areaDamageByLevelArray[0];
    }

    @Override
    public void upgrade(int level) {
        areaDamage = areaDamageByLevelArray[level -1];
    }

    public int getAreaDamage() {
        return areaDamage;
    }
}
