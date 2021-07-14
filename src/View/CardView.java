package View;

import Model.Cards.Card;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CardView extends ListCell<Card> {
    private ImageView image = new ImageView();

    public CardView(){
        image.setFitHeight(90.0);
        image.setFitWidth(80.0);
        setPrefHeight(USE_COMPUTED_SIZE);
        setPrefWidth(USE_COMPUTED_SIZE);


    }
    @Override
    protected void updateItem(Card item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setGraphic(null); // don't display anything
        }
        else {
            image.setImage(new Image(item.getCardImageAddress()));
            setGraphic(image);
        }
    }
}
