package Network;

import Users.Player;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Server main class
 * @author Alireza Jabbari Mahalle No
 * @author Mohammad Heydari Rad
 * @version 1.0.0
 */
public class Main  {

    public static void main(String[] args) {
//        TwoPlayerServer server = new TwoPlayerServer();
//        server.start();
//        launch(args);
        startServer();
    }

    public static void startServer(){
        System.out.println("Please enter the port : ");
        Scanner scanner = new Scanner(System.in);
        int port = scanner.nextInt();
        System.out.println("The server is online");
        ServerSidePlayer buff = null;
        Socket socket = null;
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true){
                socket = serverSocket.accept();

                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                Player player =(Player) in.readObject();
                if(buff == null){
                    buff = new ServerSidePlayer(player, out, in);
                    System.out.println("new player joined the server\nwaiting for opponent...");
                }
                else{
                    ServerSidePlayer finalBuff = buff;
                    System.out.println("opponent joined");
                    ServerSidePlayer secondPlayer = new ServerSidePlayer(player,out,in);
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            TwoPlayerServer server = new TwoPlayerServer();
                            (new PlayerHandler(finalBuff,server)).start();
                            (new PlayerHandler(secondPlayer,server)).start();
                            server.start(finalBuff,secondPlayer);
                        }
                    });
                    thread.start();
                    System.out.println("a new game started");
                    buff = null;

                }
            }
        } catch (IOException e) {
            System.err.println("server did not turn on!");
            System.exit(-1);
        } catch (ClassNotFoundException e) {
            System.err.println("client sent wrong object");
        }

    }

//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("/View/ServerView.fxml"));
//        Scene scene = new Scene(root , 400 , 400);
//        primaryStage.setResizable(false);
//        primaryStage.setScene(scene);
//        primaryStage.setTitle("Clash Royale Server");
//        primaryStage.getIcons().add(new Image(getClass().getResource("/Pictures/server/teslaTower.png").toString()));
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                startServer();
//            }
//        }).start();
//        primaryStage.show();
//    }
}
