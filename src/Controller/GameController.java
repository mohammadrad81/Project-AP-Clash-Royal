package Controller;

import Model.Cards.Card;
import Model.Game.GameManager;
import Users.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class GameController {
    private GameManager model;
    private Player player1;
    private Player player2;

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
    private ListView<Card> handListView;

    @FXML
    private ProgressBar elixirProgressBar;

    public void initialize(){
        arenaPane.setScaleX(19);
        arenaPane.setScaleX(33);

    }

//    private void initialMap(){
//        for (int i = 0; i < 19; i++){
//            for (int j = 0; j < 33; j++){
//                ImageView image = new ImageView(new Image())
//            }
//        }
//    }

}
