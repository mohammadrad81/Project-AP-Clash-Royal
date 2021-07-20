package Controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * controller for main menu page
 */
public class MainMenuController {
    /**
     * exit the program
     * @param event click on exit button
     */
    @FXML
    void exit(ActionEvent event) {
        System.exit(0);
    }

    /**
     * change page to developers page
     * @param event click on developers button
     * @throws IOException if fxml file not founded (never throws)
     */
    @FXML
    void goDevelopersDetailPage(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/View/DevelopersDetails.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * change page to login page
     * @param event click on login button
     * @throws Exception if fxml file not founded (never throws)
     */
    @FXML
    void goLogInPage(ActionEvent event) throws Exception{
        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/View/LogInPage.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * change page to the signUp page
     * @param event click on the signUp button
     * @throws Exception if fxml file not founded (never throws)
     */
    @FXML
    void goSignUpPage(ActionEvent event) throws Exception{
        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/View/SignUpPage.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
