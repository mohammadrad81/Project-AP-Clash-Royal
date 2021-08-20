package Network;

import Model.Game.Command;

import java.io.IOException;

/**
 * class for handler player actions
 * @author Alireza Jabbari Mahalle No
 * @author Mohammad Heydari Rad
 * @version 1.0.0
 */
public class PlayerHandler extends Thread{
    private ServerSidePlayer serverSidePlayer;
    private TwoPlayerServer server;

    /**
     * create an instance
     * @param serverSidePlayer the player
     * @param server the server
     */
    public PlayerHandler(ServerSidePlayer serverSidePlayer, TwoPlayerServer server) {
        this.serverSidePlayer = serverSidePlayer;
//        this.setDaemon(true);
        this.server = server;
    }

    @Override
    public void run() {
        while (true){
            Command command = null;
            Object receivedObject = null;
            try {
                receivedObject = serverSidePlayer.receiveFromPlayer();
                if(receivedObject == null){
                    break;
                }
                command = (Command) receivedObject;
                server.addCommand(command);
            } catch (IOException e) {
//                e.printStackTrace();
                server.playerDisconnected(serverSidePlayer);
                break;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
