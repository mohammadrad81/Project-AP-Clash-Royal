package Network;

import Model.Game.Command;
import Model.Game.GameManager;
import Model.Stats.Match;
import Users.Player;
import javafx.application.Platform;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class TwoPlayerServer {
    private final int numberOfPlayers = 2;
    private final int port = 8989;
    private final ServerSidePlayer[] serverSidePlayers = new ServerSidePlayer[2];
    private GameManager gameManager ;
    private Timer timer;


    public void start() {
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
                (new PlayerHandler(serverSidePlayer, this)).start();
            } catch (IOException e) {
                System.err.println("! not successful connection try !");
                i--;
                continue;
            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
                System.err.println("! wrong object sent by player !");
            }
        }

        this.gameManager = new GameManager(serverSidePlayers[0].getPlayer(),
                serverSidePlayers[1].getPlayer());

//        gameLoop();
        startTimer();
//        sendGameResultToPlayers();

    }

    public void startTimer(){
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
//                while (! gameManager.isGameOver()) {
                    gameManager.update();
                    sendGameManagerToPlayers();
                    //System.out.println(gameManager.getFrameCounter());
//                }
            }
        };
        long frameTimeInMilliseconds = (long) (1000.0 / 10);
        timer.schedule(timerTask,0, frameTimeInMilliseconds);
    }

    private void gameLoop(){
        while (! gameManager.isGameOver()){
            gameManager.update();
            sendGameManagerToPlayers();
            try {
                Thread.sleep(95);
            } catch (InterruptedException e) {
                e.printStackTrace();
                // never happens
            }
        }
    }

    private void sendGameResultToPlayers(){
        for(ServerSidePlayer serverSidePlayer : serverSidePlayers){
            try {
                serverSidePlayer.sendToPlayer(gameManager.gameResult());
            } catch (IOException e) {
                playerDisconnected(serverSidePlayer);
            }
        }
    }

    private void sendGameManagerToPlayers(){
        for (ServerSidePlayer serverSidePlayer : serverSidePlayers){
            if(serverSidePlayer.getPlayer().equals(gameManager.getFirstPlayer())){
                System.out.println("GG");
                try {
                    serverSidePlayer.sendToPlayer(GameManager.getInstance());
                } catch (IOException e) {
                    playerDisconnected(serverSidePlayer);
                }
            }
            else{
                gameManager.reverse();
                try {
                    serverSidePlayer.sendToPlayer(gameManager);
                } catch (IOException e) {
                    playerDisconnected(serverSidePlayer);
                }
                gameManager.reverse();
            }
        }
    }

    public synchronized void addCommand(Command command){
        if(command.getPlayer().equals(gameManager.getSecondPlayer())){
            Command reversedCommand = GameManager.reverseCommand(command);
            gameManager.addCommand(reversedCommand);
        }

        else{
            gameManager.addCommand(command);
        }
    }

    public synchronized void playerDisconnected(ServerSidePlayer disconnectedPlayer){
        if(disconnectedPlayer.equals(serverSidePlayers[0])){
            try {
                serverSidePlayers[1].sendToPlayer(new Match(serverSidePlayers[1].getPlayer().getUsername(),
                        serverSidePlayers[0].getPlayer().getUsername() ,
                        3, 0));
            } catch (IOException e) {
//                e.printStackTrace();
                System.err.println("! seems like both players are disconnected !");
            }
        }
        else{
            try {
                serverSidePlayers[0].sendToPlayer(new Match(serverSidePlayers[0].getPlayer().getUsername(),
                        serverSidePlayers[1].getPlayer().getUsername() , 3 , 0));
            } catch (IOException e) {
                //e.printStackTrace();
                System.err.println("! seems like both players are disconnected !");
            }
        }
        System.exit(-2);
    }
}
