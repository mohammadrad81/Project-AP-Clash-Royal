package Controller;

import Users.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class LogInPageController {

    private final String userFileAddress = "./Users/users.bin";

    @FXML
    private Button backButton;

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Button logInButton;

    @FXML
    private Label errorLabel;

    @FXML
    void logIn(ActionEvent event) throws Exception{
        if (usernameTextField.getText().isEmpty()){
            errorLabel.setText("FILL USERNAME FIELD");
            errorLabel.setVisible(true);
            usernameTextField.requestFocus();
            return;
        }

        if (passwordTextField.getText().isEmpty()){
            errorLabel.setText("FILL PASSWORD FIELD");
            errorLabel.setVisible(true);
            passwordTextField.requestFocus();
            return;
        }
        String username = usernameTextField.getText().trim();
        String password = passwordTextField.getText().trim();

        Player player = findPlayer(username, password);

        if (player == null){
            errorLabel.setText("USERNAME OR PASSWORD IS INCORRECT");
            errorLabel.setVisible(true);
            usernameTextField.requestFocus();
            return;
        }

        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/UserMenu.fxml"));
        loader.load();

        UserMenuController userMenuController = loader.getController();
        userMenuController.setPlayer(player);

        Media media = new Media(new File("src/SoundTracks/menu2.mp3").toURI().toString());
        ((MediaPlayer) stage.getUserData()).pause();
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
        stage.setUserData(mediaPlayer);

        Parent root = loader.getRoot();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void returnToMainMenu(ActionEvent event) throws Exception{
        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/View/MainMenu.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private Player findPlayer(String username, String password){
        File users = new File(userFileAddress);
        if (!users.exists())
            return null;

        try(ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(users))){
            while (true){
                Player player = (Player) objectInputStream.readObject();
                if (player == null)
                    break;
                if (player.getUsername().equals(username) && player.getPassword().equals(password))
                    return player;
            }

        }
        catch (IOException e){
            System.out.println("file not found");
        }
        catch (ClassNotFoundException e){

        }
        return null;
    }

}
