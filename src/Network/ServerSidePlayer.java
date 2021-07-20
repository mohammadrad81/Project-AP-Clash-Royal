package Network;

import Model.Game.GameManager;
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

    public synchronized void sendToPlayer(Object object) throws IOException {
//        System.out.println(((GameManager) object).getFrameCounter());

        out.writeObject(object);
        out.flush();
        out.reset();
    }
    public Object receiveFromPlayer() throws IOException, ClassNotFoundException {
        return in.readObject();
    }

}
