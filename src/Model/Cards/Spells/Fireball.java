package Model.Cards.Spells;

/**
 * class for fireball card of game
 * @version 1.0.0
 * @since 7.8.2021
 */
public class Fireball extends Spell{
    private static final String fireBallCardImageAddress = "/Pictures/CardImages/FireballCard.png";
    private static final int[] areaDamageByLevelArray = {325, 357, 393, 532, 474};

    private int areaDamage;

    /**
     * constructor for card
     */
    public Fireball() {
        super(4 , fireBallCardImageAddress , null,
                2.5 , Ability.fire);
        areaDamage = areaDamageByLevelArray[0];
    }

    /**
     * upgrades the card ( area damage ) by level
     * @param level is the level of the player
     */
    @Override
    public void upgrade(int level) {
        areaDamage = areaDamageByLevelArray[level -1];
    }

    /**
     *
     * @return area damage of the card
     */
    public int getAreaDamage() {
        return areaDamage;
    }
}
