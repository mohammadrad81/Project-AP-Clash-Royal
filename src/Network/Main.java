package Network;
/**
 * Server main class
 * @author Alireza Jabbari Mahalle No
 * @author Mohammad Heydari Rad
 * @version 1.0.0
 */
public class Main {

    public static void main(String[] args) {
        TwoPlayerServer server = new TwoPlayerServer();
        server.start();
    }
}
