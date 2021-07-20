package Controller;

import Model.Cards.Card;
import Model.Game.Command;
import Model.Game.GameManager;
import Model.Stats.Match;
import Users.Player;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class OneVOneGameController extends GameController{
    private ObjectInputStream in;
    private ObjectOutputStream out;

    private boolean gameOver = false;

    @FXML
    void addElement(MouseEvent event) {
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
            e.printStackTrace();
        }
    }

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

    @Override
    public void update() {
        Object object = null;
        try {
            object = in.readObject();
        }
        catch (Exception e){
            e.printStackTrace();
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
                e.printStackTrace();
                System.exit(-1);
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
    public void setPlayer(Player player , Socket socket ,
                          ObjectOutputStream out ,
                          ObjectInputStream in){

        this.player1 = player;
        this.out = out;
        this.in = in;
        cellWidth = (mapPane.getWidth()/19.0);
        cellHeight = (mapPane.getHeight()/33.0);

        Stage stage = (Stage) enemyUsernameLabel.getScene().getWindow();
        Media media = new Media(new File("src/SoundTracks/battle1.mp3").toURI().toString());
        ((MediaPlayer) stage.getUserData()).pause();
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
        stage.setUserData(mediaPlayer);

        initialMap();

        try {
            model = (GameManager) in.readObject();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        updateView();
        startTimer();

    }



}
