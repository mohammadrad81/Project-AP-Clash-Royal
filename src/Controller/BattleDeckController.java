package Controller;

import Model.Cards.Card;
import Users.Player;
import View.CardView;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.ArrayList;

public class BattleDeckController {

//    private ArrayList<Card> cards;
//    private ArrayList<Card> hand;
    private Player player;

    private ObservableList<Card> cards = FXCollections.observableArrayList();
    private ObservableList<Card> hand = FXCollections.observableArrayList();

    @FXML
    private Button backButton;

    @FXML
    private ListView<Card> cardsListView;

    @FXML
    private ListView<Card> handListView;

    @FXML
    void returnToUserMenu(ActionEvent event) throws Exception{
        Stage stage = (Stage) backButton.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/UserMenu.fxml"));
        loader.load();
        UserMenuController controller = loader.getController();
        controller.setPlayer(player);

        Parent root = loader.getRoot();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void initialize(){
//        cardsListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Card>() {
//            @Override
//            public void changed(ObservableValue<? extends Card> observable, Card oldValue, Card newValue) {
////                player.getCards().remove(newValue);
////                player.getHand().add(newValue);
//                Card card = cardsListView.getSelectionModel().getSelectedItem();
//                Platform.runLater(new Runnable() {
//                    @Override
//                    public void run() {
//                        cards.remove(card);
//                        hand.add(card);
//
//                    }
//                });
//
//
//
//            }
//        });
//        handListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Card>() {
//            @Override
//            public void changed(ObservableValue<? extends Card> observable, Card oldValue, Card newValue) {
////                player.getCards().add(newValue);
////                player.getHand().remove(newValue);
//                Card card = handListView.getSelectionModel().getSelectedItem();
//                Platform.runLater(new Runnable() {
//                    @Override
//                    public void run() {
//                        hand.remove(card);
//                        cards.add(card);
//                    }
//                });
//
//
//
//            }
//        });
        cardsListView.setCellFactory(new Callback<ListView<Card>, ListCell<Card>>() {
            @Override
            public ListCell<Card> call(ListView<Card> param) {
                return new CardView();
            }
        });
        handListView.setCellFactory(new Callback<ListView<Card>, ListCell<Card>>() {
            @Override
            public ListCell<Card> call(ListView<Card> param) {
                return new CardView();
            }
        });
    }

    public void setPlayer(Player player){
        this.player = player;
        cards = FXCollections.observableArrayList(player.getCards());
        hand = FXCollections.observableArrayList(player.getHand());
        cardsListView.setItems(cards);
        handListView.setItems(hand);
    }



    @FXML
    void cardsMouseClicked(MouseEvent event) {
        Card card = cardsListView.getSelectionModel().getSelectedItem();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                cards.remove(card);
                hand.add(card);
            }
        });
    }

    @FXML
    void handMouseClicked(MouseEvent event) {
        Card card = handListView.getSelectionModel().getSelectedItem();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                hand.remove(card);
                cards.add(card);
            }
        });
    }

}
