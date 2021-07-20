package Controller;

import Users.Bots.IdiotBot;
import Users.Player;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;

public class Test2 extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/WaitingPage.fxml"));


        Parent root = loader.load();
        WaitingPageController controller = loader.getController();
        primaryStage.setTitle("Clash Royale");
        Media media = new Media(new File("src/SoundTracks/menu.mp3").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
        primaryStage.setUserData(mediaPlayer);
        primaryStage.getIcons().add(new Image("/Pictures/icon.png"));
        primaryStage.setResizable(false);

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
//        GameController controller = loader.getController();
//        controller.setPlayers(new Player("Ali",1), new IdiotBot(1));

        controller.setPlayer(new Player("Karim",1));


    }


    public static void main(String[] args) {
        launch(args);
    }
}