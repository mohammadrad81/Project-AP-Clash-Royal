import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;

/**
 * main class for the game
 * @author Alireza Jabbari Mahalle No
 * @author Mohammad Heydari Rad
 * @version 1.0.0
 */
public class Main extends Application {
    /**
     * the start method to open the main menu page
     * @param primaryStage
     * @throws Exception for io exception for loading the fxml file
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/View/MainMenu.fxml"));
        primaryStage.setTitle("Clash Royale");
        Media media = new Media(getClass().getResource("/SoundTracks/menu.mp3").toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
        primaryStage.setUserData(mediaPlayer);
        primaryStage.getIcons().add(new Image(getClass().getResource("/Pictures/icon.png").toString()));
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /**
     * main method
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
