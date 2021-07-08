package Controller;

import Users.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;

public class UserMenuController {

    private Player player;

    @FXML
    private Label welcomeLabel;

    @FXML
    private Button logoutButton;

    @FXML
    void returnToMainMenu(ActionEvent event) throws Exception{
        Stage stage = (Stage) welcomeLabel.getScene().getWindow();
        Media media = new Media(new File("src/SoundTracks/menu.mp3").toURI().toString());
        ((MediaPlayer) stage.getUserData()).pause();
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
        stage.setUserData(mediaPlayer);

        Parent root = FXMLLoader.load(getClass().getResource("/View/MainMenu.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void setPlayer(Player player){
        this.player = player;
        welcomeLabel.setText("WELCOME " + player.getUsername());
    }


}

