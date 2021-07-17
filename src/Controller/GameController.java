package Controller;

import Model.Cards.Card;
import Model.Cards.Reals.Buildings.Building;
import Model.Cards.Reals.Troops.Troop;
import Model.Cards.Spells.Fireball;
import Model.Cards.Spells.Rage;
import Model.Cards.Spells.Spell;
import Model.Game.Block;
import Model.Game.Command;
import Model.Game.GameElement;
import Model.Game.GameManager;
import Model.Interfaces.HealthHaver;
import Model.Towers.KingTower;
import Model.Towers.Tower;
import Users.Player;
import View.CardView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Callback;

import java.awt.*;
import java.io.FileInputStream;
import java.util.*;
import java.util.List;

public class GameController {
    private double cellWidth;
    private double cellHeight;

    private GameManager model;
    private Player player1;
    private Player player2;
    private ObservableList<Card> cardObservableList;
    private Timer timer;
    private List<GameElement> spells = new ArrayList<>();

    @FXML
    private BorderPane border;

    @FXML
    private Label enemyCrowns;

    @FXML
    private Label yourCrowns;

    @FXML
    private Label enemyUsernameLabel;

    @FXML
    private Label timeLabel;

    @FXML
    private StackPane arenaPane;

    @FXML
    private Pane mapPane;

    @FXML
    private ListView<Card> handListView;

    @FXML
    private ImageView nextCardImage;

    @FXML
    private ProgressBar elixirProgressBar;

    @FXML
    private Label elixirNumber;

    @FXML
    private Pane elementPane;

    @FXML
    void addElement(MouseEvent event) {
        Card selectedCard = handListView.getSelectionModel().getSelectedItem();
        if (selectedCard == null)
            return;
        int x = (int) (event.getX()/cellWidth);
        int y = (int) (event.getY()/cellHeight);
        System.out.println(x + " " + y);

        Command command = new Command(player1, selectedCard, new Point(x, y));
        model.addCommand(command);
    }


    public void initialize(){
        handListView.setCellFactory(new Callback<ListView<Card>, ListCell<Card>>() {
            @Override
            public ListCell<Card> call(ListView<Card> param) {
                return new CardView();
            }
        });

    }

