package Controller;

import Users.Bots.IdiotBot;
import Users.Bots.IntelligentBot;
import Users.Bots.SmartBot;
import Users.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;

/**
 * controller class for select difficulty of bot page
 */
public class SelectDifficultyController {
    private Player player;

    @FXML
    private Button backButton;

    /**
     * back to the user menu page
     * @param event click on the back button
     * @throws Exception if fxml file not founded (never throws)
     */
    @FXML
    void returnToUserMenu(ActionEvent event) throws Exception{
        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/UserMenu.fxml"));
        loader.load();

        UserMenuController userMenuController = loader.getController();
        userMenuController.setPlayer(player);

        Parent root = loader.getRoot();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * start a game with idiot bot (easy level)
     * @param event click on idiot bot button
     * @throws Exception if fxml file not founded (never throws)
     */
    @FXML
    void startGameIdiotBot(ActionEvent event) throws Exception{
        startGame(player, new IdiotBot(player.getLevel()));
    }

    /**
     * start a game with smart bot (normal level)
     * @param event click on smart bot button
     * @throws Exception if fxml file not founded (never throws)
     */
    @FXML
    void startGameSmartBot(ActionEvent event) throws Exception{
        startGame(player,new SmartBot(player.getLevel()));
    }
    /**
     * start a game with intelligent bot (hard level)
     * @param event click on intelligent bot button
     * @throws Exception if fxml file not founded (never throws)
     */
    @FXML
    void startGameIntelligentBot(ActionEvent event) throws Exception {
        startGame(player , new IntelligentBot(player.getLevel()));
    }

    /**
     * set player for controller
     * @param player the player
     */
    public void setPlayer(Player player){
        this.player = player;
    }

    /**
     * start a game with given player and bot
     * @param user the player
     * @param bot the chosen bot
     * @throws Exception if fxml file not founded (never throws)
     */
    private void startGame(Player user, Player bot) throws Exception{
        Stage stage = (Stage) backButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/View/GameView.fxml"));
        fxmlLoader.load();

        Media media = new Media(new File("src/SoundTracks/battle1.mp3").toURI().toString());
        ((MediaPlayer) stage.getUserData()).pause();
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
        stage.setUserData(mediaPlayer);

        Parent root = fxmlLoader.getRoot();
        Scene scene = new Scene(root,600,1000);
        stage.setScene(scene);
        stage.show();

        GameController controller = fxmlLoader.getController();
        controller.setPlayers(user, bot);
    }

}
