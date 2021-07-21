package View;

import Model.Cards.Card;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * class for view of card in list view
 * @author Alireza Jabbari Mahalle No
 * @author Mohammad Heydari Rad
 * @version 1.0.0
 */
public class CardView extends ListCell<Card> {
    private ImageView image = new ImageView();

    /**
     * constructor for card view
     */
    public CardView(){
        image.setFitHeight(90.0);
        image.setFitWidth(80.0);
        setPrefHeight(USE_COMPUTED_SIZE);
        setPrefWidth(USE_COMPUTED_SIZE);


    }

    /**
     * override updateItem method of ListCell
     * @param item is the item
     * @param empty true if it is empty , else false
     */
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
