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

import java.awt.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class OneVOneGameController extends GameController{
    private ObjectInputStream in;
    private ObjectOutputStream out;

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
                while (true)
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
            return;
        }
        if (object instanceof GameManager){
            model = (GameManager) object;
        }
        else if (object instanceof Match){ // game ended
            Match matchResult = (Match) object;
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



    private void connectToServer(){
        try {
            Socket socket = new Socket("127.0.0.1", 8989);
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void setPlayer(Player player){
        this.player1 = player;
        cellWidth = (mapPane.getWidth()/19.0);
        cellHeight = (mapPane.getHeight()/33.0);
        enemyUsernameLabel.setText("WAITING FOR OPPONENT");
        connectToServer();
        initialMap();

        try {
            out.writeObject(player);
            model = (GameManager) in.readObject();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        updateView();
        startTimer();

    }



}
