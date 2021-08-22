package Controller;

import Network.IpPort;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ConnectionController {

    @FXML
    private TextField ipTextField;

    @FXML
    private TextField portTextField;

    @FXML
    private Label errorLabel;

    @FXML
    private Label successfulSetLabel;

    @FXML
    void backToMainMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/MainMenu.fxml"));
        Stage stage = (Stage) ipTextField.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void submitAddresses(ActionEvent event) {
        String ip = ipTextField.getText();
        int port;
        try{
            port = Integer.parseInt(portTextField.getText());
        }catch (NumberFormatException e){
            errorLabel.setVisible(true);
            successfulSetLabel.setVisible(false);
            return;
        }
        IpPort ipPort = IpPort.getInstance();
        ipPort.setPort(port);
        ipPort.setIp(ip);
        errorLabel.setVisible(false);
        successfulSetLabel.setVisible(true);
        return;
    }

}
