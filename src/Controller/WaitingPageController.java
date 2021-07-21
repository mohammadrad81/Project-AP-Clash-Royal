package Controller;


import Users.Player;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * controller for waiting for server and opponent page
 * @author Alireza Jabbari Mahalle No
 * @author Mohammad Heydari Rad
 * @version 1.0.0
 */
public class WaitingPageController {
    private Player player;
    private Socket socket;
    private Thread connector;
    private static final String ip = "127.0.0.1";
    private static final int port = 8989;

    @FXML
    private Label infoLabel;

    /**
     * returns back to the user menu page
     * @param event
     * @throws IOException
     */
    @FXML
    void backToUserMenu(ActionEvent event) throws IOException {
        connector.interrupt();
        Stage stage = (Stage) infoLabel.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/View/UserMenu.fxml"));
        fxmlLoader.load();
        UserMenuController controller = fxmlLoader.getController();
        controller.setPlayer(player);
        Parent root = fxmlLoader.getRoot();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * sets the player , and waits for the connection to the server
     * @param player is the player who plays the game
     */
    public void setPlayer(Player player){
        this.player = player;

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                infoLabel.setText("waiting to connect to server...");
            }
        });

        connector = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!connector.isInterrupted() && socket == null){
                    try {
                        Thread.sleep(500);
                        socket = new Socket(ip , port);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                infoLabel.setText("waiting for opponent to join...");
                            }
                        });
                    } catch (IOException e) {
                        //waiting for connection
                    } catch (InterruptedException e) {
                        break;
                    }
                }
                ObjectOutputStream out = null;
                ObjectInputStream in = null;
                try {
                    out = new ObjectOutputStream(socket.getOutputStream());
                    in = new ObjectInputStream(socket.getInputStream());
                    out.writeObject(player);
                    in.readObject();
                    goToOnlineGame(out , in);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });
        connector.setDaemon(true);
        connector.start();
    }

    /**
     * goes to online game with the opponent
     * @param out is the outputStream to the server
     * @param in is the inputStream to the server
     */
    private void goToOnlineGame(ObjectOutputStream out , ObjectInputStream in){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Stage stage =(Stage) infoLabel.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/View/OneVOneGameView.fxml"));
                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                OneVOneGameController controller = loader.getController();

                Parent root = loader.getRoot();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                controller.setPlayer(player , socket , out , in);
            }
        });
    }

}
