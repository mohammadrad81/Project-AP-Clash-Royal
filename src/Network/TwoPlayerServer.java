package Network;

import Model.Game.Command;
import Model.Game.GameManager;
import Users.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TwoPlayerServer {
    private static final int numberOfPlayers = 2;
    private static final int port = 8989;
    private static final ServerSidePlayer[] serverSidePlayers = new ServerSidePlayer[2];
    private static GameManager gameManager ;


    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println("! the server couldn't turn on !");
            System.exit(-1);
        }

        for (int i = 0; i < numberOfPlayers; i++) {
            try {
                Socket socket = serverSocket.accept();
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                Player player = (Player) in.readObject();
                ServerSidePlayer serverSidePlayer = new ServerSidePlayer(player , out , in);
                serverSidePlayers[i] = serverSidePlayer;
                (new PlayerHandler(serverSidePlayer)).start();
            } catch (IOException e) {
                System.err.println("! not successful connection try !");
                i--;
                continue;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        gameManager = new GameManager(serverSidePlayers[0].getPlayer(),
                serverSidePlayers[1].getPlayer());

        gameLoop();
        sendGameResultToPlayers();

    }

    private static void gameLoop(){
        while (! gameManager.isGameOver()){
            gameManager.update();
            sendGameManagerToPlayers();
            try {
                Thread.sleep(90);
            } catch (InterruptedException e) {
                e.printStackTrace();
                // never happens
            }
        }
    }

    private static void sendGameResultToPlayers(){
        for(ServerSidePlayer serverSidePlayer : serverSidePlayers){
            try {
                serverSidePlayer.sendToPlayer(gameManager.gameResult());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void sendGameManagerToPlayers(){
        for (ServerSidePlayer serverSidePlayer : serverSidePlayers){
            if(serverSidePlayer.getPlayer().equals(gameManager.getFirstPlayer())){
                try {
                    serverSidePlayer.sendToPlayer(gameManager);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else{
                gameManager.reverse();
                try {
                    serverSidePlayer.sendToPlayer(gameManager);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                gameManager.reverse();
            }
        }
    }

    public synchronized static void addCommand(Command command){
        if(command.getPlayer().equals(gameManager.getSecondPlayer())){
            Command reversedCommand = GameManager.reverseCommand(command);
            gameManager.addCommand(reversedCommand);
        }

        else{
            gameManager.addCommand(command);
        }
    }

    public synchronized static void playerDisconnected(ServerSidePlayer disconnectedPlayer){
        // implement later
    }
}
