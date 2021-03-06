package Controller;

import Users.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;

/**
 * controller for match result page
 * @author Alireza Jabbari Mahalle No
 * @author Mohammad Heydari Rad
 * @version 1.0.0
 */
public class GameResultController {
    private Player player;

    @FXML
    private Label resultLabel;

    /**
     *  back to the user menu page
     * @throws Exception if fxml file not founded (never throws)
     */
    @FXML
    void backToMenu(ActionEvent event) throws Exception{
        Stage stage = (Stage) resultLabel.getScene().getWindow();
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
     * set players and result of game
     * @param player the player
     * @param firstPlayerCrowns number of crowns of first player
     * @param secondPlayerCrown number of crowns of second player
     */
    public void setPlayers(Player player, int firstPlayerCrowns, int secondPlayerCrown){
        this.player = player;
        resultLabel.setText("" + firstPlayerCrowns + " - " + secondPlayerCrown);
    }
}
