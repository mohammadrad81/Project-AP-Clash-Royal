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
import javafx.stage.Stage;

import java.io.*;

public class SignupPageController {

    private final String directoryAddress = "./Users/"; //must append with 'username'.bin

    @FXML
    private Button backButton;

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Button signupButton;

    @FXML
    private Label errorLabel;

    @FXML
    void returnToMainMenu(ActionEvent event) throws Exception{
        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/View/MainMenu.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void signup(ActionEvent event) throws Exception{
        if (usernameTextField.getText().trim().isEmpty()){
            errorLabel.setText("FILL USERNAME FIELD");
            errorLabel.setVisible(true);
            usernameTextField.requestFocus();
            return;
        }
        if (passwordTextField.getText().trim().isEmpty()){
            errorLabel.setText("FILL PASSWORD FIELD");
            errorLabel.setVisible(true);
            passwordTextField.requestFocus();
            return;
        }
        String username = usernameTextField.getText().trim();
        String password = passwordTextField.getText().trim();

        if (usernameExists(username)){
            errorLabel.setText("PLAYER ALREADY EXISTS!");
            errorLabel.setVisible(true);
            usernameTextField.requestFocus();
            return;
        }

        Player player = new Player(username, password);

        addPlayer(player);

        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/View/MainMenu.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();




    }

    private boolean usernameExists(String username) {
        File users = new File(directoryAddress + username + ".bin");
        return users.exists();
    }

    private void addPlayer(Player player){
        File file = new File(directoryAddress + player.getUsername() + ".bin");
        if (!file.exists()){
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();

            }
            catch (IOException e){
                System.out.println("cannot create file");
            }
        }
        try (FileOutputStream fileOutputStream = new FileOutputStream(file);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)){

            objectOutputStream.writeObject(player);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

}