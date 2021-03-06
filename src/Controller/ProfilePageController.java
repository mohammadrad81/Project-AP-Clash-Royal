package Controller;

import Model.Cards.Card;
import Users.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;


import java.io.File;
import java.io.IOException;

/**
 * controller for profile page
 * this page shows player stats
 * @author Alireza Jabbari Mahalle No
 * @author Mohammad Heydari Rad
 * @version 1.0.0
 */
public class ProfilePageController {
    private Player player;
    @FXML
    private Button backButton;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label levelLabel;

    @FXML
    private Label xpLabel;

    @FXML
    private Label matchesLabel;

    @FXML
    private Label wonMatchesLabel;

    @FXML
    private FlowPane cardPicturesFlowPane;

    /**
     * back to the user menu page
     * @param event click on the back
     * @throws IOException if fxml file not founded (never throws)
     */
    @FXML
    void returnToUserMenu(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/UserMenu.fxml"));
        loader.load();

        UserMenuController userMenuController = loader.getController();
        userMenuController.setPlayer(player);

        Media media = new Media(getClass().getResource("/SoundTracks/menu2.mp3").toString());
        ((MediaPlayer) stage.getUserData()).pause();
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
        stage.setUserData(mediaPlayer);

        Parent root = loader.getRoot();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * set player for controller
     * @param player the player
     */
    public void setPlayer(Player player){
        this.player = player;
        usernameLabel.setText(usernameLabel.getText() + player.getUsername());
        levelLabel.setText(levelLabel.getText() + player.getLevel());
        xpLabel.setText(xpLabel.getText() + player.getXp());
        matchesLabel.setText(matchesLabel.getText() + player.getHistory().size());
        wonMatchesLabel.setText(wonMatchesLabel.getText() + player.getCountOfWonMatches());

        showPlayerHand();
    }

    /**
     * shows hand cards of player
     */
    private void showPlayerHand(){
        for(Card card : player.getHand()){
            cardPicturesFlowPane.getChildren().add(cardImageView(card));
        }
    }

    /**
     * receive view for card
     * @param card the card to be shown
     * @return imageView of card
     */
    private ImageView cardImageView(Card card){
        ImageView image = new ImageView();
        image.setFitHeight(120);
        image.setFitWidth(80);
        image.setImage(new Image(card.getCardImageAddress()));
        return image;
    }

}
