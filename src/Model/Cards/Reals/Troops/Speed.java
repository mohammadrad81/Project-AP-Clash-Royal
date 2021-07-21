package Model.Cards.Reals.Troops;

/**
 * is an enum for speed of the Troop
 * the amounts are by deca second
 * @author Alireza Jabbari Mahalle No
 * @author Mohammad Heydari Rad
 * @version 1.0.0
 * @since 7.8.2021
 */
public enum Speed {
    superFast(5),
    fast(7),
    medium(10),
    slow(14);

    private final int value;
    private Speed(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
