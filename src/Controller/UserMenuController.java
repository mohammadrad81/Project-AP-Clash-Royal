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
import java.io.IOException;

public class UserMenuController {

    private Player player;

    @FXML
    private Label welcomeLabel;

    @FXML
    private Label errorLabel;

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

    @FXML
    void goBattleDeck(ActionEvent event) throws Exception{
        Stage stage = (Stage) welcomeLabel.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/BattleDeck.fxml"));
        loader.load();
        BattleDeckController controller = loader.getController();
        controller.setPlayer(player);

        Parent root = loader.getRoot();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void goBattleHistory(ActionEvent event) throws Exception{
        Stage stage = (Stage) welcomeLabel.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/BattleHistory.fxml"));
        loader.load();
        BattleHistoryController controller = loader.getController();
        controller.setPlayer(player);

        Parent root = loader.getRoot();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void setPlayer(Player player){
        this.player = player;
        welcomeLabel.setText("WELCOME " + player.getUsername());
    }

    @FXML
    void goProfilePage(ActionEvent event) throws IOException {
        Stage stage = (Stage) welcomeLabel.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/View/profilePage.fxml"));
        fxmlLoader.load();
        ProfilePageController controller = fxmlLoader.getController();
        controller.setPlayer(player);

        Parent root = fxmlLoader.getRoot();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void goTrainingCamp(ActionEvent event) throws Exception{
        if (!validHand())
            return;
        Stage stage = (Stage) welcomeLabel.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/View/SelectDifficulty.fxml"));
        fxmlLoader.load();
        SelectDifficultyController controller = fxmlLoader.getController();
        controller.setPlayer(player);

        Parent root = fxmlLoader.getRoot();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void goWaitingPage(ActionEvent event) throws IOException {
        if(!validHand())
            return;
        Stage stage = (Stage) welcomeLabel.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/WaitingPage.fxml"));
        loader.load();
        WaitingPageController controller = loader.getController();
        controller.setPlayer(player);

        Parent root = loader.getRoot();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private boolean validHand(){
        if (player.getHand().size() < 8){
            errorLabel.setText("FIRST SET HAND IN BATTLE DECK");
            errorLabel.setVisible(true);
            return false;
        }
        else
            return true;
    }


}

