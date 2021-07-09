package Controller;

import Model.Stats.Match;
import Users.Player;
import View.MatchView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Callback;

public class BattleHistoryController {

    private Player player;
    private ObservableList<Match> matches;

    @FXML
    private Button backButton;

    @FXML
    private ListView<Match> matchesListView;

    @FXML
    void returnToUserMenu(ActionEvent event) throws Exception{
        Stage stage = (Stage) backButton.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/UserMenu.fxml"));
        loader.load();
        UserMenuController controller = loader.getController();
        controller.setPlayer(player);

        Parent root = loader.getRoot();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void initialize(){
        matchesListView.setCellFactory(new Callback<ListView<Match>, ListCell<Match>>() {
            @Override
            public ListCell<Match> call(ListView<Match> param) {
                return new MatchView(player.getUsername());
            }
        });
    }


    public void setPlayer(Player player) {
        this.player = player;
        matches = FXCollections.observableArrayList(player.getHistory());
        matchesListView.setItems(matches);
    }
}