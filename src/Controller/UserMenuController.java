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

/**
 * controller for user menu page
 */
public class UserMenuController {

    private Player player;

    @FXML
    private Label welcomeLabel;

    @FXML
    private Label errorLabel;

    @FXML
    private Button logoutButton;

    /**
     * back to the main menu page
     * @param event click on logOut button
     * @throws Exception if fxml file not founded (never throws)
     */
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

    /**
     * go to battle deck page
     * @param event click on battle deck button
     * @throws Exception if fxml file not founded (never throws)
     */
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

    /**
     * go to the battle history page
     * @param event click on battle history button
     * @throws Exception if fxml file not founded (never throws)
     */
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

    /**
     * set player for controller
     * @param player the player
     */
    public void setPlayer(Player player){
        this.player = player;
        welcomeLabel.setText("WELCOME " + player.getUsername());
    }

    /**
     * go to the profile page
     * @param event click on profile button
     * @throws IOException if fxml file not founded (never throws)
     */
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

    /**
     * go to training camp page (select difficulty of bot)
     * @param event click on training camp button
     * @throws Exception
     */
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

    /**
     * check number of card of player in its hand
     * @return true if player have 8 cards, otherwise false
     */
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

