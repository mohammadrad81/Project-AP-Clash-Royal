package Controller;

import Model.Cards.Card;
import Model.Game.Command;
import Model.Game.GameManager;
import Users.Player;
import View.CardView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class GameController {
    private double cellWidth;
    private double cellHeight;

    private GameManager model;
    private Player player1;
    private Player player2;
    private ObservableList<Card> cardObservableList;
    private Timer timer;

    @FXML
    private BorderPane border;

    @FXML
    private Label enemyCrowns;

    @FXML
    private Label yourCrowns;

    @FXML
    private Label enemyUsernameLabel;

    @FXML
    private Label timeLabel;

    @FXML
    private StackPane arenaPane;

    @FXML
    private Pane mapPane;

    @FXML
    private ListView<Card> handListView;

    @FXML
    private ImageView nextCardImage;

    @FXML
    private ProgressBar elixirProgressBar;

    @FXML
    private Label elixirNumber;

    @FXML
    void addElement(MouseEvent event) {
        Card selectedCard = handListView.getSelectionModel().getSelectedItem();
        if (selectedCard == null)
            return;
        int x = (int) (event.getX()/cellWidth);
        int y = (int) (event.getY()/cellHeight);
        System.out.println(x + " " + y);

        Command command = new Command(player1, selectedCard, new Point(x, y));
        if (model.isCommandAreaAllowed(command))
            model.addCommand(command);
        model.update();
        updateView();
    }


    public void initialize(){
        handListView.setCellFactory(new Callback<ListView<Card>, ListCell<Card>>() {
            @Override
            public ListCell<Card> call(ListView<Card> param) {
                return new CardView();
            }
        });

    }

    private void initialMap(){
        for (int i = 0; i < 19; i++){
            for (int j = 0; j < 33; j++){
                ImageView image = new ImageView(new Image("/Pictures/Tiles/1.png"));
                image.setX(i*cellWidth);
                image.setY(j*cellHeight);
                image.setFitHeight(cellHeight);
                image.setFitWidth(cellWidth);
                mapPane.getChildren().add(image);
            }
        }
    }

    public void setPlayers(Player player1, Player player2){
        this.player1 = player1;
        this.player2 = player2;
        cellWidth = (mapPane.getWidth()/19.0);
        cellHeight = (mapPane.getHeight()/33.0);
        initialMap();
        enemyUsernameLabel.setText(player2.getUsername());
        model = new GameManager(player1, player2);
        updateView();
        startTimer();
    }

    public void startTimer(){
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        update();
                    }
                });
            }
        };
        long frameTimeInMilliseconds = (long) (1000.0 / model.getFps());
        timer.schedule(timerTask,0, frameTimeInMilliseconds);
    }

    public void update(){
        model.update();
        updateView();
        if (model.isGameOver())
            timer.cancel();
    }

    public void updateView(){
        List<Card> hand = model.getPlayerRandomCardsHashMap().get(player1);
        nextCardImage.setImage(new Image(hand.get(4).getCardImageAddress()));
//        cardList.remove(4);
        List<Card> cardList = new ArrayList<>();
        for (int i = 0; i < 4; i++){
            cardList.add(hand.get(i));
        }

        cardObservableList = FXCollections.observableList(cardList);
        handListView.setItems(cardObservableList);

        int elixir = model.getFirstPlayerElixir();
        elixirProgressBar.setProgress(elixir/10.0);
        elixirNumber.setText(Integer.toString(elixir));

        enemyCrowns.setText(Integer.toString(model.getSecondPlayerCrown()));
        yourCrowns.setText(Integer.toString(model.getFirstPlayerCrown()));

        long frameCount = 1800 - model.getFrameCounter();
        String seconds = "";
        String minutes = "" + frameCount/600;
        if ((frameCount % 600)/10 < 10)
            seconds = "0" + (frameCount % 600)/10;
        else
            seconds = "" + (frameCount % 600)/10;

        timeLabel.setText("" + minutes + ":" + seconds);

//        model.update();
    }

}
