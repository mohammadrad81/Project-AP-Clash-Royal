package Network;

import Model.Game.GameManager;
import Users.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * class for send and receive data with player
 */
public class ServerSidePlayer {
    private Player player;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    /**
     * create an instance
     * @param player the player
     * @param out output stream
     * @param in input stream
     */
    public ServerSidePlayer(Player player, ObjectOutputStream out, ObjectInputStream in) {
        this.player = player;
        this.out = out;
        this.in = in;
    }

    /**
     * get player
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * send an object to the player
     * @param object the data to be sent
     * @throws IOException if player disconnected
     */
    public synchronized void sendToPlayer(Object object) throws IOException {

        out.writeObject(object);
        out.flush();
        out.reset();
    }

    /**
     * receive an object from player
     * @return received object
     * @throws IOException if player disconnected
     * @throws ClassNotFoundException
     */
    public Object receiveFromPlayer() throws IOException, ClassNotFoundException {
        return in.readObject();
    }

}
