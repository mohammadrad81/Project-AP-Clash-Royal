package Model.Cards.Spells;

public class Rage extends Spell{
    private static final String rageCardImageAddress = "../../../Pictures/CardImages/RageCard.jpg";
    private static final double[] durationByLevelArray = {6, 6.5, 7, 7.5, 8};

    private double duration;

    public Rage() {
        super(3, rageCardImageAddress, null, 5, Ability.boost);
        duration = durationByLevelArray[0];
    }


    @Override
    public void upgrade(int level) {
        duration = durationByLevelArray[level -1];
    }

    public double getDuration() {
        return duration;
    }
}
