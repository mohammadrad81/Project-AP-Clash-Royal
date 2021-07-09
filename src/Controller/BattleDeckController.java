package Controller;

import Model.Cards.Card;
import Users.Player;
import View.CardView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class BattleDeckController {

    private Player player;
    private final String directoryAddress = "./Users/"; //must append with 'username'.bin
    private boolean deckChanged = false;

    private ObservableList<Card> cards = FXCollections.observableArrayList();
    private ObservableList<Card> hand = FXCollections.observableArrayList();

    @FXML
    private Button backButton;

    @FXML
    private ListView<Card> cardsListView;

    @FXML
    private ListView<Card> handListView;

    @FXML
    private Label selectedCardsLabel;

    @FXML
    void returnToUserMenu(ActionEvent event) throws Exception{
        if (deckChanged)
            saveChanges();

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
                if (hand.size() >= 8)
                    return;
                deckChanged = true;
                player.getCards().remove(card);
                player.getHand().add(card);
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
                deckChanged = true;
                player.getCards().add(card);
                player.getHand().remove(card);
                hand.remove(card);
                cards.add(card);
            }
        });
    }
    private void saveChanges(){
        File file = new File(directoryAddress + player.getUsername() + ".bin");

        try (FileOutputStream fileOutputStream = new FileOutputStream(file);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)){

            objectOutputStream.writeObject(player);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

}
