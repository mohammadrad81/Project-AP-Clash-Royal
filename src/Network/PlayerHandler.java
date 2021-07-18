package Network;

import Model.Game.Command;

import java.io.IOException;

public class PlayerHandler extends Thread{
    private ServerSidePlayer serverSidePlayer;

    public PlayerHandler(ServerSidePlayer serverSidePlayer) {
        this.serverSidePlayer = serverSidePlayer;
        this.setDaemon(true);
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
                TwoPlayerServer.addCommand(command);
            } catch (IOException e) {
//                e.printStackTrace();
                TwoPlayerServer.playerDisconnected(serverSidePlayer);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
