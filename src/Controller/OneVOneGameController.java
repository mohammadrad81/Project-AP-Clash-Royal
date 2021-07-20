package Controller;

import Model.Game.GameManager;
import Model.Stats.Match;
import Users.Player;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class OneVOneGameController extends GameController{
    private ObjectInputStream in;
    private ObjectOutputStream out;

    @Override
    public void update() {
        Object object = null;
        try {
            object = in.readObject();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        if (object instanceof GameManager){
            model = (GameManager) object;
        }
        else if (object instanceof Match){ // game ended
            Match matchResult = (Match) object;
            timer.cancel();
            try {
                out.writeObject(null);
            }
            catch (Exception e){
                e.printStackTrace();
            }

            goResultPage(matchResult);
            return;
        }

        if (model.getFrameCounter() == 1200)
            changeMusic("battle2.mp3");

        updateView();

    }

    private void connectToServer(){
        try {
            Socket socket = new Socket("127.0.0.1", 8989);
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void setPlayer(Player player){
        this.player1 = player;
        cellWidth = (mapPane.getWidth()/19.0);
        cellHeight = (mapPane.getHeight()/33.0);
        enemyUsernameLabel.setText("WAITING FOR OPPONENT");
        connectToServer();
        initialMap();

        try {
            out.writeObject(player);
            model = (GameManager) in.readObject();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        updateView();
        startTimer();

    }



}
