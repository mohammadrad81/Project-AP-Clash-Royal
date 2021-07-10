package Model.Cards.Spells;

/**
 * the arrow spell card in game
 * @see Model.Cards.Card
 * @version 1.0.0
 * @since 7.8.2021
 */
public class Arrows extends Spell{
    private static final String arrowsCardImageAddress = "/Pictures/CardImages/ArrowsCard.png";
    private static final int[] areaDamageByLevelArray = {144, 156, 174, 189, 210};
    private int areaDamage;

    /**
     * constructor for card
     */
    public Arrows() {
        super(3 , arrowsCardImageAddress , null,
                4 , Ability.arrow);
        areaDamage = areaDamageByLevelArray[0];
    }

    /**
     * upgrade method for card
     * @param level is the level of the player
     */
    @Override
    public void upgrade(int level) {
        areaDamage = areaDamageByLevelArray[level-1];
    }

    /**
     *
     * @return area damage of the arrows card
     */
    public int getAreaDamage() {
        return areaDamage;
    }
}
