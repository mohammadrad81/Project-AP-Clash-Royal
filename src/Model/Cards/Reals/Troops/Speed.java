package Model.Cards.Reals.Troops;

/**
 * is an enum for speed of the Troop
 */
public enum Speed {
    superFast(7),
    fast(5),
    medium(4),
    slow(3);

    private final int value;
    private Speed(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
