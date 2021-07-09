package View;

import Model.Cards.Card;
import Model.Stats.Match;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;

public class MatchView extends ListCell<Match> {
    private HBox hBox = new HBox(8.0);
    private Label label = new Label();
    private ImageView image = new ImageView();

    public MatchView(){
        hBox.setAlignment(Pos.CENTER_RIGHT);

        label.setTextAlignment(TextAlignment.CENTER);
        label.setWrapText(true);
        hBox.getChildren().add(label);

        image.setFitHeight(32.0);
        image.setFitWidth(40.0);

        hBox.getChildren().add(image);
        setPrefHeight(USE_COMPUTED_SIZE);
        setPrefWidth(USE_COMPUTED_SIZE);


    }
    @Override
    protected void updateItem(Match item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setGraphic(null); // don't display anything
        }
        else {
            image.setImage(new Image(item.getImageAddress()));
            label.setText(item.toString());
            setGraphic(hBox);
        }
    }
}