package Network;

import Users.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ServerSidePlayer {
    private Player player;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public ServerSidePlayer(Player player, ObjectOutputStream out, ObjectInputStream in) {
        this.player = player;
        this.out = out;
        this.in = in;
    }

    public Player getPlayer() {
        return player;
    }

    public void sendToPlayer(Object object) throws IOException {
        out.writeObject(object);
    }
    public Object receiveFromPlayer() throws IOException, ClassNotFoundException {
        return in.readObject();
    }

}