    private void initialMap(){
        try {

            FileInputStream mapFile = new FileInputStream("src/View/map.txt");
            Scanner lineReader = null;
            lineReader = new Scanner(mapFile);

            for (int i = 0; i < 33; i++) {
                String line = lineReader.nextLine();
                for (int j = 0; j < 19; j++) {
                    char[] str = line.toCharArray();
                    //String tileNumber = scanner.next();
                    Character tileNumber = str[2*j];
                    ImageView image = new ImageView(new Image("/Pictures/Tiles/" + tileNumber.toString() + ".png"));
                    image.setX(j * cellWidth);
                    image.setY(i * cellHeight);
                    image.setFitHeight(cellHeight);
                    image.setFitWidth(cellWidth);
                    mapPane.getChildren().add(image);
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setPlayers(Player player1, Player player2){
        this.player1 = player1;
        this.player2 = player2;
        cellWidth = (mapPane.getWidth()/19.0);
        cellHeight = (mapPane.getHeight()/33.0);
        initialMap();
        enemyUsernameLabel.setText(player2.getUsername());
        model = new GameManager(player1, player2);
        updateView();
        startTimer();
    }

    public void startTimer(){
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        update();
                    }
                });
            }
        };
        long frameTimeInMilliseconds = (long) (1000.0 / model.getFps());
        timer.schedule(timerTask,0, frameTimeInMilliseconds);
    }

    public void update(){
        model.update();
        if (model.isGameOver()) {
            timer.cancel();
            enemyCrowns.setText(Integer.toString(model.getSecondPlayerCrown()));
            yourCrowns.setText(Integer.toString(model.getFirstPlayerCrown()));
            return;
        }
        updateView();

    }

    public void showElements(){
//        HashMap<Player, List<GameElement>> gameElementHashMap = model.getPlayerToElementHashMap();
//        List<GameElement> firstPlayerElements = gameElementHashMap.get(player1);
//        for (GameElement element: firstPlayerElements){
//            ImageView imageView = new ImageView(new Image(findImage(element)));
//            imageView.setX(element.getLocation().getX() * cellWidth);
//            imageView.setY(element.getLocation().getY() * cellHeight);
//            System.out.println("element at : " + element.getLocation().getX() + " , " + element.getLocation().getY());
//            imageView.setFitHeight(cellHeight);
//            imageView.setFitWidth(cellWidth);
//            mapPane.getChildren().add(imageView);
////                group.getChildren().add(imageView);
////                canvas.setHeight(arenaPane.getHeight());
////                canvas.setWidth(arenaPane.getWidth());
////
////                GraphicsContext gc = canvas.getGraphicsContext2D();
////                gc.drawImage(new Image(element.getImageAddress().replace("color","red") + "backward.png"), cellWidth * element.getLocation().getX() , cellHeight * element.getLocation().getY() , cellWidth * 3 , cellHeight * 3);
//
//        }
//        List<GameElement> secondPlayerElements = gameElementHashMap.get(player2);
//        for (GameElement element: secondPlayerElements){
//            ImageView imageView = new ImageView(new Image(findImage(element)));
//            imageView.setX(element.getLocation().getX() * cellWidth);
//            imageView.setY(element.getLocation().getY() * cellHeight);
//            imageView.setFitHeight(cellHeight);
//            imageView.setFitWidth(cellWidth);
//            mapPane.getChildren().add(imageView);
//        }


        GameElement[][][] mapArray = model.getMapArray();
        GameElement element = null;
        for (int i = 0; i < 19; i++) {
            for (int j = 0; j < 33; j++) {
                for (int k = 0; k < 2; k++) {
                    element = mapArray[i][j][k];
                    if(element != null){
                        if(!(element instanceof Block)){
                            if (! (element.getGameEntity() instanceof Spell))
                                showNonSpell(element);
                        }
                    }
                }
            }
        }
    }
    private void showNonSpell(GameElement element){
//        ImageView imageView = new ImageView(new Image(findImage(element)));
//        imageView.setX(element.getLocation().getX() * cellWidth);
//        imageView.setY(element.getLocation().getY() * cellHeight);
//        imageView.setFitHeight(cellHeight);
//        imageView.setFitWidth(cellWidth);
//        mapPane.getChildren().add(imageView);
        VBox view = elementView(element);
        view.setLayoutX(element.getLocation().getX() * cellWidth);
        view.setLayoutY(((element.getLocation().getY()) - 0.2) * cellHeight);
        view.setMaxHeight(cellHeight);
        view.setMaxWidth(cellWidth);

        mapPane.getChildren().add(view);

    }

    private VBox elementView(GameElement element){
        VBox vBox = new VBox(1);
        ProgressBar healthBar = new ProgressBar();
        int maxHealth;
        if (element.getGameEntity() instanceof HealthHaver)
            maxHealth = ((HealthHaver)element.getGameEntity()).getHealth();
        else
            return vBox;
        int currentHealth = element.getHealth();
        healthBar.setProgress((double) currentHealth / maxHealth);
        healthBar.setMaxWidth(cellWidth);
        healthBar.getStylesheets().add(getClass().getResource("/Styles/HealthBarStyles.css").toExternalForm());
        if (element.getOwner().equals(player1))
            healthBar.setStyle("-fx-accent: blue;");
        else
            healthBar.setStyle("-fx-accent: red;");
        vBox.getChildren().add(healthBar);
        ImageView imageView = new ImageView(new Image(findImage(element)));
        imageView.setFitHeight(cellHeight);
        imageView.setFitWidth(cellWidth);
        vBox.getChildren().add(imageView);

        return vBox;

    }

    private void showSpells(){
        List<GameElement> activeSpells = model.getActiveSpells();
        for (GameElement element: activeSpells) {
            if (element.getGameEntity() instanceof Rage) {
                Rage rage = (Rage) element.getGameEntity();
                Circle circle = new Circle(rage.getRadius() * cellHeight);
                circle.setCenterX(element.getLocation().getX() * cellWidth + cellWidth/2);
                circle.setCenterY(element.getLocation().getY() * cellHeight + cellHeight/2);
                circle.setFill(Color.rgb(185,86,255,0.5));
                mapPane.getChildren().add(circle);
            }
            else{
                spells.add(element); // arrows and fireball
            }
        }

        Iterator<GameElement> iterator = spells.iterator();
        while (iterator.hasNext()){
            GameElement spellElement = iterator.next();
            if (spellElement.getMadeAtFrame() + 10 == model.getFrameCounter()){
                iterator.remove();
                continue;
            }
            else{
                String imageAddress = "/Pictures/SpellEffects/";
                if (spellElement.getGameEntity() instanceof Fireball){
                    imageAddress += "Fireball.png";
                }
                else{
                    imageAddress += "Arrows.png";
                }

                ImageView imageView = new ImageView(new Image(imageAddress));
                double radius = ((Spell)spellElement.getGameEntity()).getRadius();
                imageView.setX((spellElement.getLocation().getX() - radius ) * cellWidth + cellWidth/2);
                imageView.setY((spellElement.getLocation().getY() - radius ) * cellHeight + cellHeight/2);
                imageView.setFitHeight(cellHeight * radius * 2);
                imageView.setFitWidth(cellWidth * radius * 2);
                mapPane.getChildren().add(imageView);
            }
        }


    }

    public String findImage(GameElement element){
        String imageAddress = element.getImageAddress();
        if (element.getOwner().equals(player1))
            imageAddress = imageAddress.replace("color", "blue");
        else
            imageAddress = imageAddress.replace("color", "red");

        String direction = "";
        if (element.getGameEntity() instanceof Troop){
            switch (element.getDirection()){
                case left:
                    direction = "left";
                    break;
                case right:
                    direction = "right";
                    break;
                case forward:
                    direction = "forward";
                    break;
                case backward:
                    direction = "backward";
            }
            return imageAddress + direction + ".png";
        }
        else if (element.getGameEntity() instanceof Tower){
            if (element.getGameEntity() instanceof KingTower){
                if (((KingTower)element.getGameEntity()).isActiveToShoot())
                    return imageAddress + "active.png";
                else
                    return imageAddress + "idle.png";
            }
            else
                return imageAddress + "PrincesTower.png";
        }
        else if (element.getGameEntity() instanceof Building){
            return imageAddress;
        }
        return null;

    }

    public void updateView(){
//            canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
//            canvas.getGraphicsContext2D().strokeRect(0, 0, canvas.getWidth(), canvas.getHeight());
//            try {
//                Thread.sleep(100);
//
//            }
//            catch (Exception e){
//                e.printStackTrace();
//            }
//        while (elementPane.getChildren().size() > 0) {
//            ((ImageView)elementPane.getChildren().get(1)).setVisible(false);
//            elementPane.getChildren().remove(0);
//            try {
//                Thread.sleep(100);
//            }
//            catch (Exception e){
//
//            }
//        }
//        while (elementPane.getChildren().size()>0)
//            elementPane.getChildren().remove(0);
//        mapPane.getChildren().clear();
//        initialMap();
        while (mapPane.getChildren().size() > 627){
//            ((ImageView) elementPane.getChildren().get(0)).setImage(null);
            mapPane.getChildren().remove(627);
        }


        List<Card> hand = model.getPlayerRandomCardsHashMap().get(player1);
        nextCardImage.setImage(new Image(hand.get(4).getCardImageAddress()));
//        cardList.remove(4);
        List<Card> cardList = new ArrayList<>();
        for (int i = 0; i < 4; i++){
            cardList.add(hand.get(i));
        }

        showElements();
        showSpells();

        cardObservableList = FXCollections.observableList(cardList);
        handListView.setItems(cardObservableList);

        int elixir = model.getFirstPlayerElixir();
        elixirProgressBar.setProgress(elixir/10.0);
        elixirNumber.setText(Integer.toString(elixir));

        enemyCrowns.setText(Integer.toString(model.getSecondPlayerCrown()));
        yourCrowns.setText(Integer.toString(model.getFirstPlayerCrown()));

        long frameCount = 1800 - model.getFrameCounter();
        String seconds = "";
        String minutes = "" + frameCount/600;
        if ((frameCount % 600)/10 < 10)
            seconds = "0" + (frameCount % 600)/10;
        else
            seconds = "" + (frameCount % 600)/10;

        timeLabel.setText("" + minutes + ":" + seconds);

//        model.update();
    }

}
