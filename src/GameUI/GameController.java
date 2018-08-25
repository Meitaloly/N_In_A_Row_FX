package GameUI;

import GameLogic.GameManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.*;
import javafx.stage.Stage;


public class GameController {

    @FXML Label playerOneName;
    @FXML Label playerOneId;
    @FXML Label playerOneType;
    @FXML Label playerOneColor;
    @FXML Label playerOneNumofTurns;

    private Stage primaryStage;
    private GameManager gameManager;

    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void showPlayersToScreen(){
        if (gameManager.numOfPlayers()>0) {
            int player = 1;
            String name;
            String ID;
            String type;
            String turns;
            //while (player<gameManager.numOfPlayers()){

            //  }
            name = gameManager.getPlayerName(player);  //only for debug
            playerOneName.setText(name);
            ID = "ID: " + gameManager.getPlayerId(player);    //only for debug
            playerOneId.setText(ID);
            type = "Player type: " + gameManager.getPlayerType(player);  //only for debug
            playerOneType.setText(type);
            turns = "Number of turns: " + gameManager.getPlayerNumOfTurns(player);   //only for debug
            playerOneNumofTurns.setText(turns);
        }
    }
}
