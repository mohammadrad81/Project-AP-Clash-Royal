package Controller;

import Model.Cards.Card;
import Model.Cards.Reals.Buildings.Building;
import Model.Cards.Reals.Troops.Troop;
import Model.Cards.Spells.Fireball;
import Model.Cards.Spells.Rage;
import Model.Cards.Spells.Spell;
import Model.Game.*;
import Model.Interfaces.HealthHaver;
import Model.Stats.Match;
import Model.Towers.KingTower;
import Model.Towers.Tower;
import Users.Player;
import View.CardView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class GameController {
    protected double cellWidth;
    protected double cellHeight;

    protected GameManager model;
    protected Player player1;
    private Player player2;
    private ObservableList<Card> cardObservableList;
    protected Timer timer;
    private List<GameElement> spells = new ArrayList<>();
    private List<Shoot> shootList = new ArrayList<>();

    private final String directoryAddress = "./Users/"; //must append with 'username'.bin

    @FXML
    private BorderPane border;

    @FXML
    private Label enemyCrowns;

    @FXML
    private Label yourCrowns;

    @FXML
    protected Label enemyUsernameLabel;

    @FXML
    private Label timeLabel;

    @FXML
    private StackPane arenaPane;

    @FXML
    protected Pane mapPane;

    @FXML
    protected ListView<Card> handListView;

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

    protected void initialMap(){
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
//        timer = new Timer();
//        TimerTask timerTask = new TimerTask() {
//            @Override
//            public void run() {
//                Platform.runLater(new Runnable() {
//                    @Override
//                    public void run() {
//                        update();
//                    }
//                });
//            }
//        };
//        long frameTimeInMilliseconds = (long) (1000.0 / model.getFps());
//        timer.schedule(timerTask,0, frameTimeInMilliseconds);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (true)
                    update();
            }
        };
        Thread thread = new Thread(runnable);
//        thread.setDaemon(true);
        thread.start();
    }

    public void update(){
        model.update();
        if (model.isGameOver()) {
            timer.cancel();
            enemyCrowns.setText(Integer.toString(model.getSecondPlayerCrown()));
            yourCrowns.setText(Integer.toString(model.getFirstPlayerCrown()));

            goResultPage(model.gameResult());
            return;
        }
        if (model.getFrameCounter() == 1200){
            changeMusic("battle2.mp3");
        }
        updateView();

    }

    protected void goResultPage(Match matchResult) {
//        Match matchResult = model.gameResult();
        player1.addMatchToHistory(matchResult);
//        player2.addMatchToHistory(matchResult);

        String address = "/View/";
        int firstPlayerCrowns;
        int secondPlayerCrowns;
        if (matchResult.getWinnerName().equals(player1.getUsername())){
            firstPlayerCrowns = matchResult.getWinnerCrown();
            secondPlayerCrowns = matchResult.getLooserCrown();

            address += "VictoryPage.fxml";
            changeMusic("victory.mp3");
            player1.setXp(player1.getXp() + 200);
        }
        else {
            firstPlayerCrowns = matchResult.getLooserCrown();;
            secondPlayerCrowns = matchResult.getWinnerCrown();
            address += "DefeatPage.fxml";
            changeMusic("defeat.mp3");
            player1.setXp(player1.getXp() + 70);
        }

        saveChanges();

        Stage stage = (Stage) border.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(address));

        try {
            fxmlLoader.load();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        Parent root = fxmlLoader.getRoot();
        GameResultController controller = fxmlLoader.getController();
        controller.setPlayers(player1, firstPlayerCrowns, secondPlayerCrowns);

        Scene scene = new Scene(root,500,900);
        stage.setScene(scene);
        stage.show();


    }

    private void saveChanges(){
        File file = new File(directoryAddress + player1.getUsername() + ".bin");

        try (FileOutputStream fileOutputStream = new FileOutputStream(file);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)){

            objectOutputStream.writeObject(player1);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    protected void changeMusic(String name){
        Stage stage = (Stage) border.getScene().getWindow();
        Media media = new Media(new File("src/SoundTracks/" + name).toURI().toString());
        ((MediaPlayer) stage.getUserData()).pause();
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
        stage.setUserData(mediaPlayer);
    }

    public void showElements(){

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

        VBox view = elementView(element);
        view.setLayoutX(element.getLocation().getX() * cellWidth);
        view.setLayoutY(((element.getLocation().getY()) - 0.2) * cellHeight);
        view.setMaxHeight(cellHeight);
        view.setMaxWidth(cellWidth);

        mapPane.getChildren().add(view);

    }

    private void showShoots(){

        List<Shoot> shoots = model.getShoots();
        for (Shoot shoot: shoots){
            shootList.add(shoot);
        }

        Iterator<Shoot> iterator = shootList.iterator();
        while (iterator.hasNext()){
            Shoot shoot = iterator.next();
            if (shoot.getMadeAtFrame() + 4 == model.getFrameCounter()) {
                iterator.remove();
                continue;
            }
            ImageView imageView = new ImageView(new Image("/Pictures/SpellEffects/Fireball.png"));
            imageView.setFitWidth(cellWidth);
            imageView.setFitHeight(cellHeight);
            long delta = model.getFrameCounter() - shoot.getMadeAtFrame();
            double x = shoot.getSrc().getX() + (delta * (shoot.getDest().getX() - shoot.getSrc().getX()))/4.0;
            double y = shoot.getSrc().getY() + (delta * (shoot.getDest().getY() - shoot.getSrc().getY()))/4.0;
            imageView.setX(x * cellWidth);
            imageView.setY(y * cellHeight);

            mapPane.getChildren().add(imageView);

        }

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

    private <T> boolean isSameTypeList(List<T> first , List<T> second){
        if(first == null || second == null){
            return false;
        }
        if(first.size() != second.size()){
            return false;
        }
        else{
            for (int i = 0; i < first.size(); i++) {
                if(!first.get(i).getClass().equals(second.get(i).getClass())){
                    return false;
                }
            }
        }
        return true;
    }

    protected void updateView(){

        while (mapPane.getChildren().size() > 627){
            mapPane.getChildren().remove(627);
        }

        showShoots();


        List<Card> hand = model.getPlayerRandomCardsHashMap().get(player1);
        nextCardImage.setImage(new Image(hand.get(4).getCardImageAddress()));
        List<Card> cardList = new ArrayList<>();
        for (int i = 0; i < 4; i++){
            cardList.add(hand.get(i));
        }

        showElements();
        showSpells();
        if(! isSameTypeList(cardObservableList , cardList)){
            cardObservableList = FXCollections.observableList(cardList);
            handListView.setItems(cardObservableList);
        }
        int elixir = model.getFirstPlayerElixir();
        elixirProgressBar.setProgress(elixir/10.0);
        elixirNumber.setText(Integer.toString(elixir));

        enemyUsernameLabel.setText(model.getSecondPlayer().getUsername());

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

//        System.out.println("" + minutes + ":" + seconds);
        System.out.println(model.getFrameCounter());

    }

    public void setPlayer(Player player){

    }

}
