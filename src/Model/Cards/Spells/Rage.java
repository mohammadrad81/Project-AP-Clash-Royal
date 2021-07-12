package Model.Cards.Spells;

/**
 * rage spell of the game
 * @version 1.0.0
 * @since 7.8.2021
 */
public class Rage extends Spell{
    private static final String rageCardImageAddress = "/Pictures/CardImages/RageCard.jpg";
    private static final double[] durationByLevelArray = {6, 6.5, 7, 7.5, 8};

    private double duration;

    /**
     * constructor for the rage spell
     */
    public Rage() {
        super(3, rageCardImageAddress, null, 5, Ability.boost);
        duration = durationByLevelArray[0];
    }
    /**
     * constructor for leveled rage spell
     * @param level is level of card
     */
    public Rage(int level){
        super(3, rageCardImageAddress, null, 5, Ability.boost);
        upgrade(level);
    }

    /**
     * upgrades the spell ( changes the duration )
     * @param level is the level of the player
     */
    @Override
    public void upgrade(int level) {
        duration = durationByLevelArray[level -1];
    }

    /**
     *
     * @return duration of the card ability
     */
    public double getDuration() {
        return duration;
    }
}
