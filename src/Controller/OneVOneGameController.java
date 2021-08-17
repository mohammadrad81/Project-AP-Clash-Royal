package Controller;

import Model.Cards.Card;
import Model.Game.Command;
import Model.Game.GameManager;
import Model.Stats.Match;
import Users.Player;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * class for 1 v 1 game
 * @author Alireza Jabbari Mahalle No
 * @author Mohammad Heydari Rad
 * @version 1.0.0
 */
public class OneVOneGameController extends GameController{
    private ObjectInputStream in;
    private ObjectOutputStream out;

    private boolean gameOver = false;

    /**
     * by the chosen card and the chosen location by the player
     * sends a command to server to play the card
     * @param event click on map pane
     */
    @FXML
    void addElement(MouseEvent event) {
        if (gameOver)
            return;
        Card selectedCard = handListView.getSelectionModel().getSelectedItem();
        if (selectedCard == null)
            return;
        int x = (int) (event.getX()/cellWidth);
        int y = (int) (event.getY()/cellHeight);
        System.out.println(x + " " + y);

        Command command = new Command(player1, selectedCard, new Point(x, y));
        try {
            out.writeObject(command);
        }
        catch (Exception e){
            gameOver = true;
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    disconnect();
                }
            });
            return;
        }
    }

    /**
     * updates the game concurrently
     */
    @Override
    public void startTimer() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (!gameOver)
                    update();

            }
        };
        Thread thread = new Thread(runnable);
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * updates the game
     */
    @Override
    public void update() {
        Object object = null;
        try {
            object = in.readObject();
        }
        catch (Exception e){
            gameOver = true;
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    disconnect();
                }
            });
            return;
        }
        if (object instanceof GameManager){
            model = (GameManager) object;
        }
        else if (object instanceof Match){ // game ended
            Match matchResult = (Match) object;
            gameOver = true;
//            timer.cancel();
            try {
                out.writeObject(null);
            }
            catch (Exception e){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        disconnect();
                    }
                });
                return;
            }
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    goResultPage(matchResult);
                }
            });

            return;
        }

        if (model.getFrameCounter() == 1200)
            changeMusic("battle2.mp3");

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                updateView();
            }
        });
//        System.gc();
//        Runtime.getRuntime().gc();
//        Runtime.getRuntime().gc();
    }



//    private void connectToServer(){
//        try {
////            Socket socket = new Socket("127.0.0.1", 8989);
////            in = new ObjectInputStream(socket.getInputStream());
////            out = new ObjectOutputStream(socket.getOutputStream());
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//    @Override

    /**
     * sets the player , the connection stuff of the player
     * @param player the player
     * @param socket the socket to server
     * @param out the outputStream to server
     * @param in the inputStream to server
     */
    public void setPlayer(Player player , Socket socket ,
                          ObjectOutputStream out ,
                          ObjectInputStream in){

        this.player1 = player;
        this.out = out;
        this.in = in;
        cellWidth = (mapPane.getWidth()/19.0);
        cellHeight = (mapPane.getHeight()/33.0);

        Stage stage = (Stage) enemyUsernameLabel.getScene().getWindow();
        Media media = new Media(getClass().getResource("/SoundTracks/battle1.mp3").toString());
        ((MediaPlayer) stage.getUserData()).pause();
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
        stage.setUserData(mediaPlayer);

        initialMap();

        try {
            model = (GameManager) in.readObject();
        }
        catch (Exception e){
            gameOver = true;
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    disconnect();
                }
            });

            return;
        }
        updateView();
        startTimer();

    }

    private void disconnect(){
        Stage stage = (Stage) enemyUsernameLabel.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/View/UserMenu.fxml"));
        try {
            fxmlLoader.load();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        UserMenuController controller = fxmlLoader.getController();
        controller.setPlayer(player1);

        Media media = new Media(getClass().getResource("/SoundTracks/menu2.mp3").toString());
        ((MediaPlayer) stage.getUserData()).pause();
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
        stage.setUserData(mediaPlayer);

        Parent root = fxmlLoader.getRoot();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }



}
